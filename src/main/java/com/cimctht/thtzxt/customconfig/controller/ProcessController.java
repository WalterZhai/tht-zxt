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
     * @comment 表格查询
     * @author Walter(翟笑天)
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
     * @comment 新建model
     * key值在流程图内定义
     * @author Walter(翟笑天)
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

            // 定义新模型
            Model modelData = repositoryService.newModel();

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, modelName);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(modelName);
            modelData.setKey(modelKey);

            //保存模型
            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
            response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + modelData.getId());
        }catch (Exception e){
        }
    }

    /**
     * @comment 部署
     * @author Walter(翟笑天)
     * @date 2021/3/25
     */
    @RequestMapping("/activiti-explorer/model/deployModel")
    public JsonResult deployModel(@RequestParam("id") String id) {
        Deployment deployment = null;
        try {
            Model modelData = repositoryService.getModel(id);
            byte[] bytes =repositoryService.getModelEditorSource(modelData.getId());

            if (bytes == null) {
                throw new UnimaxException("模型数据为空，请先设计流程并成功保存，再进行发布。");
            }

            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(bytes);

            if("process".equals(String.valueOf(modelNode.get("properties").get("process_id")).replace("\"",""))){
                throw new UnimaxException("process_id 必须修改后才能部署。");
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
            //model设置关联的部署id
            modelData.setDeploymentId(deployment.getId());
            repositoryService.saveModel(modelData);

            //根据发布流程的id获取流程定义
            //ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();


            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }


    /**
     * @comment 删除model
     * @author Walter(翟笑天)
     * @date 2021/3/25
     */
    @RequestMapping("/activiti-explorer/model/delete")
    public JsonResult deleteModel(HttpServletRequest request){
        try{
            String id = request.getParameter("id");
            //repositoryService.deleteDeployment(id,true); // 流程部署ID
            repositoryService.deleteModel(id);
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException("删除失败"));
        }
    }

    /**
     * @comment 测试用的接口
     * @author Walter(翟笑天)
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
     * @comment 查看流程图(未实例化)
     * @author Walter(翟笑天)
     * @date 2021/3/25
     */
    @GetMapping(value = "/runActiviti/viewProcessPictureStatic")
    public ModelAndView viewProcessPictureStatic( String key){

        ModelAndView modelAndView;
        InputStream inputStream = null;
        try{
            if(StringUtils.isEmpty(key)){
                throw new UnimaxException("key不能为空！");
            }
            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).orderByProcessDefinitionVersion().desc().list();
            if(list!=null && list.size()==0){
                throw new UnimaxException("未找到相应流程定义！");
            }
            //根据流程定义id来获取BpmnModel对象
            String processDefinitionId=list.get(0).getId();
            BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
            //这个类在5.22.0往上的版本中才有
            DefaultProcessDiagramGenerator diagramGenerator=new DefaultProcessDiagramGenerator();

            //绘制bpmnModel代表的流程的流程图
            //inputStream = diagramGenerator.generateDiagram(bpmnModel, "png", new ArrayList<String>());

            inputStream = diagramGenerator.generateDiagram(bpmnModel, "png", new ArrayList<String>(),new ArrayList<String>(),"宋体","宋体",processDefinitionId,null, 1.0);

            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);
            //用base64写成字符串
            String base64Result = Base64.byteArrayToBase64(b);
            StringBuilder imgStr = new StringBuilder("data:image/").append("流程图").append(";base64,").append(base64Result);
            String str = imgStr.toString();


            ModelMap Model = new ModelMap().addAttribute("file", str);
            modelAndView= new ModelAndView("/customconfig/process/process_view",Model);
        }catch (Exception e){
            throw new UnimaxException("查看失败！");
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
     * 查看实例流程图(已运行)
     * @param processInstanceId
     */
    @GetMapping(value = "/runActiviti/viewProcessPictureDynamic")
    public ModelAndView viewProcessPictureDynamic(String processInstanceId){
        ModelAndView modelAndView;
        InputStream inputStream = null;
        try{
            HistoricProcessInstance processInstance= historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            //根据流程定义id来获取BpmnModel对象
            String processDefinitionId=processInstance.getProcessDefinitionId();
            //获得定义图片
            BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
            //画图实例
            DefaultProcessDiagramGenerator diagramGenerator=new DefaultProcessDiagramGenerator();

            //获得当前节点
            //List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);

            // 获取历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            // 获取流程中已经执行的节点，按照执行先后顺序排序
            List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId)
                    .orderByHistoricActivityInstanceId().asc().list();
            //没有结束时间的去掉
            for (int i = historicActivityInstances.size() - 1; i > -1; i--) {
                if (historicActivityInstances.get(i).getEndTime() == null) {
                    historicActivityInstances.remove(i);
                }
            }
           // 高亮已经执行流程节点ID集合
            List<String> highLightedActivitiIds = new ArrayList<>();
            for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
                highLightedActivitiIds.add(historicActivityInstance.getActivityId());
            }

            // 高亮流程已发生流转的线id集合
            List<String> highLightedFlowIds = getHighLightedFlows(bpmnModel, historicActivityInstances);


            //绘制bpmnModel代表的流程的流程图
            //inputStream = diagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);

            inputStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitiIds,highLightedFlowIds,"宋体","宋体","宋体",null, 1.0);

            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);
            //用base64写成字符串
            String base64Result = Base64.byteArrayToBase64(b);
            StringBuilder imgStr = new StringBuilder("data:image/").append("流程图").append(";base64,").append(base64Result);
            String str = imgStr.toString();

            ModelMap Model = new ModelMap().addAttribute("file", str);
            modelAndView= new ModelAndView("customconfig/process/process_view",Model);
        }catch (Exception e){
            throw new UnimaxException("查看失败！");
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
     * 走过的流程连线集合
     * @param bpmnModel
     * @param historicActivityInstances
     * @return
     */
    private static List<String> getHighLightedFlows(BpmnModel bpmnModel, List<HistoricActivityInstance> historicActivityInstances) {
        // 高亮流程已发生流转的线id集合
        List<String> highLightedFlowIds = new ArrayList<>();
        // 全部活动节点
        List<FlowNode> historicActivityNodes = new ArrayList<>();
        // 已完成的历史活动节点
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
        // 遍历已完成的活动实例，从每个实例的outgoingFlows中找到已执行的
        for (HistoricActivityInstance currentActivityInstance : finishedActivityInstances) {
            // 获得当前活动对应的节点信息及outgoingFlows信息
            currentFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(currentActivityInstance.getActivityId(), true);
            List<SequenceFlow> sequenceFlows = currentFlowNode.getOutgoingFlows();

            /**
             * 遍历outgoingFlows并找到已已流转的 满足如下条件认为已已流转： 1.当前节点是并行网关或兼容网关，则通过outgoingFlows能够在历史活动中找到的全部节点均为已流转 2.当前节点是以上两种类型之外的，通过outgoingFlows查找到的时间最早的流转节点视为有效流转
             */
            if ("parallelGateway".equals(currentActivityInstance.getActivityType()) || "inclusiveGateway".equals(currentActivityInstance.getActivityType())) {
                // 遍历历史活动节点，找到匹配流程目标节点的
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
                    // 遍历匹配的集合，取得开始时间最早的一个
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
