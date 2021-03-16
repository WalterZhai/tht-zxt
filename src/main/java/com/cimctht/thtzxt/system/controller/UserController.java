package com.cimctht.thtzxt.system.controller;

import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.system.Impl.UserServiceImpl;
import com.cimctht.thtzxt.system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@RestController
public class UserController {

    static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping(value = "/user/tableData")
    public TableEntity userTableData(HttpServletRequest request, String searchName, String page, String limit) {
        TableEntity table;
        try{
            table = userService.queryUsersByLikeName(searchName,Integer.parseInt(page),Integer.parseInt(limit));
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }

    @GetMapping(value = "/user/onlineUserData")
    public TableEntity onlineUserData(HttpServletRequest request,String page,String limit) {
        TableEntity table;
        try{
            table = userService.onlineUserData(Integer.parseInt(page),Integer.parseInt(limit));
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }


}
