package com.example.link.entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@TableName("t_sys_role")
public class SysRoleVo {
    private Long id;
    private String cd;
    private String nm;
    private String rmks;
}