package com.cimctht.thtzxt.system.controller;

import com.cimctht.thtzxt.system.Impl.GroupServiceImpl;
import com.cimctht.thtzxt.system.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupController {

    @Autowired
    private GroupServiceImpl groupServiceImpl;

    @Autowired
    private GroupRepository groupRepository;



}
