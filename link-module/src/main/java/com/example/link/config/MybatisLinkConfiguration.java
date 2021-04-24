package com.example.link.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yui.comn.mybatisx.extension.injector.SoftSqlInjector;
import yui.comn.mybatisx.extension.plugins.inner.PaginationCacheInterceptor;

/**
 * <p>
 *          Mybatis-link 核心配置类
 * </p>
 *
 * @author Zyred
 * @date 2021/4/24 16:39
 **/
@Configuration
public class MybatisLinkConfiguration {

        @Bean
        public MybatisPlusInterceptor mybatisPlusInterceptor() {
            MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
            interceptor.addInnerInterceptor(new PaginationCacheInterceptor(DbType.MYSQL));
            return interceptor;
        }
        @Bean
        public ISqlInjector sqlInjector() {
            // 自定义注入类
            return new SoftSqlInjector();
        }

}
