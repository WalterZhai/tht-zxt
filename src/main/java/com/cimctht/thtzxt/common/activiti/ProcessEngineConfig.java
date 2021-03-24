package com.cimctht.thtzxt.common.activiti;

import lombok.Data;
import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/24
 */
@Configuration
@ConfigurationProperties(prefix = "spring.activiti.datasource")
@Data
public class ProcessEngineConfig {

    private static final Logger logger = LoggerFactory.getLogger(ProcessEngineConfig.class);

    @Value("${spring.activiti.datasource.url}")
    private String url;

    @Value("${spring.activiti.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.activiti.datasource.username}")
    private String username;

    @Value("${spring.activiti.datasource.password}")
    private String password;

    /**
     * 初始化流程引擎
     * @return
     */
    @Primary
    @Bean
    public ProcessEngine initProcessEngine() {
        logger.info("ProcessEngine开始");

        // 流程引擎配置
        ProcessEngineConfiguration cfg = null;

        try {
            cfg = new StandaloneProcessEngineConfiguration()
                    .setJdbcUrl(url)
                    .setJdbcUsername(username)
                    // 对密码进行解密
                    .setJdbcPassword(password)
                    .setJdbcDriver(driverClassName)
                    // 初始化基础表，不需要的可以改为 DB_SCHEMA_UPDATE_FALSE
                    .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
                    // 默认邮箱配置
                    // 发邮件的主机地址，先用 QQ 邮箱
                    //.setMailServerHost("smtp.qq.com")
                    // POP3/SMTP服务的授权码
                    //.setMailServerPassword("xxxxxxx")
                    // 默认发件人
                    //.setMailServerDefaultFrom("836369078@qq.com")
                    // 设置发件人用户名
                    //.setMailServerUsername("管理员");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 初始化流程引擎对象
        ProcessEngine processEngine = cfg.buildProcessEngine();
        logger.info("ProcessEngine结束");
        return processEngine;
    }

    //八大接口
    // 业务流程的定义相关服务
    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine){
        return processEngine.getRepositoryService();
    }

    // 流程对象实例相关服务
    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine){
        return processEngine.getRuntimeService();
    }

    // 流程任务节点相关服务
    @Bean
    public TaskService taskService(ProcessEngine processEngine){
        return processEngine.getTaskService();
    }

    // 流程历史信息相关服务
    @Bean
    public HistoryService historyService(ProcessEngine processEngine){
        return processEngine.getHistoryService();
    }

    // 表单引擎相关服务
    @Bean
    public FormService formService(ProcessEngine processEngine){
        return processEngine.getFormService();
    }

    // 用户以及组管理相关服务
    @Bean
    public IdentityService identityService(ProcessEngine processEngine){
        return processEngine.getIdentityService();
    }

    // 管理和维护相关服务
    @Bean
    public ManagementService managementService(ProcessEngine processEngine){
        return processEngine.getManagementService();
    }

    // 动态流程服务
    @Bean
    public DynamicBpmnService dynamicBpmnService(ProcessEngine processEngine){
        return processEngine.getDynamicBpmnService();
    }

}
