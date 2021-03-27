package com.cimctht.thtzxt.basedata.controller;

import com.cimctht.thtzxt.basedata.Impl.SystemParamsServiceImpl;
import com.cimctht.thtzxt.basedata.entity.SystemParams;
import com.cimctht.thtzxt.basedata.repository.SystemParamsRepository;
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

@RestController
public class SystemParamsController {

    static final Logger logger = LoggerFactory.getLogger(SystemParamsController.class);

    @Autowired
    private SystemParamsServiceImpl systemParamsServiceImpl;

    @Autowired
    private SystemParamsRepository systemParamsRepository;

    @GetMapping(value = "/systemParams/systemParamsTableData")
    public TableEntity systemParamsTableData(HttpServletRequest request, Integer page, Integer limit) {
        TableEntity table;
        try{
            table = systemParamsServiceImpl.systemParamsTableData(page,limit);
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }


    @PostMapping(value = "/systemParams/delSystemParams")
    public JsonResult delSystemParams(HttpServletRequest request) {
        String id = request.getParameter("id");
        try{
            SystemParams systemParams = systemParamsRepository.findSystemParamsById(id);
            systemParams.setIsDelete(1);
            systemParamsRepository.save(systemParams);
            return new JsonResult("删除成功");
        }catch (Exception ex){
            return new JsonResult(new UnimaxException("删除失败"));
        }
    }


    @GetMapping(value = "/topage/systemParamsEntity")
    public ModelAndView systemParamsEntity(HttpServletRequest request) {
        String url = request.getParameter("url");
        url = url.substring(0, url.lastIndexOf("."));
        url = url.replace(".", "/");
        String id = request.getParameter("id");
        SystemParams systemParams = systemParamsRepository.findSystemParamsById(id);
        ModelAndView modelAndView= new ModelAndView(url).addObject("systemParams",systemParams);
        return modelAndView;
    }

    @PostMapping(value = "/systemParams/addSystemParams")
    public JsonResult addSystemParams(HttpServletRequest request) {
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        String value = request.getParameter("value");
        String description = request.getParameter("description");
        try{
            SystemParams systemParams = new SystemParams();
            systemParams.setCode(code);
            systemParams.setName(name);
            systemParams.setValue(value);
            systemParams.setDescription(description);
            systemParamsRepository.save(systemParams);
            return new JsonResult("添加成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException("添加失败"));
        }
    }

    @PostMapping(value = "/systemParams/editSystemParams")
    public JsonResult editSystemParams(HttpServletRequest request) {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        String value = request.getParameter("value");
        String description = request.getParameter("description");
        try{
            SystemParams systemParams = systemParamsRepository.findSystemParamsById(id);
            systemParams.setCode(code);
            systemParams.setName(name);
            systemParams.setValue(value);
            systemParams.setDescription(description);
            systemParamsRepository.save(systemParams);
            return new JsonResult("修改成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException("修改失败"));
        }
    }

}
