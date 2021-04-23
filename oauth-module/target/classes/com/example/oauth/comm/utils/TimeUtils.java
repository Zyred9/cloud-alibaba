package com.example.oauth.comm.utils;

/**
 * <p>
 *          时间工具类
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public class TimeUtils {

    public static final String Y                    =       "yyyy";
    public static final String Y_M                  =       "yyyy-MM";
    public static final String Y_M_D                =       "yyyy-MM-dd";
    public static final String Y_M_D_H              =       "yyyy-MM-dd HH";
    public static final String Y_M_D_H_M            =       "yyyy-MM-dd HH:mm";
    public static final String Y_M_D_H_M_S          =       "yyyy-MM-dd HH:mm:ss";


    /**
     * 返回所有被定义好的时间格式数组
     *
     * @return  [formatter, ...]
     */
    public static String[] getDefinitionFormatter () {
        return new String[] { Y, Y_M, Y_M_D, Y_M_D_H, Y_M_D_H_M, Y_M_D_H_M_S };
    }

}
