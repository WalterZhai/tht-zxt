package com.cimctht.thtzxt.customconfig.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.utils.MathsUtils;
import com.cimctht.thtzxt.common.utils.StringUtils;
import com.cimctht.thtzxt.customconfig.Impl.MessageServiceImpl;
import com.cimctht.thtzxt.customconfig.entity.Message;
import com.cimctht.thtzxt.customconfig.entity.MessageInfo;
import com.cimctht.thtzxt.customconfig.repository.MessageInfoRepository;
import com.cimctht.thtzxt.customconfig.repository.MessageRepository;
import com.cimctht.thtzxt.system.entity.Group;
import com.cimctht.thtzxt.system.entity.Role;
import com.cimctht.thtzxt.system.entity.User;
import com.cimctht.thtzxt.system.repository.GroupRepository;
import com.cimctht.thtzxt.system.repository.RoleRepository;
import com.cimctht.thtzxt.system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
@Service
public class MessageService implements MessageServiceImpl {

    static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private MessageInfoRepository messageInfoRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @PersistenceContext(unitName = "unimaxPersistenceUnit")
    private EntityManager unimaxEntityManager;

    @Override
    public TableEntity messageTableData(String title, Integer type, Integer isSend, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Message> pages;
        if (type == null && isSend == null) {
            pages = messageRepository.findMessagesByIsDeleteAndTitleLikeOrderByCreateDateDesc(0, StringUtils.string2LikeParam(title), pageable);
        } else if (type == null) {
            pages = messageRepository.findMessagesByIsDeleteAndTitleLikeAndIsSendOrderByCreateDateDesc(0, StringUtils.string2LikeParam(title), isSend, pageable);
        } else if (isSend == null) {
            pages = messageRepository.findMessagesByIsDeleteAndTitleLikeAndIsTypeOrderByCreateDateDesc(0, StringUtils.string2LikeParam(title), type, pageable);
        } else {
            pages = messageRepository.findMessagesByIsDeleteAndTitleLikeAndIsTypeAndIsSendOrderByCreateDateDesc(0, StringUtils.string2LikeParam(title), type, isSend, pageable);
        }
        return new TableEntity(pages.getContent(), MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }

    @Override
    public Map<String, Object> laodTrans() {
        Map<String, Object> result = new HashMap<String, Object>();

        //加载所有角色
        List<Role> listRoles = roleRepository.findRolesByIsDelete(0);
        //加载所有用户
        List<User> listUsers = userRepository.findUsersByIsDelete(0);
        //加载所有用户组
        List<Group> listGroups = groupRepository.findGroupsByIsDelete(0);

        JSONArray arr1 = new JSONArray();
        for(Role r : listRoles){
            JSONObject o = new JSONObject();
            o.put("value",r.getId());
            o.put("title",r.getName());
            o.put("disabled","");
            o.put("checked","");
            arr1.add(o);
        }
        result.put("roles",arr1);

        JSONArray arr2 = new JSONArray();
        for(User r : listUsers){
            JSONObject o = new JSONObject();
            o.put("value",r.getId());
            o.put("title",r.getName());
            o.put("disabled","");
            o.put("checked","");
            arr2.add(o);
        }
        result.put("users",arr2);

        JSONArray arr3 = new JSONArray();
        for(Group r : listGroups){
            JSONObject o = new JSONObject();
            o.put("value",r.getId());
            o.put("title",r.getName());
            o.put("disabled","");
            o.put("checked","");
            arr3.add(o);
        }
        result.put("groups",arr3);

        return result;
    }

    @Override
    @Transactional
    public void addMessageInfo(String title, String content, Integer type, JSONArray arr, String username) {
        User sendMan = userRepository.findUserByLoginNameAndIsDelete(username,0);
        Message message = new Message();
        message.setCode(sendMan.getLoginName());
        message.setName(sendMan.getName());
        message.setTitle(title);
        message.setContent(content);
        message.setIsType(type);
        message.setIsSend(0);
        message = messageRepository.save(message);

        List<MessageInfo> listInfo = new ArrayList<>();
        if(type==0){
            List<User> users = userRepository.findUsersByIsDelete(0);
            for(User user : users){
                MessageInfo messageInfo = new MessageInfo();
                messageInfo.setUserCode(user.getLoginName());
                messageInfo.setIsRead(0);
                messageInfo.setIsSend(0);
                messageInfo.setMessage(message);
                listInfo.add(messageInfo);
            }
        }else if(type==1){
            List<String> userCodes = new ArrayList<>();
            for(int i=0;i<arr.size();i++){
                Role role = roleRepository.findRoleById(arr.getJSONObject(i).getString("value"));
                for(User user : role.getUsers()){
                    if(!userCodes.contains(user.getLoginName())){
                        userCodes.add(user.getLoginName());
                    }
                }
            }
            for(String code : userCodes){
                MessageInfo messageInfo = new MessageInfo();
                messageInfo.setUserCode(code);
                messageInfo.setIsRead(0);
                messageInfo.setIsSend(0);
                messageInfo.setMessage(message);
                listInfo.add(messageInfo);
            }
        }else if(type==2){
            List<String> userCodes = new ArrayList<>();
            for(int i=0;i<arr.size();i++){
                User user = userRepository.findUserById(arr.getJSONObject(i).getString("value"));
                if(!userCodes.contains(user.getLoginName())){
                    userCodes.add(user.getLoginName());
                }
            }
            for(String code : userCodes){
                MessageInfo messageInfo = new MessageInfo();
                messageInfo.setUserCode(code);
                messageInfo.setIsRead(0);
                messageInfo.setIsSend(0);
                messageInfo.setMessage(message);
                listInfo.add(messageInfo);
            }
        }else if(type==3){
            List<String> userCodes = new ArrayList<>();
            for(int i=0;i<arr.size();i++){
                Group group = groupRepository.findGroupById(arr.getJSONObject(i).getString("value"));
                for(User user : group.getUsers()){
                    if(!userCodes.contains(user.getLoginName())){
                        userCodes.add(user.getLoginName());
                    }
                }
            }
            for(String code : userCodes){
                MessageInfo messageInfo = new MessageInfo();
                messageInfo.setUserCode(code);
                messageInfo.setIsRead(0);
                messageInfo.setIsSend(0);
                messageInfo.setMessage(message);
                listInfo.add(messageInfo);
            }
        }
        messageInfoRepository.saveAll(listInfo);
    }

    @Override
    public void editMessage(String id, String title, String content) {
        Message message = messageRepository.findMessageById(id);
        message.setTitle(title);
        message.setContent(content);
        messageRepository.save(message);
    }

    @Override
    public void delMessages(List<Message> list) {
        for(Message message : list){
            message.setIsDelete(1);
            List<MessageInfo> infos =  messageInfoRepository.findMessageInfosByIsDeleteAndMessage(0,message);
            for(MessageInfo messageInfo : infos){
                messageInfo.setIsDelete(1);
            }
            messageInfoRepository.saveAll(infos);
        }
        messageRepository.saveAll(list);
    }

    @Override
    public void delMessage(Message message) {
        message.setIsDelete(1);
        List<MessageInfo> infos =  messageInfoRepository.findMessageInfosByIsDeleteAndMessage(0,message);
        for(MessageInfo messageInfo : infos){
            messageInfo.setIsDelete(1);
        }
        messageInfoRepository.saveAll(infos);
        messageRepository.save(message);
    }

    @Override
    public void sendMessage(List<Message> list) {
        for(Message message : list){
            if(message.getIsSend()==1){
                continue;
            }
            message.setIsSend(1);
            List<MessageInfo> infos = messageInfoRepository.findMessageInfosByIsDeleteAndMessage(0,message);
            for(MessageInfo messageInfo : infos){
                messageInfo.setIsSend(1);
            }
            messageInfoRepository.saveAll(infos);
        }
        messageRepository.saveAll(list);
    }

    @Override
    public void cancelMessage(List<Message> list) {
        for(Message message : list){
            if(message.getIsSend()==0){
                continue;
            }
            message.setIsSend(0);
            List<MessageInfo> infos =  messageInfoRepository.findMessageInfosByIsDeleteAndMessage(0,message);
            for(MessageInfo messageInfo : infos){
                messageInfo.setIsSend(0);
            }
            messageInfoRepository.saveAll(infos);
        }
        messageRepository.saveAll(list);
    }

    @Override
    public Integer inspectMessage(String username) {
        List<MessageInfo> list = messageInfoRepository.findMessageInfosByIsDeleteAndUserCodeAndIsSendAndIsRead(0,username,1, 0);
        return list.size();
    }

    @Override
    public TableEntity tableDataMessageInfo(String username,Integer page,Integer limit) {
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<MessageInfo> pages = messageInfoRepository.findMessageInfosByIsDeleteAndIsSendAndUserCodeOrderByIsReadAscCreateDateDesc(0, 1, username, pageable);
        return new TableEntity(pages.getContent(), MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }

    @Override
    public void messageRead(String id) {
        MessageInfo messageInfo = messageInfoRepository.findMessageInfoById(id);
        if(messageInfo.getIsRead()==0){
            messageInfo.setIsRead(1);
            messageInfoRepository.save(messageInfo);
        }
    }

    @Override
    public void messageReadAll(String username) {
        List<MessageInfo> list = messageInfoRepository.findMessageInfosByIsDeleteAndUserCodeAndIsSendAndIsRead(0, username,1,0);
        for(MessageInfo messageInfo : list){
            messageInfo.setIsRead(1);
        }
        messageInfoRepository.saveAll(list);
    }


}
