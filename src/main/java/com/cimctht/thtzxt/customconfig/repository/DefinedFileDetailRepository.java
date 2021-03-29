package com.cimctht.thtzxt.customconfig.repository;

import com.cimctht.thtzxt.customconfig.entity.DefinedFile;
import com.cimctht.thtzxt.customconfig.entity.DefinedFileDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
public interface DefinedFileDetailRepository extends JpaRepository<DefinedFileDetail,String> {

    Page<DefinedFileDetail> findDefinedFileDetailsByIsDeleteAndParentDefinedFileOrderBySeq(Integer isDelete, DefinedFile definedFile, Pageable pageable);

    List<DefinedFileDetail> findDefinedFileDetailsByIsDeleteAndParentDefinedFile(Integer isDelete, DefinedFile definedFile);

    DefinedFileDetail findDefinedFileDetailById(String id);

}
