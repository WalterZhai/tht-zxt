package com.cimctht.thtzxt.system.controller;

import com.cimctht.thtzxt.system.Impl.GroupServiceImpl;
import com.cimctht.thtzxt.system.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@RestController
public class GroupController {

    @Autowired
    private GroupServiceImpl groupServiceImpl;

    @Autowired
    private GroupRepository groupRepository;



}
