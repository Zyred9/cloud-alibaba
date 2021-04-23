package com.example.oauth.system.config.convert.fastjson.serializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.util.Assert;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 原 fastjson 解析 oauth2 返回结果并不是项目中想要的格式，通过对 Jackson2 消息转换器的 DEBUG
 * 发现 Oauth2 针对 Jackson2 重写了消息转换器，于是我们也决定自己重写一个 FastJson 的序列化器
 * 来解析成我们想要的样子
 *
 * 原 FastJson 格式如下：
 * {
 *     "additionalInformation": {},
 *     "expiration": "2021-04-14 16:19:22",
 *     "expired": false,
 *     "expiresIn": 5644,
 *     "refreshToken": null,
 *     "scope": [
 *         "all"
 *     ],
 *     "tokenType": "bearer",
 *     "value": "b4eccf29-fec3-4412-a294-9e543ca9000a"
 * }
 *
 * Jackson2 格式： Oauth2AccessTokenJackson2Serializer
 * {
 *         "access_token": "b80a66e3-6fa8-4f18-9a73-756afd19b817",
 *         "refresh_token": "f75f435c-980e-4b18-a03c-ad1f490f0e65",
 *         "scope": "all",
 *         "token_type": "bearer",
 *         "expires_in": 179998
 * }
 *
 *
 * @author zyred
 * @since v 0.1
 **/
public class Oauth2AccessTokenFastJsonSerializer implements ObjectSerializer {

    public static final Oauth2AccessTokenFastJsonSerializer INSTANCE = new Oauth2AccessTokenFastJsonSerializer();

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        Map<String, Object> token = this.fastJsonSerializerOAuth2Token((OAuth2AccessToken)object);

        String strToken = JSONObject.toJSONString(token);

        out.write(strToken);
    }

    private Map<String, Object> fastJsonSerializerOAuth2Token(OAuth2AccessToken token) {
        Map<String, Object> tokenMap = new HashMap<>();

        tokenMap.put(OAuth2AccessToken.ACCESS_TOKEN, token.getValue());
        tokenMap.put(OAuth2AccessToken.TOKEN_TYPE, token.getTokenType());

        OAuth2RefreshToken refreshToken = token.getRefreshToken();
        if (refreshToken != null) {
            tokenMap.put(OAuth2AccessToken.REFRESH_TOKEN, refreshToken.getValue());
        }

        Date expiration = token.getExpiration();
        if (expiration != null) {
            long now = System.currentTimeMillis();
            tokenMap.put(OAuth2AccessToken.EXPIRES_IN, (expiration.getTime() - now) / 1000);
        }

        Set<String> scope = token.getScope();
        if (scope != null && !scope.isEmpty()) {
            StringBuilder scopes = new StringBuilder();
            for (String s : scope) {
                Assert.hasLength(s, "Scopes cannot be null or empty. Got " + scope + "");
                scopes.append(s);
                scopes.append(" ");
            }
            tokenMap.put(OAuth2AccessToken.SCOPE, scopes.substring(0, scopes.length() - 1));
        }

        Map<String, Object> additionalInformation = token.getAdditionalInformation();
        for (String key : additionalInformation.keySet()) {
            tokenMap.put(key, additionalInformation.get(key));
        }

        return tokenMap;
    }
}
