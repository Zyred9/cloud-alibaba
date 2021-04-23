package com.example.oauth.comm.constant;

/**
 * <p>
 *          系统常量，业务开发请新建业务常量
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public interface SystemConstant {

    /** 登录类型 request attribute key **/
    String      ATTRIBUTE_KEY       =       "login_type";

    /** 登录类型：sys_user(系统用户)  member_user(会员用户) **/
    String      SYS_USER_TYPE       =       "sys_user";
    String      MEMBER_USER_TYPE    =       "member_user";


    /** DateTime格式化字符串 */
    String      DEFAULT_DATE_TIME_PATTERN           =       "yyyy-MM-dd HH:mm:ss";
    /** Date格式化字符串 */
    String      DEFAULT_DATE_PATTERN                =       "yyyy-MM-dd";
    /** Time格式化字符串 */
    String      DEFAULT_TIME_PATTERN                =       "HH:mm:ss";
}
