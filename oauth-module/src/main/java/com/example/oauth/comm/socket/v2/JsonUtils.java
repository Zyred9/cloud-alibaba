package com.example.oauth.comm.socket.v2;

import com.alibaba.fastjson.JSON;

/**
 * <p>
 *      JSON 工具类
 * </p>
 *
 * @author Zyred
 * @date 2021/4/20 20:58
 **/
public class JsonUtils {

    public static final String SPLIT_COMMAND = ":";

    /**
     * 将对象，转换字符串
     * @param transfer      传输对象 OBJ
     * @return              json类型的字符串
     */
    public static String getStringTransfer (SocketTransfer transfer) {
        return JSON.toJSONString(transfer);
    }

    /**
     * 将字符串，转换为 SocketTransfer 对象
     * @param json          json 字符串
     * @return              SocketTransfer 对象
     */
    public static SocketTransfer parseTransfer (String json) {
        try {
            return JSON.parseObject(json, SocketTransfer.class);
        }catch (Exception ex) {
            throw new RuntimeException("case: JSON format error => " + json);
        }
    }

}
