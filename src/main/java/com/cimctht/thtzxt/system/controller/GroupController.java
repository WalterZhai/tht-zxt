package com.cimctht.thtzxt.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cimctht.thtzxt.common.constant.SysConstant;
import com.cimctht.thtzxt.common.entity.JsonResult;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.exception.UnimaxException;
import com.cimctht.thtzxt.common.utils.StringUtils;
import com.cimctht.thtzxt.system.Impl.GroupServiceImpl;
import com.cimctht.thtzxt.system.bo.SimpleGroupBo;
import com.cimctht.thtzxt.system.entity.Group;
import com.cimctht.thtzxt.system.entity.User;
import com.cimctht.thtzxt.system.repository.GroupRepository;
import com.cimctht.thtzxt.system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@RestController
public class GroupController {

    static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupServiceImpl groupServiceImpl;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping(value = "/group/groupTableData")
    public TableEntity roleTableData(HttpServletRequest request, String code, String name, Integer page, Integer limit) {
        TableEntity table;
        try{
            table = groupServiceImpl.roleTableData(code,name,page,limit);
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }

    @GetMapping(value = "/group/groupDetailTableData")
    public TableEntity groupDetailTableData(HttpServletRequest request, String selectid, Integer page, Integer limit) {
        TableEntity table;
        try{
            table = groupServiceImpl.groupDetailTableData(selectid,page,limit);
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }

    @PostMapping(value = "/group/addGroup")
    public JsonResult addGroup(HttpServletRequest request, String name, String description) {
        try{
            Group group = new Group();
            group.setCode(SysConstant.SYSTEM_GROUP_CODE_PREFIX + StringUtils.padLeft(groupRepository.queryCodeSeqNext().toString(),3,"0"));
            group.setName(name);
            group.setDescription(description);
            groupRepository.save(group);
            return new JsonResult("生成成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/group/delGroups")
    public JsonResult delGroups(HttpServletRequest request) {
        String arrs = request.getParameter("arrs");
        List<SimpleGroupBo> arr = JSON.parseArray(arrs, SimpleGroupBo.class);
        try{
            List<Group> list = new ArrayList<>();
            for(SimpleGroupBo bo : arr){
                if(SysConstant.SYSTEM_GROUP_NAME.equals(bo.getName())){
                    throw new UnimaxException("系统管理员组无法删除!");
                }
                Group group = groupRepository.findGroupById(bo.getId());
                //用户里面删除用户组关系
                List<User> users = group.getUsers();
                for(User user : users){
                    if(user.getGroups().contains(group)){
                        user.getGroups().remove(group);
                    }
                }
                userRepository.saveAll(users);
                group.setIsDelete(1);
                list.add(group);
            }
            groupRepository.saveAll(list);
            return new JsonResult("删除成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/group/editGroup")
    public JsonResult editGroup(HttpServletRequest request,String id,String name,String description) {
        try{
            Group group = groupRepository.findGroupById(id);
            if(SysConstant.SYSTEM_GROUP_NAME.equals(group.getName())){
                throw new UnimaxException("系统管理员组无法修改!");
            }
            group.setName(name);
            group.setDescription(description);
            groupRepository.save(group);
            return new JsonResult("编辑成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/group/delGroup")
    public JsonResult delGroup(HttpServletRequest request) {
        String id = request.getParameter("id");
        try{
            Group group = groupRepository.findGroupById(id);
            if(SysConstant.SYSTEM_GROUP_NAME.equals(group.getName())){
                throw new UnimaxException("系统管理员组无法删除!");
            }
            //用户里面删除用户组关系
            List<User> users = group.getUsers();
            for(User user : users){
                if(user.getGroups().contains(group)){
                    user.getGroups().remove(group);
                }
            }
            userRepository.saveAll(users);
            group.setIsDelete(1);
            groupRepository.save(group);
            return new JsonResult("删除成功");
        }catch (Exception ex){
            return new JsonResult(new UnimaxException(ex.getMessage()));
        }
    }

    @PostMapping(value = "/group/ajaxLoadTransferGroupRelUser")
    public JsonResult ajaxLoadTransferGroupRelUser(HttpServletRequest request,String groupid) {
        try{
            Map<String,Object> map = groupServiceImpl.ajaxLoadTransferGroupRelUser(groupid);
            return new JsonResult(map);
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/group/addSelectUser")
    public JsonResult addSelectUser(HttpServletRequest request,String groupid,String data) {
        JSONArray arr = JSON.parseArray(data);
        try{
            Group group = groupRepository.findGroupById(groupid);
            for(int i=0;i<arr.size();i++){
                User user = userRepository.findUserById(arr.getJSONObject(i).getString("value"));
                user.getGroups().add(group);
                userRepository.save(user);
            }
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/group/delSelectUser")
    public JsonResult delSelectUser(HttpServletRequest request,String groupid,String data) {
        JSONArray arr = JSON.parseArray(data);
        try{
            Group group = groupRepository.findGroupById(groupid);
            for(int i=0;i<arr.size();i++){
                User user = userRepository.findUserById(arr.getJSONObject(i).getString("value"));
                user.getGroups().remove(group);
                userRepository.save(user);
            }
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

}
