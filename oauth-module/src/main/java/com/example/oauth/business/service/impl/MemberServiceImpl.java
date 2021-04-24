package com.example.oauth.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.oauth.business.entity.Member;
import com.example.oauth.business.mapper.MemberMapper;
import com.example.oauth.business.service.MemberService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Objects;


@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {


    @Override
    public Member getMember(String userName) {
        LambdaQueryWrapper<Member> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Member::getMemberName, userName);
        Member one = this.baseMapper.selectOne(wrapper);

        if (Objects.isNull(one)) {
            throw new UsernameNotFoundException("查无此人:" + userName);
        }
        return one;
    }

}
