package com.example.link.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@TableName("t_sys_user")
public class SysUserVo {
    private Long id;
    private Long roleId;
    private String username;
    private String email;
    private String rmks;
    private Integer type;
    @TableField(exist = false)
    private Integer mapId;
}