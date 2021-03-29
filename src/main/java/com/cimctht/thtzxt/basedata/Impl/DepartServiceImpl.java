package com.cimctht.thtzxt.basedata.Impl;

import com.alibaba.fastjson.JSONArray;
import com.cimctht.thtzxt.common.entity.TableEntity;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
public interface DepartServiceImpl {

    JSONArray departAjaxLoadTree();

    TableEntity departTableData(String id, Integer page, Integer limit);

    void syncDepart();
}
