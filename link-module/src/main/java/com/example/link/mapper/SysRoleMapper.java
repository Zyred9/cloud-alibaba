package com.example.link.mapper;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.link.entity.dto.SysUserDto;
import com.example.link.entity.vo.SysRoleVo;
import com.example.link.entity.vo.SysUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import yui.comn.mybatisx.annotation.Link;
import yui.comn.mybatisx.annotation.OneToOne;
import yui.comn.mybatisx.core.conditions.Wrapper;
import yui.comn.mybatisx.core.mapper.BaseDao;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseDao<SysUserVo, SysUserDto> {


}