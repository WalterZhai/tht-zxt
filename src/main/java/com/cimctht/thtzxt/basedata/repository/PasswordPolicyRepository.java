package com.cimctht.thtzxt.basedata.repository;

import com.cimctht.thtzxt.basedata.entity.PasswordPolicy;
import com.cimctht.thtzxt.basedata.entity.SystemParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
public interface PasswordPolicyRepository extends JpaRepository<PasswordPolicy,String> {

    Page<PasswordPolicy> findPasswordPoliciesByIsDelete(Integer isDelete, Pageable pageable);

    PasswordPolicy findPasswordPolicyById(String id);

    PasswordPolicy findPasswordPolicyByIsDeleteAndIsUsed(Integer isDelete, Integer isUsed);

    List<PasswordPolicy> findPasswordPoliciesByIsDelete(Integer isDelete);

}
