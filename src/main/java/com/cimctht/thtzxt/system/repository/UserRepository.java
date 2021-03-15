package com.cimctht.thtzxt.system.repository;

import com.cimctht.thtzxt.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User,String> {

    User findUserByLoginNameAndIsDelete(String loginName, Integer isDelete);

    User findUserByLoginNameAndIsLockedAndIsDelete(String loginName, Integer isLocked, Integer isDelete);
}
