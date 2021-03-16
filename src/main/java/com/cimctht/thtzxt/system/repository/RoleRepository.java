package com.cimctht.thtzxt.system.repository;

import com.cimctht.thtzxt.system.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Transactional
public interface RoleRepository extends JpaRepository<Role,String> {

    Role findRoleByCodeAndIsDelete(String code, Integer isDelete);

    @Query(nativeQuery = true, value =" select ROLE_CODE_SEQ.nextval from dual ")
    BigInteger queryCodeSeqNext();

    List<Role> findRolesByIsDelete(Integer isDelete);

    Role findRoleById(String id);

}
