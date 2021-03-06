package com.cimctht.thtzxt.system.repository;

import com.cimctht.thtzxt.system.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Transactional
public interface UserRepository extends JpaRepository<User,String> {

    User findUserByLoginNameAndIsDelete(String loginName, Integer isDelete);

    User findUserByLoginNameAndIsLockedAndIsDelete(String loginName, Integer isLocked, Integer isDelete);

    Page<User> findUsersByIsDeleteAndLoginNameLikeAndNameLikeOrderByCreateDate(Integer isDelete, String loginName, String name, Pageable pageable);

    User findUserById(String id);

    List<User> findUsersByIsDelete(Integer isDelete);

    @Query(nativeQuery = true, value =" SELECT name FROM sys_user WHERE login_name=?1 ")
    String queryUserNameByUserCode(String userCode);
}
