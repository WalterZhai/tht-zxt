package com.cimctht.thtzxt.system.controller;

import com.cimctht.thtzxt.system.Impl.UserServiceImpl;
import com.cimctht.thtzxt.system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;



}
