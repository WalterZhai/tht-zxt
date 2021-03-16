package com.cimctht.thtzxt.system.Impl;


import com.alibaba.fastjson.JSONArray;
import com.cimctht.thtzxt.system.entity.Menu;
import com.cimctht.thtzxt.system.entity.User;

import java.util.List;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
public interface MenuServiceImpl {

    List<Menu> selectLoginMenu(User user);

    List<String> loadSearchInfo(String info,User user);

    JSONArray ajaxUserLoadTreeChecked(User user);

}
