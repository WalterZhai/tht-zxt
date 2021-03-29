package com.cimctht.thtzxt.basedata.repository;

import com.cimctht.thtzxt.basedata.entity.Depart;
import com.cimctht.thtzxt.basedata.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
public interface EmployeeRepository extends JpaRepository<Employee,String> {

    Page<Employee> findEmployeesByIsDeleteAndCodeLikeAndNameLike(Integer isDelete, String code, String name, Pageable pageable);

    Page<Employee> findEmployeesByIsDeleteAndDepart(Integer isDelete, Depart d, Pageable pageable);

    List<Employee> findEmployeesByIsDelete(Integer isDelete);

    @Query(nativeQuery = true, value =" select EMPLOYEE_SEQ.nextval from dual ")
    BigInteger queryCodeSeqNext();

    Employee findEmployeeById(String id);
}
