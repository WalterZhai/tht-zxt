package com.cimctht.thtzxt.basedata.Impl;

import com.cimctht.thtzxt.basedata.bo.SimpleEmployeeBo;
import com.cimctht.thtzxt.basedata.entity.Employee;
import com.cimctht.thtzxt.common.entity.TableEntity;

import java.util.List;

public interface EmployeeServiceImpl {

    TableEntity employeeTableData(String code, String name, Integer page, Integer limit);

    void genEmployees(List<SimpleEmployeeBo> list);

    TableEntity departEmpTableData(String id, Integer page, Integer limit);

    void syncEmployee();
}
