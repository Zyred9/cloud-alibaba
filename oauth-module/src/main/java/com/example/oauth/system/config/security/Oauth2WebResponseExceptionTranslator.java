package com.example.oauth.system.config.security;

import com.example.oauth.comm.rest.RestResult;
import com.example.oauth.comm.rest.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;

/**
 * Oauth2 访问异常处理器，由 Oauth2 自己实现的
 * Oauth2 默认实现的处理器在 {@code org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator}
 *
 * 在该类中，主要是完成了 Oauth2 异常返回信息的包装，封装为此项目中通用的格式
 */
@Slf4j
public class Oauth2WebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    private final ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    /** 无效的刷新令牌{@value} */
    private static final String INVALID_REFRESH_TOKEN = "Invalid refresh token";
    /** 无效的授权码{@value} */
    private static final String INVALID_AUTHORIZATION_CODE = "Invalid authorization code";
    /** 无效的准予(授权码或refresh token无效){@value} */
    private static final String INVALID_GRANT = OAuth2Exception.INVALID_GRANT;
    /** 用户名或密码错误 **/
    private static final String BAD_CREDENTIALS = "Bad credentials";


    @Override
    public ResponseEntity translate(Exception e) {

        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);

        // 异常栈获取 OAuth2Exception 异常
        OAuth2Exception ase = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(
                OAuth2Exception.class, causeChain);

        if (ase != null) {
            return handleOauth2Exception(ase);
        }

        return new ResponseEntity<>(ResultUtils.failure(e.getMessage()), null, HttpStatus.OK);
    }


    /**
     * 处理认证异常
     *
     * @param e
     * @return
     */
    private ResponseEntity<RestResult> handleOauth2Exception(OAuth2Exception e) {

        int status = e.getHttpErrorCode();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
            headers.set("WWW-Authenticate", String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
        }


        // 异常信息
        String message = e.getMessage();
        // 错误结果处理
        String httpMsg;

        // 无效的准予
        if (INVALID_GRANT.equals(e.getOAuth2ErrorCode())) {
            switch (message) {
                case INVALID_REFRESH_TOKEN:
                    httpMsg = "无效的刷新令牌";
                    break;
                case INVALID_AUTHORIZATION_CODE:
                    httpMsg = "无效的授权码";
                    break;
                case BAD_CREDENTIALS:
                    httpMsg = "用户名或密码错误";
                    break;
                default:
                    httpMsg = "其他授予无效";
            }
        } else {
            // 根绝异常msg返回确定的结果
            httpMsg = message;
        }
        return new ResponseEntity<>(ResultUtils.failure(httpMsg), headers, HttpStatus.valueOf(status));
    }
}
