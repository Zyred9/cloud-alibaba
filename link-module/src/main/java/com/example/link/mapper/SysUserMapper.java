package com.example.link.mapper;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.link.entity.dto.SysUserDto;
import com.example.link.entity.vo.SysRoleVo;
import com.example.link.entity.vo.SysUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import yui.comn.mybatisx.annotation.Link;
import yui.comn.mybatisx.annotation.OneToOne;
import yui.comn.mybatisx.annotation.model.JoinType;
import yui.comn.mybatisx.core.conditions.Wrapper;
import yui.comn.mybatisx.core.mapper.BaseDao;

import java.util.List;

@Mapper
public interface SysUserMapper extends BaseDao<SysUserVo, SysUserDto> {

    /**
     * 左表 t_sys_user 链接右表 t_sys_role ，
     *
     * @param wrapper
     * @return
     */
    @Link( ones = { @OneToOne(leftColumn = "role_id", rightClass = SysRoleVo.class, joinType = JoinType.LEFT)}, resultMapId = "mapId")
    List<SysUserDto> listUserARole(@Param(Constants.WRAPPER) Wrapper<SysUserVo> wrapper);
    
}