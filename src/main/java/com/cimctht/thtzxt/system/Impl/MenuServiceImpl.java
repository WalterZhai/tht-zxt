package com.cimctht.thtzxt.system.Impl;


import com.alibaba.fastjson.JSONArray;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.system.entity.Menu;
import com.cimctht.thtzxt.system.entity.User;

import java.util.List;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
public interface MenuServiceImpl {

    List<Menu> selectLoginMenu(User user);

    List<String> loadSearchInfo(String info,String username);

    JSONArray ajaxUserLoadTreeChecked(String username);

    void saveMenuCollect(JSONArray data,String username);

    JSONArray ajaxLoadTree();

    TableEntity menuTableData(String id, Integer page, Integer limit);

    void rowUp(String menuId);

    void rowDown(String menuId);

    void sortChildrenSeq(Menu parentMenu);
}
