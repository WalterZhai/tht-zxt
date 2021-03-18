package com.cimctht.thtzxt.basedata.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cimctht.thtzxt.basedata.Impl.DepartServiceImpl;
import com.cimctht.thtzxt.basedata.bo.DepartSyncBo;
import com.cimctht.thtzxt.basedata.entity.Depart;
import com.cimctht.thtzxt.basedata.repository.DepartRepository;
import com.cimctht.thtzxt.basedata.repository.EmployeeRepository;
import com.cimctht.thtzxt.common.constant.SysConstant;
import com.cimctht.thtzxt.common.constant.ThtConstant;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.exception.UnimaxException;
import com.cimctht.thtzxt.common.utils.EntityUtils;
import com.cimctht.thtzxt.common.utils.MathsUtils;
import com.cimctht.thtzxt.common.utils.StringUtils;
import org.apache.axiom.om.OMElement;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import java.util.*;

@Service
public class DeaprtService implements DepartServiceImpl {

    static final Logger logger = LoggerFactory.getLogger(DeaprtService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartRepository departRepository;


    @Override
    public JSONArray ajaxLoadTree() {
        JSONArray resultArr = new JSONArray();
        List<Depart> list = departRepository.queryDepartsByParentDepartAndIsDelete(null,0);
        for(Depart d : list){
            JSONObject o = new JSONObject();
            o = recursionDepart(o,d);
            resultArr.add(o);
        }
        return resultArr;
    }

    //递归找儿子
    public JSONObject recursionDepart(JSONObject o,Depart d){
        o.put("title",d.getName());
        o.put("id",d.getId());
        List<Depart> list = departRepository.queryDepartsByParentDepartAndIsDelete(d,0);
        if(list.size()>0){
            JSONArray arr = new JSONArray();
            for(Depart dchild : list){
                JSONObject oo = new JSONObject();
                oo.put("title",dchild.getName());
                oo.put("id",dchild.getId());
                oo = recursionDepart(oo,dchild);
                arr.add(oo);
            }
            o.put("children",arr);
        }
        return o;
    }

    @Override
    public TableEntity queryMenusByIsDeleteAndPmenu(String id, Integer page, Integer limit) {
        Depart d = departRepository.findDepartById(id);
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<Depart> pages = departRepository.queryDepartsByIsDeleteAndParentDepartOrderByCode(0,d,pageable);
        return new TableEntity(pages.getContent(), MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }

    @Override
    public void addDeaprt(String id, String code, String name, String uda1) {
        Depart p = departRepository.findDepartById(id);
        Depart d = new Depart();
        d.setCode(code);
        d.setName(name);
        d.setUda1(uda1);
        d.setParentDepart(p);
        EntityUtils.insertBasicInfo(d);
        departRepository.save(d);
    }

    @Override
    public void editDeaprt(String id, String code, String name, String uda1) {
        Depart d = departRepository.findDepartById(id);
        d.setCode(code);
        d.setName(name);
        d.setUda1(uda1);
        EntityUtils.insertBasicInfo(d);
        departRepository.save(d);
    }

    @Override
    public void syncDepart() {
        //加载已有部门，转为Map，以unit_id作为key，实体作为value
        List<Depart>  listDeparts = departRepository.findDepartsByIsDelete(0);
        Map<String,Depart> map = new HashMap<String,Depart>();
        for(Depart d : listDeparts){
            map.put(d.getCode(),d);
        }

        try{
            // 使用RPC方式调用WebService
            RPCServiceClient serviceClient = new RPCServiceClient();

            // 指定调用WebService的URL
            EndpointReference targetEPR = new EndpointReference(ThtConstant.HRMS_DEPART);
            Options options = serviceClient.getOptions();
            // 确定目标服务地址
            options.setTo(targetEPR);
            // 确定调用方法
            options.setAction("soap:Body");

            options.setTimeOutInMilliSeconds(20);

            QName qname = new QName("http://10.33.1.39/hrms", "Body");
            // 指定getPrice方法的参数值
            Object[] parameters = new Object[] {};

            // 指定getPrice方法返回值的数据类型的Class对象
            Class[] returnTypes = new Class[] { String.class };



            // 调用方法一 传递参数，调用服务，获取服务返回结果集
            OMElement element = serviceClient.invokeBlocking(qname, parameters);
            Iterator it = element.getChildrenWithLocalName("record");
            List<Depart> list = new ArrayList<Depart>();
            List<DepartSyncBo> listbo = new ArrayList<DepartSyncBo>();
            while (it.hasNext()) {
                // 转成字符串
                String xmlStr = it.next().toString();
                Document doc = DocumentHelper.parseText(xmlStr);
                // 获得document中的根节点
                String uParam;
                Element rootElement = doc.getRootElement();
                uParam = rootElement.elementTextTrim("UNIT_ID");
                Depart d;
                DepartSyncBo bo = new DepartSyncBo();
                if(map.containsKey(uParam)){
                    d = map.get(uParam);
                }else{
                    d = new Depart();
                }
                uParam = rootElement.elementTextTrim("UNIT_ID");
                d.setCode(uParam);
                bo.setChildCode(uParam);
                uParam = rootElement.elementTextTrim("UNIT_NAME");
                d.setName(uParam);
                uParam = rootElement.elementTextTrim("PARENT_ID");
                bo.setParentCode(uParam);

                if(StringUtils.isEmpty(d.getId())){
                    d.setCreateId(SysConstant.ADMIN);
                    d.setModifyId(SysConstant.ADMIN);
                }else{
                    d.setModifyId(SysConstant.ADMIN);
                }
                list.add(d);
                listbo.add(bo);
            }
            departRepository.saveAll(list);
            //填写上级部门
            for(DepartSyncBo b : listbo){
                if(!StringUtils.isEmpty(b.getParentCode())){
                    Depart d =  departRepository.findDepartByCode(b.getChildCode());
                    if(d.getParentDepart()!=null){
                        if(!d.getParentDepart().getCode().equals(b.getParentCode())){
                            Depart p = departRepository.findDepartByCode(b.getParentCode());
                            d.setParentDepart(p);
                            departRepository.save(d);
                        }
                    }else{
                        Depart p = departRepository.findDepartByCode(b.getParentCode());
                        d.setParentDepart(p);
                        departRepository.save(d);
                    }
                }
            }
            logger.info("部门信息同步成功！");
        }catch (Exception e){
            throw new UnimaxException(e.getMessage());
        }
    }


}
