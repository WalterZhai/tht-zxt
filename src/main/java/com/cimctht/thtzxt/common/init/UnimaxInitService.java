package com.cimctht.thtzxt.common.init;

import com.cimctht.thtzxt.common.constant.SysConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private InitBasedataService initBasedataService;

    @Autowired
    private InitCustomConfigService initCustomConfigService;

    @Value("${unimax.init.auto}")
    private String state;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(SysConstant.INIT_STATE_OPEN.equals(state)){
            logger.info("项目初始化开始");
            //
            initImplementsService.initSpringSessionTable();
            initImplementsService.initBaseTableComment();
            initImplementsService.initSeq();
            initImplementsService.initAdmin();
            //
            initBasedataService.initBaseTableComment();
            initBasedataService.initSeq();
            //
            initCustomConfigService.InitCustomConfigComment();
            logger.info("项目初始化结束");
        }else if(SysConstant.INIT_STATE_CLOSE.equals(state)){
            logger.info("项目初始化未开启");
        }
    }

}
