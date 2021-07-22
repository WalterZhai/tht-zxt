package com.cimctht.thtzxt;

import com.cimctht.thtzxt.common.distributedlock.CacheKeyGenerator;
import com.cimctht.thtzxt.common.distributedlock.LockKeyGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@SpringBootApplication
public class ThtZxtApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThtZxtApplication.class, args);
    }

    @Bean
    public CacheKeyGenerator cacheKeyGenerator() {
        return new LockKeyGenerator();
    }

}
