package com.example.link;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * </p>
 *
 * @author Zyred
 * @date 2021/4/24 16:41
 **/
@SpringBootApplication
@MapperScan("com.example.link.mapper")
public class MybatisLinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisLinkApplication.class, args);
    }

}
