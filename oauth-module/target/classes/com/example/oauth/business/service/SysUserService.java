package com.example.oauth.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.oauth.business.entity.SysUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * 系统用户接口
 *
 * @author zyred
 * @since V 0.1
 */
public interface SysUserService extends IService<SysUser>{

    /**
     * 获取当前用户的所有角色
     * @param sysUserId     用户id
     * @return              用户角色集合
     */
    List<String> getSysUserAuths(Integer sysUserId);

    UserDetails getSysUser(String userName);
}
