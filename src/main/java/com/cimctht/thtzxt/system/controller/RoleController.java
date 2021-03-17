package com.cimctht.thtzxt.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cimctht.thtzxt.common.constant.SysConstant;
import com.cimctht.thtzxt.common.entity.JsonResult;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.exception.UnimaxException;
import com.cimctht.thtzxt.common.utils.EntityUtils;
import com.cimctht.thtzxt.common.utils.StringUtils;
import com.cimctht.thtzxt.system.Impl.RoleServiceImpl;
import com.cimctht.thtzxt.system.bo.SimpleRoleBo;
import com.cimctht.thtzxt.system.entity.Role;
import com.cimctht.thtzxt.system.entity.User;
import com.cimctht.thtzxt.system.repository.RoleRepository;
import com.cimctht.thtzxt.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@RestController
public class RoleController {

    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/role/roleTableData")
    public TableEntity roleTableData(HttpServletRequest request, String code, String name, Integer page, Integer limit) {
        TableEntity table;
        try{
            table = roleServiceImpl.roleTableData(code,name,page,limit);
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }

    @PostMapping(value = "/role/delRoles")
    public JsonResult delRoles(HttpServletRequest request) {
        String arrs = request.getParameter("arrs");
        List<SimpleRoleBo> arr = JSON.parseArray(arrs,SimpleRoleBo.class);
        try{
            List<Role> list = new ArrayList<>();
            for(SimpleRoleBo bo : arr){
                if(SysConstant.SYSTEM_ROLE_NAME.equals(bo.getName())){
                    throw new UnimaxException("系统管理员角色无法删除!");
                }
                list.add(roleRepository.findRoleById(bo.getId()));
            }
            roleRepository.saveAll(list);
            return new JsonResult("删除成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/role/delRole")
    public JsonResult delRole(HttpServletRequest request) {
        String id = request.getParameter("id");
        try{
            Role role = roleRepository.findRoleById(id);
            if(SysConstant.SYSTEM_ROLE_NAME.equals(role.getName())){
                throw new UnimaxException("系统管理员角色无法删除!");
            }
            role.setIsDelete(1);
            roleRepository.save(role);
            return new JsonResult("删除成功");
        }catch (Exception ex){
            return new JsonResult(new UnimaxException(ex.getMessage()));
        }
    }


    @PostMapping(value = "/role/addRole")
    public JsonResult addRole(HttpServletRequest request,String name,String description) {
        try{
            Role role = new Role();
            role.setCode(SysConstant.SYSTEM_ROLE_CODE_PREFIX + StringUtils.padLeft(roleRepository.queryCodeSeqNext().toString(),3,"0"));
            role.setName(name);
            role.setDescription(description);
            roleRepository.save(role);
            return new JsonResult("新增成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }


    @PostMapping(value = "/role/editRole")
    public JsonResult editRole(HttpServletRequest request,String id,String name,String description) {
        try{
            Role role = roleRepository.findRoleById(id);
            if(SysConstant.SYSTEM_ROLE_NAME.equals(role.getName())){
                throw new UnimaxException("系统管理员角色无法修改!");
            }
            role.setName(name);
            role.setDescription(description);
            roleRepository.save(role);
            return new JsonResult("修改成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/role/ajaxLoadMenuTreeByRole")
    public JsonResult ajaxLoadMenuTreeByRole(HttpServletRequest request,String roleid) {
        try{
            JSONArray array = roleServiceImpl.ajaxLoadMenuTreeByRole(roleid);
            return new JsonResult(array);
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/role/saveMenuSelectByRole")
    public JsonResult saveMenuSelectByRole(HttpServletRequest request,String id,String treeData) {
        JSONArray trees = JSON.parseArray(treeData);
        try{
            roleServiceImpl.saveMenuSelectByRole(id,trees);
            return new JsonResult("保存成功!");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }


    @PostMapping(value = "/role/ajaxLoadTransferRoleRelUser")
    public JsonResult ajaxLoadTransferRoleRelUser(HttpServletRequest request,String roleid) {
        try{
            Map<String,Object> map = roleServiceImpl.ajaxLoadTransferRoleRelUser(roleid);
            return new JsonResult(map);
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/role/addSelectUser")
    public JsonResult addSelectUser(HttpServletRequest request,String roleid,String data) {
        JSONArray arr = JSON.parseArray(data);
        try{
            Role role = roleRepository.findRoleById(roleid);
            for(int i=0;i<arr.size();i++){
                User user = userRepository.findUserById(arr.getJSONObject(i).getString("value"));
                user.getRoles().add(role);
                userRepository.save(user);
            }
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/role/delSelectUser")
    public JsonResult delSelectUser(HttpServletRequest request,String roleid,String data) {
        JSONArray arr = JSON.parseArray(data);
        try{
            Role role = roleRepository.findRoleById(roleid);
            for(int i=0;i<arr.size();i++){
                User user = userRepository.findUserById(arr.getJSONObject(i).getString("value"));
                user.getRoles().remove(role);
                userRepository.save(user);
            }
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

}
