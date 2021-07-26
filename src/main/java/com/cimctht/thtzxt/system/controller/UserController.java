package com.cimctht.thtzxt.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cimctht.thtzxt.common.constant.SysConstant;
import com.cimctht.thtzxt.common.distributedlock.CacheLock;
import com.cimctht.thtzxt.common.distributedlock.CacheParam;
import com.cimctht.thtzxt.common.entity.JsonResult;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.exception.UnimaxException;
import com.cimctht.thtzxt.system.Impl.UserServiceImpl;
import com.cimctht.thtzxt.system.bo.SimpleUserBo;
import com.cimctht.thtzxt.system.entity.Role;
import com.cimctht.thtzxt.system.entity.User;
import com.cimctht.thtzxt.system.repository.RoleRepository;
import com.cimctht.thtzxt.system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RoleRepository roleRepository;



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

    @GetMapping(value = "/user/userTableData")
    public TableEntity userTableData(HttpServletRequest request,String loginName,String name,Integer page,Integer limit) {
        TableEntity table;
        try{
            table = userServiceImpl.userTableData(loginName,name,page,limit);
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }

    @PostMapping(value = "/user/lockUser")
    public JsonResult lockUser(HttpServletRequest request,String id,Integer isLocked) {
        try{
            userServiceImpl.updateIsLockedById(id,isLocked);
            return new JsonResult("修改成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @CacheLock(prefix = "/user/addUser")
    @PostMapping(value = "/user/addUser")
    public JsonResult addUser(HttpServletRequest request) {
        String name = request.getParameter("name");
        String loginName = request.getParameter("loginName");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        try{
            TimeUnit.SECONDS.sleep(10);
            User user = new User();
            user.setLoginName(loginName);
            user.setPassword(SysConstant.PASSWORD);
            user.setName(name);
            user.setEmail(email);
            user.setMobile(mobile);
            user.setIsLocked(SysConstant.CONSTANT_ZERO);
            userRepository.save(user);
            return new JsonResult("添加成功");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/user/delUsers")
    public JsonResult delUsers(HttpServletRequest request) {
        String arrs = request.getParameter("arrs");
        List<SimpleUserBo> list = JSON.parseArray(arrs,SimpleUserBo.class);
        try{
            List<User> users = new ArrayList<>();
            for(SimpleUserBo bo : list){
                if(SysConstant.ADMIN.equals(bo.getLoginName())){
                    throw new UnimaxException("admin用户无法删除！");
                }
                User user = userRepository.findUserById(bo.getId());
                user.setRoles(new ArrayList<>());
                user.setGroups(new ArrayList<>());
                user.setCollects(new ArrayList<>());
                user.setIsDelete(1);
                users.add(user);
            }
            userRepository.saveAll(users);
            return new JsonResult("删除成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @CacheLock(prefix = "/user/editUser")
    @PostMapping(value = "/user/editUser")
    public JsonResult editUser(HttpServletRequest request) {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        try{
            User user = userRepository.findUserById(id);
            if(SysConstant.ADMIN.equals(user.getLoginName())){
                throw new UnimaxException("admin用户无法修改！");
            }
            user.setName(name);
            user.setEmail(email);
            user.setMobile(mobile);
            userRepository.save(user);
            return new JsonResult("修改成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/user/delUser")
    public JsonResult delUser(HttpServletRequest request) {
        String obj = request.getParameter("obj");
        SimpleUserBo bo = JSON.parseObject(obj,SimpleUserBo.class);
        try{
            User user = userRepository.findUserById(bo.getId());
            if(SysConstant.ADMIN.equals(user.getLoginName())){
                throw new UnimaxException("admin用户无法删除 ！");
            }
            user.setRoles(new ArrayList<>());
            user.setGroups(new ArrayList<>());
            user.setCollects(new ArrayList<>());
            user.setIsDelete(1);
            userRepository.save(user);
            return new JsonResult("删除成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/user/ajaxLoadTransferUserRelRole")
    public JsonResult ajaxLoadTransferUserRelRole(HttpServletRequest request,String userid) {
        try{
            Map<String,Object> map = userServiceImpl.ajaxLoadTransferUserRelRole(userid);
            return new JsonResult(map);
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/user/addSelectRole")
    public JsonResult addSelectRole(HttpServletRequest request,String userid,String data) {
        JSONArray arr = JSON.parseArray(data);
        try{
            User user = userRepository.findUserById(userid);
            for(int i=0;i<arr.size();i++){
                Role role = roleRepository.findRoleById(arr.getJSONObject(i).getString("value"));
                user.getRoles().add(role);
            }
            userRepository.save(user);
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/user/delSelectRole")
    public JsonResult delSelectRole(HttpServletRequest request,String userid,String data) {
        JSONArray arr = JSON.parseArray(data);
        try{
            User user = userRepository.findUserById(userid);
            for(int i=0;i<arr.size();i++){
                Role role = roleRepository.findRoleById(arr.getJSONObject(i).getString("value"));
                user.getRoles().remove(role);
            }
            userRepository.save(user);
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

}
