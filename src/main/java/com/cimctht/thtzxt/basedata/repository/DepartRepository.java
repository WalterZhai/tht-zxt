package com.cimctht.thtzxt.basedata.repository;

import com.cimctht.thtzxt.basedata.entity.Depart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface DepartRepository extends JpaRepository<Depart,String> {

    Depart findDepartByCode(String code);

    List<Depart> queryDepartsByParentDepartAndIsDelete(Depart d, Integer isDelete);

    Depart findDepartById(String id);

    Page<Depart> queryDepartsByIsDeleteAndParentDepartOrderByCode(Integer isDelete, Depart d, Pageable pageable);

    List<Depart> findDepartsByIsDelete(Integer isDelete);

    @Query(nativeQuery = true, value =" select DEPART_SEQ.nextval from dual ")
    BigInteger queryCodeSeqNext();
}
