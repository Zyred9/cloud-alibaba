package com.example.oauth.system.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * <p>
 *      oauth2.0配置文件
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Configuration
@ConditionalOnBean(OAuth2AutoConfiguration.class)
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    /** 存储token，使用redis **/
    @Autowired private RedisConnectionFactory redisConnectionFactory;
    /** 密码服务 **/
    @Autowired @Lazy private PasswordEncoder passwordEncoder;
    /** 授权认证器 **/
    @Autowired private AuthenticationManager authenticationManager;


    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(this.redisConnectionFactory);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置三方应用，如果不配置三方应用信息，将无法正常使用
     *
     * @param clients 客户端配置
     * @throws Exception exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()

                // ******** code码授权 ************
                //创建一个 web 第三方
                .withClient("web")
                //密码
                .secret(this.passwordEncoder.encode("web-secret"))
                //作用域
                .scopes("web-scopes")
                //授权方式一共有四种，这里是验证码授 权，还有密码授权，静默授权，客户端授权
                .authorizedGrantTypes("authorization_code")
                //访问成功后的跳转地址
                .redirectUris("https://www.baidu.com")
                //token有效时间
                .accessTokenValiditySeconds(7200)

                // ******** 静默授权 ************
                .and()
                .withClient("ios")
                .secret(passwordEncoder.encode("ios-secret"))
                .scopes("ios-scopes")
                .authorizedGrantTypes("implicit")
                .redirectUris("https://www.baidu.com")
                .accessTokenValiditySeconds(7200)

                // ******** 密码授权 ************
                .and()
                .withClient("api")
                .secret(this.passwordEncoder.encode("api-secret"))
                .scopes("all")
                .authorizedGrantTypes("password")
                .redirectUris("https://www.baidu.com")
                .accessTokenValiditySeconds(7200)

                //  ******** 客户端授权 ************
                .and()
                .withClient("client")
                .secret(passwordEncoder.encode("client-secret"))
                .scopes("client-scopes") .authorizedGrantTypes("client_credentials")
                .redirectUris("https://www.baidu.com")
                .accessTokenValiditySeconds(7200);
    }

    /**
     * token 的存储方式 我们先存在 redis 里面
     *
     * @param endpoints 断点信息
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(this.tokenStore())
                // 密码认证授权，必须要授权认证管理器
                .authenticationManager(this.authenticationManager);
        // 配置自定义异常转换器，在 Oauth2 中，异常不会被全局异常拦截，而是直接返回当前异常信息
        endpoints.exceptionTranslator(new Oauth2WebResponseExceptionTranslator());
    }
}
