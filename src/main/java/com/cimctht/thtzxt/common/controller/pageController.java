package com.cimctht.thtzxt.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class pageController {

    @GetMapping(value="/topage")
    public ModelAndView topage(HttpServletRequest request) {
        String url = request.getParameter("url");
        url = url.substring(0, url.lastIndexOf("."));
        url = url.replace(".","/");
        ModelAndView modelAndView= new ModelAndView(url);
        return modelAndView;
    }

    @GetMapping(value="/topageid")
    public ModelAndView topageid(HttpServletRequest request) {
        String url = request.getParameter("url");
        url = url.substring(0, url.lastIndexOf("."));
        url = url.replace(".","/");
        String id = request.getParameter("id");
        ModelAndView modelAndView= new ModelAndView(url).addObject("id",id);
        return modelAndView;
    }

}
