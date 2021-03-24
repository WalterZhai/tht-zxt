package com.cimctht.thtzxt.customconfig.Impl;

import com.alibaba.fastjson.JSONArray;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.customconfig.entity.Message;
import com.cimctht.thtzxt.system.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
public interface MessageServiceImpl {

    TableEntity messageTableData(String title, Integer type, Integer isSend, Integer page, Integer limit);

    Map<String,Object> laodTrans();

    void addMessageInfo(String title, String content, Integer type, JSONArray arr, String username);

    void editMessage(String id, String title, String content);

    void delMessages(List<Message> list);

    void delMessage(Message message);

    void sendMessage(List<Message> list);

    void cancelMessage(List<Message> list);

    Integer inspectMessage(String username);

    TableEntity tableDataMessageInfo(String username, Integer page, Integer limit);

    void messageRead(String id);

    void messageReadAll(String username);

}
