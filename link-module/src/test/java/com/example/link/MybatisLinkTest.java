package com.example.link;

import com.example.link.entity.dto.SysUserDto;
import com.example.link.mapper.SysUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import yui.comn.mybatisx.core.conditions.query.FindWrapper;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Zyred
 * @date 2021/4/24 16:51
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisLinkTest {


    @Autowired
    private SysUserMapper sysUserDao;

    @Test
    public void testSelect() {
        System.out.println(("----- mybatis link quickstart ------"));
        List<SysUserDto> list = sysUserDao.listUserARole(new FindWrapper<>());
        System.out.println(list);
    }

}
