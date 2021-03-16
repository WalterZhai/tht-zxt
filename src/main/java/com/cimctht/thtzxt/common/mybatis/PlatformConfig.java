package com.cimctht.thtzxt.common.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Configuration
@MapperScan(basePackages = {"com.cimctht.servicestation.dao.platform.*"},sqlSessionFactoryRef = "sqlSessionFactoryPlatform")
public class PlatformConfig {


	private static final Logger logger = LoggerFactory.getLogger(PlatformConfig.class);

	@Bean(name="platform")
	@ConfigurationProperties(prefix = "spring.datasource.platform")
	public DataSource getDataSource() {
		logger.info("第二数据源初始化完成");
	    return new DruidDataSource();
	}


	@Bean
	public SqlSessionFactory sqlSessionFactoryPlatform(@Qualifier("platform")DataSource  dataSource) throws Exception {
		SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
		fb.setDataSource(dataSource);
		fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/static/assets/mapper/*.xml"));
		return fb.getObject();
	}

	@Bean
    public DataSourceTransactionManager dataSourceTransactionManagerPlatform(@Qualifier("platform") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplatePlatform(@Qualifier("sqlSessionFactoryPlatform") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
	
}
