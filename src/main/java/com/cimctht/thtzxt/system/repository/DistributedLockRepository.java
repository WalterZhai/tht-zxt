package com.cimctht.thtzxt.system.repository;

import com.cimctht.thtzxt.system.entity.DistributedLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Transactional
public interface DistributedLockRepository extends JpaRepository<DistributedLock,String> {

    DistributedLock findDistributedLockByLockKey(String lockKey);

    int deleteDistributedLockByLockKey(String lockKey);

    List<DistributedLock> findDistributedLocksByCreateDateLessThan(Date standardDate);

}
