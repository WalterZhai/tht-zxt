package com.cimctht.thtzxt.basedata.repository;

import com.cimctht.thtzxt.basedata.entity.Depart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartRepository extends JpaRepository<Depart,String> {

    Depart findDepartByCode(String code);

    List<Depart> queryDepartsByParentDepartAndIsDelete(Depart d, Integer isDelete);

    Depart findDepartById(String id);

    Page<Depart> queryDepartsByIsDeleteAndParentDepartOrderByCode(Integer isDelete, Depart d, Pageable pageable);

    List<Depart> findDepartsByIsDelete(Integer isDelete);
}
