package com.cimctht.thtzxt.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cimctht.thtzxt.common.entity.JsonResult;
import com.cimctht.thtzxt.common.exception.UnimaxException;
import com.cimctht.thtzxt.system.Impl.MenuServiceImpl;
import com.cimctht.thtzxt.system.entity.Menu;
import com.cimctht.thtzxt.system.entity.User;
import com.cimctht.thtzxt.system.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@RestController
public class MenuController {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuServiceImpl menuServiceImpl;


    @PostMapping(value = "/menu/loadSearchInfo")
    public JsonResult loadSearchInfo(HttpServletRequest request, String info) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        try{
            List<String> list = menuServiceImpl.loadSearchInfo(info,username);
            return new JsonResult(list);
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/menu/searchAndOpenMenu")
    public JsonResult searchAndOpenMenu(HttpServletRequest request,String info) {
        try{
            String code = info.substring(info.indexOf("(") + 1, info.indexOf(")"));
            Menu menu = menuRepository.findMenuByCodeAndIsDelete(code, 0);
            return new JsonResult(menu);
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/menu/ajaxUserLoadTreeChecked")
    public JsonResult ajaxUserLoadTreeChecked(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        try{
            JSONArray array = menuServiceImpl.ajaxUserLoadTreeChecked(username);
            return new JsonResult(array);
        }catch (Exception e){
            return new JsonResult(new UnimaxException("加载树形菜单失败!"));
        }
    }

    @PostMapping(value = "/menu/saveMenuCollect")
    public JsonResult saveMenuCollect(HttpServletRequest request) {
        String treeData = request.getParameter("treeData");
        JSONArray trees = JSON.parseArray(treeData);
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        try{
            menuServiceImpl.saveMenuCollect(trees,username);
            return new JsonResult("保存成功!");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }


}
