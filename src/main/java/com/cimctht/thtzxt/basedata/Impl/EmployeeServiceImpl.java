package com.cimctht.thtzxt.basedata.Impl;

import com.cimctht.thtzxt.basedata.entity.Employee;
import com.cimctht.thtzxt.common.entity.TableEntity;

import java.util.List;

public interface EmployeeServiceImpl {

    TableEntity findEmployeesByIsDeleteAndCodeLikeAndNameLike(String name, String code, Integer page, Integer limit);

    void genEmployees(List<Employee> list);

    TableEntity employeeTableData(String id, Integer page, Integer limit);

    void syncEmployee();
}
