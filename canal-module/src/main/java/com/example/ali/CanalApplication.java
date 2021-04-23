package com.example.ali;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * <p>
 *          第一个版本：CANAL -> REDIS
 *          第二个版本：CANAL -> RABBIT_MQ -> REDIS
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CanalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanalApplication.class, args);
    }

}
