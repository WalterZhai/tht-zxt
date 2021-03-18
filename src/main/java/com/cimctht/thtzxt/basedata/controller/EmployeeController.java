package com.cimctht.thtzxt.basedata.controller;

import com.alibaba.fastjson.JSON;
import com.cimctht.thtzxt.basedata.Impl.EmployeeServiceImpl;
import com.cimctht.thtzxt.basedata.entity.Employee;
import com.cimctht.thtzxt.basedata.repository.EmployeeRepository;
import com.cimctht.thtzxt.common.entity.JsonResult;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.exception.UnimaxException;
import com.cimctht.thtzxt.common.utils.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class EmployeeController {

    static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping(value = "/employee/tableData")
    public TableEntity employeeTableData(HttpServletRequest request, String searchName, String searchCode, String page, String limit) {
        TableEntity table;
        try{
            table = employeeServiceImpl.findEmployeesByIsDeleteAndCodeLikeAndNameLike(searchName,searchCode,Integer.parseInt(page),Integer.parseInt(limit));
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }

    @PostMapping(value = "/employee/delEmployees")
    public JsonResult delEmployees(HttpServletRequest request) {
        String arrs = request.getParameter("arrs");
        List<Employee> list = JSON.parseArray(arrs,Employee.class);
        try{
            EntityUtils.insertDeleteAll(list);
            employeeRepository.saveAll(list);
            return new JsonResult("删除成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException("删除失败"));
        }
    }

    @PostMapping(value = "/employee/deleteEmployee")
    public JsonResult deleteEmployee(HttpServletRequest request) {
        String obj = request.getParameter("obj");
        Employee e = JSON.parseObject(obj,Employee.class);
        try{
            EntityUtils.insertDelete(e);
            employeeRepository.save(e);
            return new JsonResult("删除成功");
        }catch (Exception ex){
            return new JsonResult(new UnimaxException("删除失败"));
        }
    }

    @PostMapping(value = "/employee/genEmployees")
    public JsonResult genEmployees(HttpServletRequest request) {
        String arrs = request.getParameter("arrs");
        List<Employee> list = JSON.parseArray(arrs,Employee.class);
        try{
            employeeServiceImpl.genEmployees(list);
            return new JsonResult("生成成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException("生成失败"));
        }
    }

    @GetMapping(value = "/employee/departEmpTableData")
    public TableEntity employeeTableData(HttpServletRequest request, String id, Integer page, Integer limit) {
        TableEntity table;
        try{
            table = employeeServiceImpl.employeeTableData(id,page,limit);
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }

}
