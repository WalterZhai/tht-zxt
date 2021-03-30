package com.cimctht.thtzxt.customconfig.repository;

import com.cimctht.thtzxt.customconfig.entity.DefinedFile;
import com.cimctht.thtzxt.customconfig.entity.ScheduleTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
public interface ScheduleTaskRepository extends JpaRepository<ScheduleTask,String> {

    List<ScheduleTask> findScheduleTasksByIsDelete(Integer isDelete);

    ScheduleTask findScheduleTaskById(String id);

    Page<ScheduleTask> findScheduleTasksByIsDeleteAndNameLikeOrderByCreateDate(Integer isDelete, String name, Pageable pageable);
}
