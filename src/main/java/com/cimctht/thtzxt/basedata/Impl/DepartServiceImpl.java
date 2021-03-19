package com.cimctht.thtzxt.basedata.Impl;

import com.alibaba.fastjson.JSONArray;
import com.cimctht.thtzxt.common.entity.TableEntity;

public interface DepartServiceImpl {

    JSONArray departAjaxLoadTree();

    TableEntity departTableData(String id, Integer page, Integer limit);

    void syncDepart();
}
