package com.cimctht.thtzxt.system.repository;

import com.cimctht.thtzxt.system.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
public interface MenuRepository extends JpaRepository<Menu,String> {

    Menu findMenuByCodeAndIsDelete(String code,Integer isDelete);

    @Query(nativeQuery = true, value =" select MENU_CODE_SEQ.nextval from dual ")
    BigInteger queryCodeSeqNext();

    List<Menu> findMenusByIsDeleteAndParentMenu(Integer isDelete, Menu menu);

}