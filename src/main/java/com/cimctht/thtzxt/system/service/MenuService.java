package com.cimctht.thtzxt.system.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.utils.MathsUtils;
import com.cimctht.thtzxt.system.Impl.MenuServiceImpl;
import com.cimctht.thtzxt.system.bo.SimpleMenuBo;
import com.cimctht.thtzxt.system.entity.Menu;
import com.cimctht.thtzxt.system.entity.Role;
import com.cimctht.thtzxt.system.entity.User;
import com.cimctht.thtzxt.system.repository.MenuRepository;
import com.cimctht.thtzxt.system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        List<Menu> result = new ArrayList<>();
        for(Menu menu : menus){
            if(ids.contains(menu.getId())){
                result.add(recursiveExclusion(menu,ids));
            }
        }
        return result;
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
        object.put("spread",false);
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

    @Override
    public JSONArray ajaxLoadTree() {
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
            JSONObject object = recursiveMenu(menu);
            arr.add(object);
        }
        virtualTop.put("children",arr);
        result.add(virtualTop);
        return result;
    }

    //递归建模
    public JSONObject recursiveMenu(Menu menu){
        JSONObject object = new JSONObject();
        object.put("title",menu.getName());
        object.put("id",menu.getId());
        object.put("spread",false);
        if(menu.getChildMenus().size()>0){
            JSONArray children = new JSONArray();
            for(Menu child : menu.getChildMenus()){
                JSONObject obj = recursiveMenu(child);
                children.add(obj);
            }
            object.put("children",children);
        }
        return object;
    }

    @Override
    public TableEntity menuTableData(String id, Integer page, Integer limit) {
        Menu parent = menuRepository.findMenuById(id);
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<Menu> pages = menuRepository.findMenusByIsDeleteAndParentMenuOrderBySeq(0,parent,pageable);
        List<Menu> list = pages.getContent();
        List<SimpleMenuBo> result = new ArrayList<>();
        for(Menu menu : list){
            result.add(new SimpleMenuBo(menu));
        }
        return new TableEntity(result, MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }

    @Override
    public void rowUp(String menuId) {
        Menu menu = menuRepository.findMenuById(menuId);
        //找到父菜单
        Menu parentMenu = menu.getParentMenu();
        //原本位置大于1,才进行修改
        if(menu.getSeq()>1){
            Integer target = menu.getSeq()-1;
            //原本位置上的
            Menu exist = menuRepository.findMenusByIsDeleteAndSeqAndParentMenuOrderBySeq(0,target,parentMenu);
            exist.setSeq(menu.getSeq());
            menuRepository.save(exist);
            menu.setSeq(target);
            menuRepository.save(menu);
        }
    }

    @Override
    public void rowDown(String menuId) {
        Menu menu = menuRepository.findMenuById(menuId);
        //找到父菜单
        Menu parentMenu = menu.getParentMenu();
        //找到同父菜单下的所有子菜单
        List<Menu> children = menuRepository.findMenusByIsDeleteAndParentMenuOrderBySeq(0,parentMenu);
        Integer last = children.get(children.size()-1).getSeq();
        if(menu.getSeq()<last){
            Integer target = menu.getSeq()+1;
            //原本位置上的
            Menu exist = menuRepository.findMenusByIsDeleteAndSeqAndParentMenuOrderBySeq(0,target,parentMenu);
            exist.setSeq(menu.getSeq());
            menuRepository.save(exist);
            menu.setSeq(target);
            menuRepository.save(menu);
        }

    }

    @Override
    public void sortChildrenSeq(Menu parentMenu) {
        List<Menu> children;
        if(parentMenu==null){
            children = menuRepository.findMenusByIsDeleteAndParentMenuOrderBySeq(0,null);
        }else{
            children = parentMenu.getChildMenus();
        }
        Integer seq = 1;
        for(Menu child : children){
            child.setSeq(seq);
            seq += 1;
        }
        menuRepository.saveAll(children);
    }

}
