package com.cimctht.thtzxt.basedata.controller;

import com.alibaba.fastjson.JSON;
import com.cimctht.thtzxt.basedata.Impl.PasswordPolicyServiceImpl;
import com.cimctht.thtzxt.basedata.entity.PasswordPolicy;
import com.cimctht.thtzxt.basedata.repository.PasswordPolicyRepository;
import com.cimctht.thtzxt.common.entity.JsonResult;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.exception.UnimaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PasswordPolicyController {

    static final Logger logger = LoggerFactory.getLogger(PasswordPolicyController.class);

    @Autowired
    private PasswordPolicyServiceImpl passwordPolicyServiceImpl;

    @Autowired
    private PasswordPolicyRepository passwordPolicyRepository;

    @GetMapping(value = "/passwordPolicy/passwordPolicyTableData")
    public TableEntity passwordPolicyTableData(HttpServletRequest request, Integer page, Integer limit) {
        TableEntity table;
        try{
            table = passwordPolicyServiceImpl.passwordPolicyTableData(page,limit);
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }


    @PostMapping(value = "/passwordPolicy/delPasswordPolicy")
    public JsonResult delPasswordPolicy(HttpServletRequest request) {
        String id = request.getParameter("id");
        try{
            PasswordPolicy passwordPolicy = passwordPolicyRepository.findPasswordPolicyById(id);
            passwordPolicy.setIsDelete(1);
            passwordPolicyRepository.save(passwordPolicy);
            return new JsonResult("删除成功");
        }catch (Exception ex){
            return new JsonResult(new UnimaxException("删除失败"));
        }
    }


    @GetMapping(value = "/topage/passwordPolicyEntity")
    public ModelAndView passwordPolicyEntity(HttpServletRequest request) {
        String url = request.getParameter("url");
        url = url.substring(0, url.lastIndexOf("."));
        url = url.replace(".", "/");
        String id = request.getParameter("id");
        PasswordPolicy passwordPolicy = passwordPolicyRepository.findPasswordPolicyById(id);
        ModelAndView modelAndView= new ModelAndView(url).addObject("passwordPolicy",passwordPolicy);
        return modelAndView;
    }

    @PostMapping(value = "/passwordPolicy/addPasswordPolicy")
    public JsonResult addPasswordPolicy(HttpServletRequest request) {
        String name = request.getParameter("name");
        String value = request.getParameter("value");
        String description = request.getParameter("description");
        try{
            PasswordPolicy passwordPolicy = new PasswordPolicy();
            passwordPolicy.setName(name);
            passwordPolicy.setValue(value);
            passwordPolicy.setDescription(description);
            passwordPolicy.setIsUsed(1);
            passwordPolicyRepository.save(passwordPolicy);
            return new JsonResult("添加成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException("添加失败"));
        }
    }

    @PostMapping(value = "/passwordPolicy/editPasswordPolicy")
    public JsonResult editPasswordPolicy(HttpServletRequest request) {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String value = request.getParameter("value");
        String description = request.getParameter("description");
        try{
            PasswordPolicy passwordPolicy = passwordPolicyRepository.findPasswordPolicyById(id);
            passwordPolicy.setName(name);
            passwordPolicy.setValue(value);
            passwordPolicy.setDescription(description);
            passwordPolicyRepository.save(passwordPolicy);
            return new JsonResult("修改成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException("修改失败"));
        }
    }

    @PostMapping(value = "/passwordPolicy/usedPasswordPolicy")
    public JsonResult usedPasswordPolicy(HttpServletRequest request) {
        String arrs = request.getParameter("arrs");
        List<PasswordPolicy> list = JSON.parseArray(arrs,PasswordPolicy.class);
        try{
            passwordPolicyServiceImpl.usedPasswordPolicy(list.get(0));
            return new JsonResult("选用成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

}
