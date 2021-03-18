package com.cimctht.thtzxt.common.schedule;

import com.cimctht.thtzxt.basedata.Impl.DepartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Service
public class ScheduleService {

    @Autowired
    DepartServiceImpl departServiceImpl;

    // @Scheduled(cron="0 0 12 * * ?")
    // public void syncEmployee() {
    //     syncService.syncEmployee();
    // }
    //
    @Scheduled(cron="0 0 11 * * ?")
    public void syncDepart() {
        departServiceImpl.syncDepart();
    }

}
