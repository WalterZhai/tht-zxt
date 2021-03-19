package com.cimctht.thtzxt.basedata.service;

import com.cimctht.thtzxt.basedata.Impl.EmployeeServiceImpl;
import com.cimctht.thtzxt.basedata.bo.SimpleEmployeeBo;
import com.cimctht.thtzxt.basedata.entity.Depart;
import com.cimctht.thtzxt.basedata.entity.Employee;
import com.cimctht.thtzxt.basedata.repository.DepartRepository;
import com.cimctht.thtzxt.basedata.repository.EmployeeRepository;
import com.cimctht.thtzxt.common.constant.SysConstant;
import com.cimctht.thtzxt.common.constant.ThtConstant;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.exception.UnimaxException;
import com.cimctht.thtzxt.common.utils.EntityUtils;
import com.cimctht.thtzxt.common.utils.MathsUtils;
import com.cimctht.thtzxt.common.utils.StringUtils;
import com.cimctht.thtzxt.common.utils.TimeUtils;
import com.cimctht.thtzxt.system.entity.User;
import com.cimctht.thtzxt.system.repository.UserRepository;
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
public class EmployeeService implements EmployeeServiceImpl {

    static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartRepository departRepository;

    @Override
    public TableEntity employeeTableData(String code, String name, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<Employee> pages = employeeRepository.findEmployeesByIsDeleteAndCodeLikeAndNameLike(0,StringUtils.string2LikeParam(code),StringUtils.string2LikeParam(name),pageable);
        List<Employee> list = pages.getContent();
        List<SimpleEmployeeBo> result = new ArrayList<>();
        for(Employee employee : list){
            result.add(new SimpleEmployeeBo(employee));
        }
        return new TableEntity(result, MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }

    @Override
    public void genEmployees(List<SimpleEmployeeBo> list) {
        List<User> listU = new ArrayList<>();
        for(SimpleEmployeeBo emp : list){
            if(userRepository.findUserByLoginNameAndIsDelete(emp.getCode(),0)!=null){
                continue;
            }
            User u = new User();
            u.setName(emp.getName());
            u.setLoginName(emp.getCode());
            u.setPassword(SysConstant.PASSWORD);
            u.setIsLocked(0);
            u.setEmail(emp.getEmail());
            u.setMobile(emp.getMobile());
            listU.add(u);
        }
        userRepository.saveAll(listU);
    }

    @Override
    public TableEntity departEmpTableData(String id, Integer page, Integer limit) {
        Depart d = departRepository.findDepartById(id);
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<Employee> pages = employeeRepository.findEmployeesByIsDeleteAndDepart(0,d,pageable);
        List<Employee> list = pages.getContent();
        List<SimpleEmployeeBo> result = new ArrayList<>();
        for(Employee employee : list){
            result.add(new SimpleEmployeeBo(employee));
        }
        return new TableEntity(result, MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }

    @Override
    public void syncEmployee(){

        //加载已有用户，转为Map，以集团工号作为key，实体作为value
        List<Employee>  listEmps = employeeRepository.findEmployeesByIsDelete(0);
        Map<String,Employee> map = new HashMap<String,Employee>();
        for(Employee e : listEmps){
            map.put(e.getUda1(),e);
        }

        try{

            // 使用RPC方式调用WebService
            RPCServiceClient serviceClient = new RPCServiceClient();

            // 指定调用WebService的URL
            EndpointReference targetEPR = new EndpointReference(ThtConstant.HRMS_EMPLOYEE);
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
            List<Employee> list = new ArrayList<Employee>();
            while (it.hasNext()) {
                // 转成字符串
                String xmlStr = it.next().toString();
                Document doc = DocumentHelper.parseText(xmlStr);
                // 获得document中的根节点
                String uParam;
                Element rootElement = doc.getRootElement();
                uParam = rootElement.elementTextTrim("EMPLOYEE_CODE");
                Employee u;
                if(map.containsKey(uParam)){
                    u = map.get(uParam);
                }else{
                    u = new Employee();
                }
                uParam = rootElement.elementTextTrim("COMPANY_CODE");
                u.setCode(uParam);
                uParam = rootElement.elementTextTrim("EMPLOYEE_NAME");
                u.setName(uParam);
                uParam = rootElement.elementTextTrim("EMPLOYEE_CODE");
                u.setUda1(uParam);
                uParam = rootElement.elementTextTrim("MOBIL");
                u.setMobile(uParam);
                uParam = rootElement.elementTextTrim("SEX_NAME");
                if("男".equals(uParam)){
                    u.setSex(0);
                }else{
                    u.setSex(1);
                }
                uParam = rootElement.elementTextTrim("CERTIFICATE_ID");
                u.setIdCardNum(uParam);
                uParam = rootElement.elementTextTrim("BORN_DATE");
                u.setBirthday(TimeUtils.convertStringToDate(uParam,"yyyy-MM-dd hh:mm:ss"));
                uParam = rootElement.elementTextTrim("HOME_TOWN");
                u.setAddress(uParam);
                uParam = rootElement.elementTextTrim("OFFICE_PHONE");
                u.setOfficeTele(uParam);
                uParam = rootElement.elementTextTrim("EMAIL");
                u.setEmail(uParam);
                uParam = rootElement.elementTextTrim("FIRST_WORK_DATE");
                u.setCareerBeginDate(TimeUtils.convertStringToDate(uParam,"yyyy-MM-dd hh:mm:ss"));
                uParam = rootElement.elementTextTrim("JOIN_DATE");
                u.setJoinCompanyDate(TimeUtils.convertStringToDate(uParam,"yyyy-MM-dd hh:mm:ss"));
                uParam = rootElement.elementTextTrim("UNIT_ID");
                Depart d = departRepository.findDepartByCode(uParam);
                u.setDepart(d);

                if(StringUtils.isEmpty(u.getId())){
                    u.setCreateId("admin");
                    u.setModifyId("admin");
                }else{
                    u.setModifyId("admin");
                }

                list.add(u);
            }
            employeeRepository.saveAll(list);
            logger.info("人员信息同步成功！");
        }catch (Exception e){
            throw new UnimaxException(e.getMessage());
        }
    }

}
