package com.example.oauth.comm.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *          本项目中，FastJson 序列化的 Feature 定义，一切的序列化 Feature 全部通过本工具取
 *          https://www.it610.com/article/1281455199860310016.htm
 *
 *
 *          序列化时间的问题：
 *
 *          1. Date: 全局配置（消息转换器内），设置 JSON.DEFAULT_DATE_FORMAT = "your formatter"
 *          2. LocalDate: 该时间默认为 yyyy-MM-dd 格式，可根据业务来选择此时间
 *          3. LocalDateTime: 默认格式 yyyy-MM-ddTHH:mm:ss, 需要修改该格式如下方法
 *              3.1：全局配置(默认格式) 开发针对 FastJson 序列化 LocalDateTime 的序列化器，序列化器需要实现 ObjectSerializer
 *              3.2: 局部配置(自定义格式)
 *                   LocalDateTime localDateTime = LocalDateTime.now();
 *                   // 这个错误的单词不是我写的 哈哈哈哈
 *                   String json = JSON.toJSONStringWithDateFormat(localDateTime, JSON.DEFFAULT_DATE_FORMAT);
 *
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public class FastJsonUtils {


    public static Feature[] getGlobalFeatures () {

        return new Feature[] {
                // 使用 bigDecimal
                Feature.UseBigDecimal,
                // 自动关闭资源，默认 true
                Feature.AutoCloseSource,
                // 忽略不匹配的字段属性
                Feature.IgnoreNotMatch,
                // 根据属性排序优先匹配
                Feature.SortFeidFastMatch,
                // 禁用 ASM 字节码框架获取属性字段名称
                Feature.DisableASM,
                // 初始化字符串类型的属性为 ""
                Feature.InitStringFieldAsEmpty

        };
    }




    /**
     * 设置当前JSON 序列化器序列化时间方式为 yyyy-MM-dd，
     * 不针对全局变量（只这是当前被执行的方法，除非在全局
     * 配置里面设置处调用此方法），且只针对 Date 类型
     */
    public static String setJsonDefaultFormat () {
        return setJsonFormat(TimeUtils.Y_M_D_H_M_S);
    }

    /**
     * 设置当前JSON 序列化器序列化时间方式为指定格式，不
     * 针对全局变量（只这是当前被执行的方法，除非在全局配
     * 置里面设置处调用此方法），且只针对 Date 类型
     *
     * @param format        yyyy-MM-dd / yyyy-MM-dd HH:mm:sss
     */
    public static String setJsonFormat (String format) {
        return JSON.DEFFAULT_DATE_FORMAT = format;
    }


    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String json = JSON.toJSONStringWithDateFormat(localDateTime, JSON.DEFFAULT_DATE_FORMAT);
        System.out.println("LocalDateTime:" + json);

        Date date = new Date();
        json = JSON.toJSONStringWithDateFormat(date, setJsonDefaultFormat());
        System.out.println("Date:" + json);

        LocalDate now = LocalDate.now();
        json = JSON.toJSONStringWithDateFormat(now, JSON.DEFFAULT_DATE_FORMAT);
        System.out.println("LocalDate:" + json);
    }
}
