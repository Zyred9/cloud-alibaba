package com.example.link.entity.dto;

import com.example.link.entity.vo.SysRoleVo;
import com.example.link.entity.vo.SysUserVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SysUserDto {
    protected SysUserVo sysUserVo;
    protected SysRoleVo sysRoleVo;
}