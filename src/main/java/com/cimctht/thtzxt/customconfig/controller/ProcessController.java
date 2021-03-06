package com.cimctht.thtzxt.customconfig.controller;

import com.alibaba.druid.util.Base64;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.cimctht.thtzxt.common.entity.JsonResult;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.exception.UnimaxException;
import com.cimctht.thtzxt.customconfig.Impl.ProcessServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class ProcessController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ProcessServiceImpl processServiceImpl;


    /**
     * @comment ????????????
     * @author Walter(?????????)
     * @date 2021/3/25
     */
    @GetMapping(value = "/model/modelTableData")
    public TableEntity modelTableData(HttpServletRequest request, String title, Integer page, Integer limit) {
        TableEntity table;
        try{
            table = processServiceImpl.findModels(title,page,limit);
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }

    /**
     * @comment ??????model
     * key????????????????????????
     * @author Walter(?????????)
     * @date 2021/3/25
     */
    @RequestMapping("/activiti-explorer/model/create")
    public void createModel(HttpServletRequest request, HttpServletResponse response){
        try{
            String modelName = request.getParameter("modelName");
            String modelKey = "";
            String description = request.getParameter("description");

            ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

            RepositoryService repositoryService = processEngine.getRepositoryService();

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);

            // ???????????????
            Model modelData = repositoryService.newModel();

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, modelName);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(modelName);
            modelData.setKey(modelKey);

            //????????????
            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
            response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + modelData.getId());
        }catch (Exception e){
        }
    }

    /**
     * @comment ??????
     * @author Walter(?????????)
     * @date 2021/3/25
     */
    @RequestMapping("/activiti-explorer/model/deployModel")
    public JsonResult deployModel(@RequestParam("id") String id) {
        Deployment deployment = null;
        try {
            Model modelData = repositoryService.getModel(id);
            byte[] bytes =repositoryService.getModelEditorSource(modelData.getId());

            if (bytes == null) {
                throw new UnimaxException("???????????????????????????????????????????????????????????????????????????");
            }

            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(bytes);

            if("process".equals(String.valueOf(modelNode.get("properties").get("process_id")).replace("\"",""))){
                throw new UnimaxException("process_id ??????????????????????????????");
            }
            modelData.setKey(String.valueOf(modelNode.get("properties").get("process_id")).replace("\"",""));
            JSONObject metaObj = JSONObject.parseObject(modelData.getMetaInfo());
            metaObj.put("revision",modelData.getVersion()+1);
            modelData.setMetaInfo(metaObj.toString());
            modelData.setVersion(modelData.getVersion()+1);

            byte[] bpmnBytes = null;

            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            bpmnBytes = new BpmnXMLConverter().convertToXML(model);

            String processName = modelData.getName() + ".bpmn20.xml";

            deployment = repositoryService.createDeployment()
                    .name(modelData.getName())
                    .addString(processName, new String(bpmnBytes,"utf-8"))
                    .key(modelData.getKey())
                    .deploy();
            //model?????????????????????id
            modelData.setDeploymentId(deployment.getId());
            repositoryService.saveModel(modelData);

            //?????????????????????id??????????????????
            //ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();


            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }


    /**
     * @comment ??????model
     * @author Walter(?????????)
     * @date 2021/3/25
     */
    @RequestMapping("/activiti-explorer/model/delete")
    public JsonResult deleteModel(HttpServletRequest request){
        try{
            String id = request.getParameter("id");
            //repositoryService.deleteDeployment(id,true); // ????????????ID
            repositoryService.deleteModel(id);
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException("????????????"));
        }
    }

    /**
     * @comment ??????????????????
     * @author Walter(?????????)
     * @date 2021/3/25
     */
    @GetMapping(value = "/act/test")
    public JsonResult ActTest(HttpServletRequest request){
        //List<Task> list = runServiceImpl.getTask("100001");
        //Boolean b = processServiceImpl.isEnd("100001");
        //runServiceImpl.completeTask("100006");
        // List<Task> list = processServiceImpl.getTask("2506");
        // processServiceImpl.completeTask("7502");
        return new JsonResult();
    }

    /**
     * @comment ???????????????(????????????)
     * @author Walter(?????????)
     * @date 2021/3/25
     */
    @GetMapping(value = "/runActiviti/viewProcessPictureStatic")
    public ModelAndView viewProcessPictureStatic( String key){

        ModelAndView modelAndView;
        InputStream inputStream = null;
        try{
            if(StringUtils.isEmpty(key)){
                throw new UnimaxException("key???????????????");
            }
            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).orderByProcessDefinitionVersion().desc().list();
            if(list!=null && list.size()==0){
                throw new UnimaxException("??????????????????????????????");
            }
            //??????????????????id?????????BpmnModel??????
            String processDefinitionId=list.get(0).getId();
            BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
            //????????????5.22.0????????????????????????
            DefaultProcessDiagramGenerator diagramGenerator=new DefaultProcessDiagramGenerator();

            //??????bpmnModel???????????????????????????
            //inputStream = diagramGenerator.generateDiagram(bpmnModel, "png", new ArrayList<String>());

            inputStream = diagramGenerator.generateDiagram(bpmnModel, "png", new ArrayList<String>(),new ArrayList<String>(),"??????","??????",processDefinitionId,null, 1.0);

            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);
            //???base64???????????????
            String base64Result = Base64.byteArrayToBase64(b);
            StringBuilder imgStr = new StringBuilder("data:image/").append("?????????").append(";base64,").append(base64Result);
            String str = imgStr.toString();


            ModelMap Model = new ModelMap().addAttribute("file", str);
            modelAndView= new ModelAndView("/customconfig/process/process_view",Model);
        }catch (Exception e){
            throw new UnimaxException("???????????????");
        } finally {
            if(inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return modelAndView;
    }

    /**
     * ?????????????????????(?????????)
     * @param processInstanceId
     */
    @GetMapping(value = "/runActiviti/viewProcessPictureDynamic")
    public ModelAndView viewProcessPictureDynamic(String processInstanceId){
        ModelAndView modelAndView;
        InputStream inputStream = null;
        try{
            HistoricProcessInstance processInstance= historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            //??????????????????id?????????BpmnModel??????
            String processDefinitionId=processInstance.getProcessDefinitionId();
            //??????????????????
            BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
            //????????????
            DefaultProcessDiagramGenerator diagramGenerator=new DefaultProcessDiagramGenerator();

            //??????????????????
            //List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);

            // ????????????????????????
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            // ?????????????????????????????????????????????????????????????????????
            List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId)
                    .orderByHistoricActivityInstanceId().asc().list();
            //???????????????????????????
            for (int i = historicActivityInstances.size() - 1; i > -1; i--) {
                if (historicActivityInstances.get(i).getEndTime() == null) {
                    historicActivityInstances.remove(i);
                }
            }
           // ??????????????????????????????ID??????
            List<String> highLightedActivitiIds = new ArrayList<>();
            for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
                highLightedActivitiIds.add(historicActivityInstance.getActivityId());
            }

            // ?????????????????????????????????id??????
            List<String> highLightedFlowIds = getHighLightedFlows(bpmnModel, historicActivityInstances);


            //??????bpmnModel???????????????????????????
            //inputStream = diagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);

            inputStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitiIds,highLightedFlowIds,"??????","??????","??????",null, 1.0);

            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);
            //???base64???????????????
            String base64Result = Base64.byteArrayToBase64(b);
            StringBuilder imgStr = new StringBuilder("data:image/").append("?????????").append(";base64,").append(base64Result);
            String str = imgStr.toString();

            ModelMap Model = new ModelMap().addAttribute("file", str);
            modelAndView= new ModelAndView("customconfig/process/process_view",Model);
        }catch (Exception e){
            throw new UnimaxException("???????????????");
        } finally {
            if(inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return modelAndView;
    }

    /**
     * ???????????????????????????
     * @param bpmnModel
     * @param historicActivityInstances
     * @return
     */
    private static List<String> getHighLightedFlows(BpmnModel bpmnModel, List<HistoricActivityInstance> historicActivityInstances) {
        // ?????????????????????????????????id??????
        List<String> highLightedFlowIds = new ArrayList<>();
        // ??????????????????
        List<FlowNode> historicActivityNodes = new ArrayList<>();
        // ??????????????????????????????
        List<HistoricActivityInstance> finishedActivityInstances = new ArrayList<>();

        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstance.getActivityId(), true);
            historicActivityNodes.add(flowNode);
            if (historicActivityInstance.getEndTime() != null) {
                finishedActivityInstances.add(historicActivityInstance);
            }
        }

        FlowNode currentFlowNode = null;
        FlowNode targetFlowNode = null;
        // ???????????????????????????????????????????????????outgoingFlows?????????????????????
        for (HistoricActivityInstance currentActivityInstance : finishedActivityInstances) {
            // ??????????????????????????????????????????outgoingFlows??????
            currentFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(currentActivityInstance.getActivityId(), true);
            List<SequenceFlow> sequenceFlows = currentFlowNode.getOutgoingFlows();

            /**
             * ??????outgoingFlows???????????????????????? ??????????????????????????????????????? 1.??????????????????????????????????????????????????????outgoingFlows???????????????????????????????????????????????????????????? 2.???????????????????????????????????????????????????outgoingFlows?????????????????????????????????????????????????????????
             */
            if ("parallelGateway".equals(currentActivityInstance.getActivityType()) || "inclusiveGateway".equals(currentActivityInstance.getActivityType())) {
                // ????????????????????????????????????????????????????????????
                for (SequenceFlow sequenceFlow : sequenceFlows) {
                    targetFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(sequenceFlow.getTargetRef(), true);
                    if (historicActivityNodes.contains(targetFlowNode)) {
                        highLightedFlowIds.add(targetFlowNode.getId());
                    }
                }
            } else {
                List<Map<String, Object>> tempMapList = new ArrayList<>();
                for (SequenceFlow sequenceFlow : sequenceFlows) {
                    for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
                        if (historicActivityInstance.getActivityId().equals(sequenceFlow.getTargetRef())) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("highLightedFlowId", sequenceFlow.getId());
                            map.put("highLightedFlowStartTime", historicActivityInstance.getStartTime().getTime());
                            tempMapList.add(map);
                        }
                    }
                }

                if (!CollectionUtils.isEmpty(tempMapList)) {
                    // ?????????????????????????????????????????????????????????
                    long earliestStamp = 0L;
                    String highLightedFlowId = null;
                    for (Map<String, Object> map : tempMapList) {
                        long highLightedFlowStartTime = Long.valueOf(map.get("highLightedFlowStartTime").toString());
                        if (earliestStamp == 0 || earliestStamp >= highLightedFlowStartTime) {
                            highLightedFlowId = map.get("highLightedFlowId").toString();
                            earliestStamp = highLightedFlowStartTime;
                        }
                    }

                    highLightedFlowIds.add(highLightedFlowId);
                }

            }

        }
        return highLightedFlowIds;
    }



}
