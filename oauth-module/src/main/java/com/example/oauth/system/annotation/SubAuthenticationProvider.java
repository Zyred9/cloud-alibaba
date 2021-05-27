package com.example.oauth.system.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 *      该注解，用来标记 {@see org.springframework.security.authentication.AuthenticationProvider} 类
 *      所有的子类，当 spring boot 启动的时候，{@see com.example.oauth.system.config.security.WebSecurityConfig} 类
 *      中会将所有的子类加载到 {@see org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder}
 *      类中进行管理，作为登录后的认证结果进行处理，所有实现了 {@see org.springframework.security.authentication.AuthenticationProvider} 类
 *      的子类，都需要加上该注释 利用了 spring 注解 {@see org.springframework.beans.factory.annotation.Qualifier} 特性来完成属性集合注入
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Qualifier
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SubAuthenticationProvider {
}
