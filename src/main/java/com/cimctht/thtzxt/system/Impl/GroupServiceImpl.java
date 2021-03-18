package com.cimctht.thtzxt.system.Impl;

import com.cimctht.thtzxt.common.entity.TableEntity;

import java.util.Map;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
public interface GroupServiceImpl {

    TableEntity roleTableData(String code,String name,Integer page,Integer limit);

    TableEntity groupDetailTableData(String selectid,Integer page,Integer limit);

    Map<String,Object> ajaxLoadTransferGroupRelUser(String groupid);

}
