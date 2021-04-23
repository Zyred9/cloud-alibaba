package com.example.oauth.comm.rest;

import com.example.oauth.comm.constant.RestCodeConstant;

/**
 * <p>
 *          构造返回结果集的工具 {@see com.example.oauth.comm.rest.RestResult}
 *          {@see com.example.oauth.comm.constant.RestCodeConstant}
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public class ResultUtils {

    /**
     * 无参默认返回结果
     * @return  操作成功后操作结果封装
     */
    public static RestResult success () {
        return success(null);
    }

    /**
     * 含 data 成功返回结果
     * @param data      操作后返回结果
     * @return          操作成功后操作结果封装
     */
    public static RestResult success (Object data) {
        return success(RestCodeConstant.SUCCESS_MSG, data);
    }

    /**
     * 含 data、 msg 成功返回结果
     * @param data      操作后返回结果
     * @param msg       成功后返回消息
     * @return          操作成功后操作结果封装
     */
    public static RestResult success (String msg, Object data) {
        return success(RestCodeConstant.SUCCESS, msg, data);
    }

    /**
     * 含 code、data、 msg 成功返回结果
     * @param code      自定义 http 状态码
     * @param msg       成功后返回消息
     * @param data      操作后返回结果
     * @return          操作成功后操作结果封装
     */
    public static RestResult success (int code, String msg, Object data) {
        return new RestResult(code, msg, data);
    }




    /**
     * 无参默认返回结果
     *
     * @return  操作失败后操作结果封装
     */
    public static RestResult failure() {
        return failure(null);
    }

    /**
     * 无参默认返回结果
     *
     * @return  操作失败后操作结果封装
     */
    public static RestResult failure(String msg) {
        return failure(msg, null);
    }

    /**
     * 含 data 失败返回结果
     * @param data      操作后返回结果
     * @return          操作失败后操作结果封装
     */
    public static RestResult failure (Object data) {
        return failure(RestCodeConstant.FAILURE_MSG, data);
    }

    /**
     * 含 data、 msg 失败返回结果
     * @param data      操作后返回结果
     * @param msg       失败后返回消息
     * @return          操作失败后操作结果封装
     */
    public static RestResult failure (String msg, Object data) {
        return failure(RestCodeConstant.FAILURE, msg, data);
    }

    /**
     * 含 code、data、 msg 失败返回结果
     * @param code      自定义 http 状态码
     * @param msg       失败后返回消息
     * @param data      操作后返回结果
     * @return          操作失败后操作结果封装
     */
    public static RestResult failure (int code, String msg, Object data) {
        return new RestResult(code, msg, data);
    }

}
