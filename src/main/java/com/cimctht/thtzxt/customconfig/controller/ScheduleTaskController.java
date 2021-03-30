package com.cimctht.thtzxt.customconfig.controller;

import com.cimctht.thtzxt.common.entity.JsonResult;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.exception.UnimaxException;
import com.cimctht.thtzxt.customconfig.Impl.ScheduleTaskServiceImpl;
import com.cimctht.thtzxt.customconfig.entity.DocuNum;
import com.cimctht.thtzxt.customconfig.entity.ScheduleTask;
import com.cimctht.thtzxt.customconfig.repository.ScheduleTaskRepository;
import org.activiti.engine.impl.calendar.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
@RestController
public class ScheduleTaskController {

    static final Logger logger = LoggerFactory.getLogger(ScheduleTaskController.class);

    @Autowired
    private ScheduleTaskServiceImpl scheduleTaskServiceImpl;

    @Autowired
    private ScheduleTaskRepository scheduleTaskRepository;

    @GetMapping(value = "/scheduleTask/scheduleTaskTableData")
    public TableEntity scheduleTaskTableData(HttpServletRequest request, String name, Integer page, Integer limit) {
        TableEntity table;
        try{
            table = scheduleTaskServiceImpl.scheduleTaskTableData(name,page,limit);
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }

    @GetMapping(value = "/topage/scheduleTaskEntity")
    public ModelAndView scheduleTaskEntity(HttpServletRequest request) {
        String url = request.getParameter("url");
        url = url.substring(0, url.lastIndexOf("."));
        url = url.replace(".", "/");
        String id = request.getParameter("id");
        ScheduleTask scheduleTask = scheduleTaskRepository.findScheduleTaskById(id);
        ModelAndView modelAndView= new ModelAndView(url).addObject("scheduleTask",scheduleTask);
        return modelAndView;
    }


    @PostMapping(value = "/scheduleTask/addScheduleTask")
    public JsonResult addScheduleTask(HttpServletRequest request) {
        String name = request.getParameter("name");
        String cron = request.getParameter("cron");
        String serviceFullName = request.getParameter("serviceFullName");
        String methodName = request.getParameter("methodName");
        String description = request.getParameter("description");
        try{
            ScheduleTask scheduleTask = new ScheduleTask();
            scheduleTask.setName(name);
            scheduleTask.setCron(cron);
            scheduleTask.setServiceFullName(serviceFullName);
            scheduleTask.setServiceName(serviceFullName.substring(serviceFullName.lastIndexOf(".")+1));
            scheduleTask.setMethodName(methodName);
            scheduleTask.setIsopen(1);
            scheduleTask.setDescription(description);
            scheduleTaskRepository.save(scheduleTask);
            return new JsonResult("添加成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException("添加失败"));
        }
    }

    @PostMapping(value = "/scheduleTask/editScheduleTask")
    public JsonResult editScheduleTask(HttpServletRequest request) {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String cron = request.getParameter("cron");
        String serviceFullName = request.getParameter("serviceFullName");
        String methodName = request.getParameter("methodName");
        String description = request.getParameter("description");
        try{
            ScheduleTask scheduleTask = scheduleTaskRepository.findScheduleTaskById(id);
            scheduleTask.setName(name);
            scheduleTask.setCron(cron);
            scheduleTask.setServiceFullName(serviceFullName);
            scheduleTask.setServiceName(serviceFullName.substring(serviceFullName.lastIndexOf(".")+1));
            scheduleTask.setMethodName(methodName);
            scheduleTask.setDescription(description);
            scheduleTaskRepository.save(scheduleTask);
            return new JsonResult("修改成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException("修改失败"));
        }
    }


    @PostMapping(value = "/scheduleTask/delScheduleTask")
    public JsonResult delScheduleTask(HttpServletRequest request) {
        String id = request.getParameter("id");
        try{
            ScheduleTask scheduleTask = scheduleTaskRepository.findScheduleTaskById(id);
            scheduleTask.setIsDelete(1);
            scheduleTaskRepository.save(scheduleTask);
            return new JsonResult("删除成功");
        }catch (Exception ex){
            return new JsonResult(new UnimaxException("删除失败"));
        }
    }

    @PostMapping(value = "/scheduleTask/startScheduleTask")
    public JsonResult startScheduleTask(HttpServletRequest request) {
        String id = request.getParameter("id");
        try{
            ScheduleTask scheduleTask = scheduleTaskRepository.findScheduleTaskById(id);
            scheduleTaskServiceImpl.startScheduleTask(scheduleTask);
            scheduleTask.setIsopen(0);
            scheduleTaskRepository.save(scheduleTask);
            return new JsonResult("开始成功");
        }catch (Exception ex){
            return new JsonResult(new UnimaxException(ex.getMessage()));
        }
    }

    @PostMapping(value = "/scheduleTask/stopScheduleTask")
    public JsonResult stopScheduleTask(HttpServletRequest request) {
        String id = request.getParameter("id");
        try{
            ScheduleTask scheduleTask = scheduleTaskRepository.findScheduleTaskById(id);
            scheduleTaskServiceImpl.stopScheduleTask(scheduleTask);
            scheduleTask.setIsopen(1);
            scheduleTaskRepository.save(scheduleTask);
            return new JsonResult("已停止");
        }catch (Exception ex){
            return new JsonResult(new UnimaxException(ex.getMessage()));
        }
    }


    @PostMapping(value = "/scheduleTask/executeScheduleTask")
    public JsonResult executeScheduleTask(HttpServletRequest request) {
        String id = request.getParameter("id");
        try{
            ScheduleTask scheduleTask = scheduleTaskRepository.findScheduleTaskById(id);
            scheduleTaskServiceImpl.executeScheduleTask(scheduleTask);
            return new JsonResult("执行完成");
        }catch (Exception ex){
            return new JsonResult(new UnimaxException(ex.getMessage()));
        }
    }

}
