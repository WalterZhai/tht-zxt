package com.cimctht.thtzxt.system.repository;

import com.cimctht.thtzxt.system.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Transactional
public interface GroupRepository extends JpaRepository<Group,String> {


    Group findGroupById(String id);

    @Query(nativeQuery = true, value =" select GROUP_CODE_SEQ.nextval from dual ")
    BigInteger queryCodeSeqNext();

    Page<Group> findGroupsByIsDeleteAndCodeLikeAndNameLikeOrderByCode(Integer isDelete, String code, String name, Pageable pageable);
}
