package com.cimctht.thtzxt.system.repository;

import com.cimctht.thtzxt.system.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;

public interface RoleRepository extends JpaRepository<Role,String> {

    Role findRoleByCodeAndIsDelete(String code, Integer isDelete);

    @Query(nativeQuery = true, value =" select ROLE_CODE_SEQ.nextval from dual ")
    BigInteger queryCodeSeqNext();

}
