package com.cimctht.thtzxt.system.repository;

import com.cimctht.thtzxt.system.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Transactional
public interface GroupRepository extends JpaRepository<Group,String> {


    Group findGroupById(String id);

}
