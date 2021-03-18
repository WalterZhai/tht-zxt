package com.cimctht.thtzxt.common.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Component
@Order(value = 1)
public class UnimaxInitService implements ApplicationRunner {

    static final Logger logger = LoggerFactory.getLogger(UnimaxInitService.class);

    @Autowired
    private InitImplementsService initImplementsService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("项目初始化开始");
        initImplementsService.initSpringSessionTable();
        initImplementsService.initBaseTableComment();
        initImplementsService.initSeq();
        initImplementsService.initAdmin();
        logger.info("项目初始化结束");
    }

}
