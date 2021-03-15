package com.cimctht.thtzxt.system.service;

import com.cimctht.thtzxt.system.Impl.MenuServiceImpl;
import com.cimctht.thtzxt.system.entity.Menu;
import com.cimctht.thtzxt.system.entity.Role;
import com.cimctht.thtzxt.system.entity.User;
import com.cimctht.thtzxt.system.repository.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class MenuService implements MenuServiceImpl {

    static final Logger logger = LoggerFactory.getLogger(MenuService.class);

    @Autowired
    private MenuRepository menuRepository;


    @Override
    public List<Menu> selectLoginMenu(User user) {
        List<Menu> menus =  menuRepository.findMenusByIsDeleteAndParentMenu(0,null);
        //获得所有授权的菜单id
        List<String> ids = new ArrayList<>();
        for(Role role : user.getRoles()){
            for(Menu menu : role.getMenus()){
                if(!ids.contains(menu.getId())){
                    ids.add(menu.getId());
                }
            }
        }
        //遍历所有菜单，不在ids内的去除
        for(Menu menu : menus){
            if(ids.contains(menu.getId())){
                recursiveExclusion(menu,ids);
            }
        }
        return menus;
    }

    //递归排除
    public Menu recursiveExclusion(Menu menu,List<String> ids){
        if(menu.getChildMenus().size()>0){
            for(Menu child : menu.getChildMenus()){
                if(!ids.contains(child.getId())){
                    menu.getChildMenus().remove(child);
                }else{
                    recursiveExclusion(child,ids);
                }
            }
        }
        return menu;
    }

    @Override
    public List<String> loadSearchInfo(String info, User user) {
        //所有用户能看到的菜单
        List<Menu> menuList = new ArrayList<>();
        List<Role> roleList = user.getRoles();
        for(Role role : roleList){
            for(Menu menu : role.getMenus()){
                if(!menuList.contains(menu)){
                    menuList.add(menu);
                }
            }
        }
        //包含info信息的菜单
        List<String> mls = new ArrayList<>();
        for(Menu menu : menuList){
            if(menu.getName().indexOf(info)>-1){
                mls.add(menu.getName()+"("+menu.getCode()+")");
            }
        }
        return mls;
    }



}
