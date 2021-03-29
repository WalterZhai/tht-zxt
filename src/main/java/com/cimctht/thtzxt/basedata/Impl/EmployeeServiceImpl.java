package com.cimctht.thtzxt.basedata.Impl;

import com.cimctht.thtzxt.basedata.bo.SimpleEmployeeBo;
import com.cimctht.thtzxt.basedata.entity.Employee;
import com.cimctht.thtzxt.common.entity.TableEntity;

import java.util.List;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
public interface EmployeeServiceImpl {

    TableEntity employeeTableData(String code, String name, Integer page, Integer limit);

    void genEmployees(List<SimpleEmployeeBo> list);

    TableEntity departEmpTableData(String id, Integer page, Integer limit);

    void syncEmployee();
}
