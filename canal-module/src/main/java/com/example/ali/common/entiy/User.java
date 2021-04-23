package com.example.ali.common.entiy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Setter
@Getter
@ToString
@Table(name = "t_user")
public class User implements Serializable {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;
    /**
     * 用户性别
     */
    private Integer gender;
    /**
     * 国家id
     */
    @Column(name = "country_id")
    private Integer countryId;
    /**
     * 用户出生日期
     */
    private Date birthday;
    /**
     * 用户创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

}