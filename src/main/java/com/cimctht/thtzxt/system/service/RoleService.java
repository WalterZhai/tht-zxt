package com.cimctht.thtzxt.system.service;

import com.cimctht.thtzxt.system.Impl.RoleServiceImpl;
import com.cimctht.thtzxt.system.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements RoleServiceImpl {

    @Autowired
    private RoleRepository roleRepository;


}
