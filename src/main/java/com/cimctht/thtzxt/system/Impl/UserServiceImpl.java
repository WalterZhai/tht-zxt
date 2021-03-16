package com.cimctht.thtzxt.system.Impl;

import com.cimctht.thtzxt.common.entity.TableEntity;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
public interface UserServiceImpl {

    TableEntity queryUsersByLikeName(String name, Integer page, Integer limit);

    TableEntity onlineUserData(Integer page, Integer limit);

    void editPassword(String username,String pwd1,String pwd2,String pwd3);

}
