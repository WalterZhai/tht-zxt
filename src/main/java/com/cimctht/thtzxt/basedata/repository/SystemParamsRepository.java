package com.cimctht.thtzxt.basedata.repository;

import com.cimctht.thtzxt.basedata.entity.SystemParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
public interface SystemParamsRepository extends JpaRepository<SystemParams,String> {

    Page<SystemParams> findSystemParamsByIsDelete(Integer isDelete, Pageable pageable);

    SystemParams findSystemParamsById(String id);

    SystemParams findSystemParamsByIsDeleteAndCode(Integer isDelete, String code);
}
