package com.cimctht.thtzxt.system.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
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

    @Override
    public JSONArray ajaxUserLoadTreeChecked(User user) {
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
    //     //获得用户已收藏的菜单
    //     List<String> listCollectids = menuRepository.queryMenuidByUserCollect(userid);
    //
    //     JSONArray resultArr = new JSONArray();
    //     JSONObject mtop = new JSONObject();
    //     mtop.put("title","<h3><i class='layui-icon layui-icon-align-left'>&nbsp;</i>菜单树</h3>");
    //     mtop.put("id","");
    //     mtop.put("spread",true);
    //     //返回的JSONArr
    //     JSONArray arr = new JSONArray();
    //     List<Menu> listTop = menuRepository.queryMenusByLeveAndIsDeleteOrderBySeq(1,0);
    //     for(Menu top : listTop){
    //         if(listMenuids.contains(top.getId())){
    //             JSONObject jsono = new JSONObject();
    //             jsono.put("title",top.getName());
    //             jsono.put("id",top.getId());
    //             jsono.put("spread",true);
    //             List<Menu> listChildren = menuRepository.queryMenusByLeveAndIsDeleteAndPmenuOrderBySeq(2,0,top);
    //
    //             if(listChildren.size()>0){
    //                 JSONArray arrChild = new JSONArray();
    //                 for(Menu child : listChildren){
    //                     if(listMenuids.contains(child.getId())){
    //                         JSONObject jsonoChild = new JSONObject();
    //                         jsonoChild.put("title",child.getName());
    //                         jsonoChild.put("id",child.getId());
    //                         if(listCollectids.contains(child.getId())){
    //                             jsonoChild.put("checked",true);
    //                         }
    //                         jsonoChild.put("spread",true);
    //                         arrChild.add(jsonoChild);
    //                     }
    //                 }
    //                 jsono.put("children",arrChild);
    //             }
    //             arr.add(jsono);
    //         }
    //     }
    //
    //     mtop.put("children",arr);
    //     resultArr.add(mtop);
        return new JSONArray();
    }


}
