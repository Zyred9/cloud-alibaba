package com.example.oauth.comm.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 *      全局返回结果，非 model、view、void，统一使用该类作为返回结果集
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestResult implements Serializable {

    private static final long serialVersionUID = 4711431640160897150L;

    private Integer code;

    private String message;

    private Object data;


}
