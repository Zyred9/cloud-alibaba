package com.example.oauth.comm.constant;

/**
 * <p>
 *      返回结果状态码，没有特殊情况，请勿修改本类
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public interface RestCodeConstant {

    int         SUCCESS             =      200;
    String      SUCCESS_MSG         =      "操作成功";

    int         FAILURE             =      500;
    String      FAILURE_MSG         =      "操作失败";

    int         NO_ACCESS           =      401;
    String      NO_ACCESS_MSG       =      "无权操作";

    /** 通常指：源 GET 被 POST 请求 **/
    int         REQ_METHOD          =      700;
    String      REQ_METHOD_MSG      =      "请求方法错误";

}
