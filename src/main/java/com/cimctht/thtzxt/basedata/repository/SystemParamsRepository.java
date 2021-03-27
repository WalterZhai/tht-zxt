package com.cimctht.thtzxt.basedata.repository;

import com.cimctht.thtzxt.basedata.entity.SystemParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemParamsRepository extends JpaRepository<SystemParams,String> {

    Page<SystemParams> findSystemParamsByIsDelete(Integer isDelete, Pageable pageable);

    SystemParams findSystemParamsById(String id);

    SystemParams findSystemParamsByIsDeleteAndCode(Integer isDelete, String code);
}
