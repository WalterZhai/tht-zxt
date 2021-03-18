package com.cimctht.thtzxt.system.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.utils.MathsUtils;
import com.cimctht.thtzxt.common.utils.StringUtils;
import com.cimctht.thtzxt.system.Impl.GroupServiceImpl;
import com.cimctht.thtzxt.system.bo.SimpleGroupBo;
import com.cimctht.thtzxt.system.bo.SimpleUserBo;
import com.cimctht.thtzxt.system.controller.GroupController;
import com.cimctht.thtzxt.system.entity.Group;
import com.cimctht.thtzxt.system.entity.User;
import com.cimctht.thtzxt.system.repository.GroupRepository;
import com.cimctht.thtzxt.system.repository.UserRepository;
import org.apache.axis2.util.ArrayStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Service
public class GroupService implements GroupServiceImpl {

    static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TableEntity roleTableData(String code, String name, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<Group> pages = groupRepository.findGroupsByIsDeleteAndCodeLikeAndNameLikeOrderByCode(0, StringUtils.string2LikeParam(code),StringUtils.string2LikeParam(name),pageable);
        List<Group> list = pages.getContent();
        List<SimpleGroupBo> result = new ArrayList<>();
        for(Group group : list){
            result.add(new SimpleGroupBo(group));
        }
        return new TableEntity(result, MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }

    @Override
    public TableEntity groupDetailTableData(String selectid, Integer page, Integer limit) {
        Group group = groupRepository.findGroupById(selectid);
        if(group!=null){
            List<User> users = group.getUsers();
            List<SimpleUserBo> result = new ArrayList<>();
            Integer begin = (page-1)*limit;
            Integer end = page*limit;
            if(end>users.size()){
                end = users.size();
            }
            for(int i=begin;i<end;i++){
                result.add(new SimpleUserBo(users.get(i)));
            }
            return new TableEntity(result, MathsUtils.convertInteger2BigDecimal(users.size()));
        }else{
            return new TableEntity(new ArrayList<>(), BigDecimal.ZERO);
        }
    }

    @Override
    public Map<String, Object> ajaxLoadTransferGroupRelUser(String groupid) {
        Group group = groupRepository.findGroupById(groupid);
        Map<String, Object> result = new HashMap<>();

        //已属于用户组的用户
        List<User> userExist = group.getUsers();
        List<User> userAll = userRepository.findUsersByIsDelete(0);
        //data值
        JSONArray arr = new JSONArray();
        for(User user : userAll){
            JSONObject o = new JSONObject();
            o.put("value",user.getId());
            o.put("title",user.getName());
            o.put("disabled","");
            o.put("checked","");
            arr.add(o);
        }
        result.put("data",arr);
        //value值
        List<String> listids = new ArrayList<>();
        for(User user : userExist){
            listids.add(user.getId());
        }
        result.put("value",listids);
        return result;
    }
}
