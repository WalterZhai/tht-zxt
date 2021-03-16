package com.cimctht.thtzxt.system.controller;

import com.cimctht.thtzxt.system.Impl.MenuServiceImpl;
import com.cimctht.thtzxt.system.entity.Menu;
import com.cimctht.thtzxt.system.entity.User;
import com.cimctht.thtzxt.system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@RestController
public class SystemController {

    static final Logger logger = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private MenuServiceImpl menuServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value="/login")
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView modelAndView= new ModelAndView("system/system/login");
        return modelAndView;
    }

    @GetMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request, Authentication authentication) {
        HttpSession session = request.getSession();
        Principal principal = request.getUserPrincipal();
        User user = userRepository.findUserByLoginNameAndIsDelete(principal.getName(),0);
        session.setAttribute("user",user);
        List<Menu> menus = menuServiceImpl.selectLoginMenu(user);
        ModelMap Model = new ModelMap().addAttribute("menus", menus);
        ModelAndView modelAndView= new ModelAndView("system/system/index",Model);
        logger.info(user.getName()+" 登录成功！");
        return modelAndView;
    }


}
