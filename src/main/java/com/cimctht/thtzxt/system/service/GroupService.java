package com.cimctht.thtzxt.system.service;

import com.cimctht.thtzxt.system.Impl.GroupServiceImpl;
import com.cimctht.thtzxt.system.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Service
public class GroupService implements GroupServiceImpl {

    @Autowired
    private GroupRepository groupRepository;

}
