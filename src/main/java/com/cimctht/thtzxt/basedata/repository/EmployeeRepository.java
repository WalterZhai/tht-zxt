package com.cimctht.thtzxt.basedata.repository;

import com.cimctht.thtzxt.basedata.entity.Depart;
import com.cimctht.thtzxt.basedata.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,String> {

    Page<Employee> findEmployeesByIsDeleteAndCodeLikeAndNameLike(Integer isDelete, String code, String name, Pageable pageable);

    Page<Employee> findEmployeesByIsDeleteAndDepart(Integer isDelete, Depart d, Pageable pageable);

}
