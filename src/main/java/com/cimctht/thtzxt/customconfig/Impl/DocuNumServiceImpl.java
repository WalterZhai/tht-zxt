package com.cimctht.thtzxt.customconfig.Impl;

import com.cimctht.thtzxt.common.entity.TableEntity;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
public interface DocuNumServiceImpl {

    TableEntity docuNumTableData(String code, String name, Integer page, Integer limit);

}
