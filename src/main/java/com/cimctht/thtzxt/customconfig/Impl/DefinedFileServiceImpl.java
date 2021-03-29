package com.cimctht.thtzxt.customconfig.Impl;

import com.cimctht.thtzxt.common.entity.TableEntity;

import java.util.Map;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
public interface DefinedFileServiceImpl {

    TableEntity definedFileTableData(String code, String name, Integer page, Integer limit);

    TableEntity definedFileDetailTableData(String selectid, Integer page, Integer limit);

}
