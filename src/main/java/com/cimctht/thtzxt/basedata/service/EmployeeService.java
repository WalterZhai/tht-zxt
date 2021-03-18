package com.cimctht.thtzxt.basedata.service;

import com.cimctht.thtzxt.basedata.Impl.EmployeeServiceImpl;
import com.cimctht.thtzxt.basedata.entity.Depart;
import com.cimctht.thtzxt.basedata.entity.Employee;
import com.cimctht.thtzxt.basedata.repository.DepartRepository;
import com.cimctht.thtzxt.basedata.repository.EmployeeRepository;
import com.cimctht.thtzxt.common.constant.SysConstant;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.utils.EntityUtils;
import com.cimctht.thtzxt.common.utils.MathsUtils;
import com.cimctht.thtzxt.system.entity.User;
import com.cimctht.thtzxt.system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public TableEntity findEmployeesByIsDeleteAndCodeLikeAndNameLike(String name, String code, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<Employee> pages = employeeRepository.findEmployeesByIsDeleteAndCodeLikeAndNameLike(0,"%"+code+"%","%"+name+"%",pageable);
        return new TableEntity(pages.getContent(), MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }

    @Override
    public void genEmployees(List<Employee> list) {
        List<User> listU = new ArrayList<User>();
        for(Employee emp : list){
            User u = new User();
            u.setName(emp.getName());
            u.setLoginName(emp.getCode());
            u.setPassword(SysConstant.PASSWORD);
            u.setIsLocked(0);
            u.setEmail(emp.getEmail());
            u.setMobile(emp.getMobile());
            EntityUtils.insertBasicInfo(u);
            listU.add(u);
        }
        userRepository.saveAll(listU);
    }

    @Override
    public TableEntity employeeTableData(String id, Integer page, Integer limit) {
        Depart d = departRepository.findDepartById(id);
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<Employee> pages = employeeRepository.findEmployeesByIsDeleteAndDepart(0,d,pageable);
        return new TableEntity(pages.getContent(), MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }

}
