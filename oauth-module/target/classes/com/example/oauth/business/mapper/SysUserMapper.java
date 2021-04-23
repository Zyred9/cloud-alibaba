package com.example.oauth.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.oauth.business.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {

    List<String> getUserAuthByUserId(@Param("userId") Integer userId);

    SysUser selectByUserName(@Param("userName") String userName);
}