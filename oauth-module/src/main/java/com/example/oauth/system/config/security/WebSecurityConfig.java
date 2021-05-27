package com.example.oauth.system.config.security;

import com.example.oauth.system.annotation.SubAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 *      web 配置
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @SubAuthenticationProvider
    @Autowired private final List<AuthenticationProvider> providers = Collections.emptyList();

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 拦截所有的请求，必须要进行登录
        http.authorizeRequests().anyRequest().authenticated();
        // 默认登录页面
        http.formLogin();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {

        this.providers.forEach(auth::authenticationProvider);

//        auth
//                // 密码处理
//                .authenticationProvider(userNameAuthenticationProvider);
    }


    /**
     * 密码授权认证时，必须要注入 AuthenticationManager 到容器中
     *
     * @return AuthenticationManager 密码授权认证管理器
     * @throws Exception exception
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
