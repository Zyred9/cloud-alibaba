package com.example.oauth.system.constant;

/**
 * <p>
 *
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public class SystemConstant {

    private static final String CM_GLOBAL_PREFIX                = "cm.global.";

    /*** 消息序列化器规范，默认设置为 fastjson 作为消息转换器***/
    public static final String CONVERTER_NAME_FAST_JSON         = "fastjson";
    public static final String CONVERTER_NAME_JACK_SON_2        = "jackson2";

    /**  **/
    public static final String CM_GLOBAL_CONVERTER              = CM_GLOBAL_PREFIX + "converter";
    public static final String YML_GLOBAL_FORMATTER_KEY         = CM_GLOBAL_PREFIX + "formatter";

}
