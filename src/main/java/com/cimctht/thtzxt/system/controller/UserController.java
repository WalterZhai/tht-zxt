package com.cimctht.thtzxt.system.controller;

import com.cimctht.thtzxt.common.entity.JsonResult;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.exception.UnimaxException;
import com.cimctht.thtzxt.system.Impl.UserServiceImpl;
import com.cimctht.thtzxt.system.entity.User;
import com.cimctht.thtzxt.system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@RestController
public class UserController {

    static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserRepository userRepository;


    @GetMapping(value = "/user/tableData")
    public TableEntity userTableData(HttpServletRequest request, String searchName, String page, String limit) {
        TableEntity table;
        try{
            table = userServiceImpl.queryUsersByLikeName(searchName,Integer.parseInt(page),Integer.parseInt(limit));
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }

    @GetMapping(value = "/user/onlineUserData")
    public TableEntity onlineUserData(HttpServletRequest request,String page,String limit) {
        TableEntity table;
        try{
            table = userServiceImpl.onlineUserData(Integer.parseInt(page),Integer.parseInt(limit));
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }


    @GetMapping(value = "/topage/user/loadUserInfo")
    public ModelAndView topageUserLoadUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String url = request.getParameter("url");
        url = url.substring(0, url.lastIndexOf("."));
        url = url.replace(".", "/");
        User user = userRepository.findUserByLoginNameAndIsDelete(username,0);
        ModelAndView modelAndView= new ModelAndView(url).addObject("user",user);
        return modelAndView;
    }

    @PostMapping(value = "/user/editBaseInfo")
    public JsonResult editBaseInfo(HttpServletRequest request) {
        String id = request.getParameter("id");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        try{
            User user = userRepository.findUserById(id);
            user.setEmail(email);
            user.setMobile(mobile);
            userRepository.save(user);
            return new JsonResult("修改成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException("修改失败"));
        }
    }

    @PostMapping(value = "/user/editPassword")
    public JsonResult editPassword(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String pwd1 = request.getParameter("pwd1");
        String pwd2 = request.getParameter("pwd2");
        String pwd3 = request.getParameter("pwd3");
        try{
            userServiceImpl.editPassword(username,pwd1,pwd2,pwd3);
            return new JsonResult("修改成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }


}
