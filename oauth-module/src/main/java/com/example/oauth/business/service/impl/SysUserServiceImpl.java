package com.example.oauth.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.oauth.business.entity.SysUser;
import com.example.oauth.business.mapper.SysUserMapper;
import com.example.oauth.business.service.SysUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Service
@SuppressWarnings("unchecked")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public List<String> getSysUserAuths(Integer sysUserId) {
        List<String> auths = this.baseMapper.getUserAuthByUserId(sysUserId);

        if (CollectionUtils.isEmpty(auths)) {
            return Collections.EMPTY_LIST;
        }

        return auths;
    }


    @Override
    public SysUser getSysUser(String userName) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUser::getUsername, userName);
        SysUser one = this.baseMapper.selectOne(wrapper);

        if (Objects.isNull(one)) {
            return  null;
        }

        one.setAuths(this.getSysUserAuths(one.getUserId()));
        return one;
    }
}
