package com.cimctht.thtzxt.system.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cimctht.thtzxt.system.Impl.MenuServiceImpl;
import com.cimctht.thtzxt.system.entity.Menu;
import com.cimctht.thtzxt.system.entity.Role;
import com.cimctht.thtzxt.system.entity.User;
import com.cimctht.thtzxt.system.repository.MenuRepository;
import com.cimctht.thtzxt.system.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Menu> selectLoginMenu(User user) {
        List<Menu> menus =  menuRepository.findMenusByIsDeleteAndParentMenuOrderBySeq(0,null);
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
    public List<String> loadSearchInfo(String info, String username) {
        User user = userRepository.findUserByLoginNameAndIsDelete(username,0);
        //所有用户能看到的菜单
        List<Menu> menuList = new ArrayList<>();
        List<Role> roleList = user.getRoles();
        for(Role role : roleList){
            for(Menu menu : role.getMenus()){
                if(!StrUtil.hasEmpty(menu.getHref()) && !menuList.contains(menu)){
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
    public JSONArray ajaxUserLoadTreeChecked(String username) {
        User user = userRepository.findUserByLoginNameAndIsDelete(username,0);
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
        //获得用户已收藏的菜单
        List<Menu> collectList = user.getCollects();

        //最终返回
        JSONArray result = new JSONArray();
        //虚拟顶层
        JSONObject virtualTop = new JSONObject();
        virtualTop.put("title","<h3><i class='layui-icon layui-icon-align-left'>&nbsp;</i>菜单树</h3>");
        virtualTop.put("id","");
        virtualTop.put("spread",true);
        //实际菜单层级
        JSONArray arr = new JSONArray();
        List<Menu> menus =  menuRepository.findMenusByIsDeleteAndParentMenuOrderBySeq(0,null);
        for(Menu menu : menus){
            if(menuList.contains(menu)){
                JSONObject object = recursiveModel(menu,menuList,collectList);
                arr.add(object);
            }
        }
        virtualTop.put("children",arr);
        result.add(virtualTop);
        return result;
    }

    //递归建模
    public JSONObject recursiveModel(Menu menu,List<Menu> menuList,List<Menu> collectList){
        JSONObject object = new JSONObject();
        object.put("title",menu.getName());
        object.put("id",menu.getId());
        object.put("spread",true);
        if(collectList.contains(menu)){
            object.put("checked",true);
        }
        if(menu.getChildMenus().size()>0){
            JSONArray children = new JSONArray();
            for(Menu child : menu.getChildMenus()){
                if(menuList.contains(child)){
                    JSONObject obj = recursiveModel(child,menuList,collectList);
                    children.add(obj);
                }
            }
            object.put("children",children);
        }
        return object;
    }


    @Override
    public void saveMenuCollect(JSONArray data, String username) {
        User user = userRepository.findUserByLoginNameAndIsDelete(username,0);
        //最终收藏菜单
        List<String> resultIds = new ArrayList<>();
        //先保存传进来的菜单
        resultIds = getResultIds(data,resultIds);
        List<Menu> list = menuRepository.findMenusByIsDeleteAndIdIn(0,resultIds);
        user.setCollects(list);
        userRepository.save(user);
    }

    public List<String> getResultIds(JSONArray data,List<String> resultIds){
        for(int i=0;i<data.size();i++) {
            JSONObject object = data.getJSONObject(i);
            JSONArray children = (JSONArray) object.get("children");
            if(children==null){
                resultIds.add(object.getString("id"));
            }
            if(children!=null && children.size()>0){
                getResultIds(children,resultIds);
            }
        }
        return resultIds;
    }

}
