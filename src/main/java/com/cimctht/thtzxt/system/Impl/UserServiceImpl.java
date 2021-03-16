package com.cimctht.thtzxt.system.Impl;

import com.cimctht.thtzxt.common.entity.TableEntity;

public interface UserServiceImpl {

    TableEntity queryUsersByLikeName(String name, Integer page, Integer limit);

}
