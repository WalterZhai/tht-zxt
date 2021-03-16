package com.cimctht.thtzxt.system.controller;

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
        User user = (User) session.getAttribute("user");
        try{
            List<String> list = menuServiceImpl.loadSearchInfo(info,user);
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


}
