package com.example.oauth.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.oauth.business.entity.Member;
import com.example.oauth.business.entity.SysUser;
import com.example.oauth.business.service.MemberService;
import com.example.oauth.business.service.SysUserService;
import com.example.oauth.comm.constant.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * <p>
 * 用户详情
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired private SysUserService sysUserService;
    @Autowired private MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(requestAttributes).getRequest();
        String loginType = request.getHeader(SystemConstant.ATTRIBUTE_KEY);

        switch (loginType) {
            case SystemConstant.SYS_USER_TYPE :
                UserDetails sysUser = this.sysUserService.getSysUser(userName);
                if (Objects.isNull(sysUser)) {
                    throw new UsernameNotFoundException("查无此人:" + userName);
                }
                return sysUser;
            case SystemConstant.MEMBER_USER_TYPE : return this.memberService.getMember(userName);
            default: throw new RuntimeException("登录类型错误,无法检索用户!");
        }
    }

}
