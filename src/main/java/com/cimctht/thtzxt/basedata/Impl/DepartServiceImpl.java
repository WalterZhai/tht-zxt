package com.cimctht.thtzxt.basedata.Impl;

import com.alibaba.fastjson.JSONArray;
import com.cimctht.thtzxt.common.entity.TableEntity;

public interface DepartServiceImpl {

    JSONArray ajaxLoadTree();

    TableEntity queryMenusByIsDeleteAndPmenu(String id, Integer page, Integer limit);

    void addDeaprt(String id, String code, String name, String uda1);

    void editDeaprt(String id, String code, String name, String uda1);

    void syncDepart();
}
