package com.example.oauth;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.oauth.business.entity.SysUser;
import com.example.oauth.business.mapper.SysUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 *
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisPlusTest {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    public void selectUser () {

//        SysUser one = this.sysUserMapper.selectById(1);
        SysUser one = this.sysUserMapper.selectByUserName("张永红");

        System.out.println(one);

    }

}
