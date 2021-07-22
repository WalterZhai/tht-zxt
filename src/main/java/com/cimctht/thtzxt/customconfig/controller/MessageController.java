package com.cimctht.thtzxt.customconfig.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cimctht.thtzxt.common.distributedlock.CacheLock;
import com.cimctht.thtzxt.common.entity.JsonResult;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.exception.UnimaxException;
import com.cimctht.thtzxt.customconfig.Impl.MessageServiceImpl;
import com.cimctht.thtzxt.customconfig.entity.Message;
import com.cimctht.thtzxt.customconfig.entity.MessageInfo;
import com.cimctht.thtzxt.customconfig.repository.MessageInfoRepository;
import com.cimctht.thtzxt.customconfig.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
@RestController
public class MessageController {

    static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageServiceImpl messageServiceImpl;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageInfoRepository messageInfoRepository;

    @GetMapping(value = "/message/messageTableData")
    public TableEntity messageTableData(HttpServletRequest request, String title, Integer type, Integer isSend, Integer page, Integer limit) {
        TableEntity table;
        try{
            table = messageServiceImpl.messageTableData(title,type, isSend,page,limit);
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }

    @PostMapping(value = "/message/laodTrans")
    public JsonResult laodTrans(HttpServletRequest request) {
        try{
            Map<String,Object> map = messageServiceImpl.laodTrans();
            return new JsonResult(map);
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @CacheLock(prefix = "/message/addMessageInfo")
    @PostMapping(value = "/message/addMessageInfo")
    public JsonResult addMessageInfo(HttpServletRequest request,Integer type) {
        String username = (String) request.getSession().getAttribute("username");
        String title = request.getParameter("frm[title]");
        String content = request.getParameter("frm[content]");
        String data = request.getParameter("data");
        JSONArray arr = JSON.parseArray(data);
        try{
            messageServiceImpl.addMessageInfo(title,content,type,arr,username);
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/message/delMessages")
    public JsonResult delMessages(HttpServletRequest request) {
        String arrs = request.getParameter("arrs");
        List<Message> list = JSON.parseArray(arrs, Message.class);
        try{
            messageServiceImpl.delMessages(list);
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/message/delMessage")
    public JsonResult delMessage(HttpServletRequest request) {
        String id = request.getParameter("id");
        try{

            Message message = messageRepository.findMessageById(id);
            messageServiceImpl.delMessage(message);
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @CacheLock(prefix = "/message/editMessage")
    @PostMapping(value = "/message/editMessage")
    public JsonResult editMessage(HttpServletRequest request,String id,String title,String content) {
        try{
            messageServiceImpl.editMessage(id,title,content);
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/message/sendMessage")
    public JsonResult sendMessage(HttpServletRequest request) {
        String arrs = request.getParameter("arrs");
        List<Message> list = JSON.parseArray(arrs, Message.class);
        try{
            messageServiceImpl.sendMessage(list);
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/message/cancelMessage")
    public JsonResult cancelMessage(HttpServletRequest request) {
        String arrs = request.getParameter("arrs");
        List<Message> list = JSON.parseArray(arrs, Message.class);
        try{
            messageServiceImpl.cancelMessage(list);
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/message/inspectMessage")
    public JsonResult inspectMessage(HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        try{
            return new JsonResult(messageServiceImpl.inspectMessage(username));
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }


    @GetMapping(value = "/message/tableDataMessageInfo")
    public TableEntity tableDataMessageInfo(HttpServletRequest request,Integer page,Integer limit) {
        String username = (String) request.getSession().getAttribute("username");
        TableEntity table;
        try{
            table = messageServiceImpl.tableDataMessageInfo(username,page,limit);
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }

    @PostMapping(value = "/message/messageRead")
    public JsonResult messageRead(HttpServletRequest request) {
        String id = request.getParameter("id");
        try{
            messageServiceImpl.messageRead(id);
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/message/messageReadAll")
    public JsonResult messageReadAll(HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        try{
            messageServiceImpl.messageReadAll(username);
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/message/feedback")
    public JsonResult feedback(HttpServletRequest request,Integer type) {
        String username = (String) request.getSession().getAttribute("username");
        String feedback = request.getParameter("feedback");
        try{
            messageServiceImpl.messageFeedback(username,feedback);
            return new JsonResult("已反馈");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @GetMapping(value = "/message/messageUserTableData")
    public TableEntity messageUserTableData(HttpServletRequest request, String id, String code, Integer page, Integer limit) {
        TableEntity table;
        try{
            table = messageServiceImpl.messageUserTableData(id, code, page, limit);
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }

    @PostMapping(value = "/message/delMessageInfos")
    public JsonResult delMessageInfos(HttpServletRequest request) {
        String arrs = request.getParameter("arrs");
        List<String> ids = JSON.parseArray(arrs, String.class);
        try{
            List<MessageInfo> list = messageInfoRepository.findMessageInfosByIdIn(ids);
            for(MessageInfo messageInfo : list){
                messageInfo.setIsDelete(1);
            }
            messageInfoRepository.saveAll(list);
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

}
