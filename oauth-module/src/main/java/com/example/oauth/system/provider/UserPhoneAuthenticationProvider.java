package com.example.oauth.system.provider;

import com.example.oauth.system.annotation.SubAuthenticationProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * <p>
 *
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Configuration
@SubAuthenticationProvider
public class UserPhoneAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String key = authentication.getName();
        return new AnonymousAuthenticationToken(key, authentication.getPrincipal(), authentication.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserPhoneAuthenticationProvider.class.isAssignableFrom(clazz);
    }
}
