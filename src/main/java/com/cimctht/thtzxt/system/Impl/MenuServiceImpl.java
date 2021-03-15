package com.cimctht.thtzxt.system.Impl;


import com.cimctht.thtzxt.system.entity.Menu;
import com.cimctht.thtzxt.system.entity.User;

import java.util.List;

public interface MenuServiceImpl {

    List<Menu> selectLoginMenu(User user);

    List<String> loadSearchInfo(String info,User user);

}
