
package com.example.oauth.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.oauth.business.entity.Member;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 会员服务接口
 *
 * @author zyred
 * @since V 0.1
 */
public interface MemberService extends IService<Member> {


    UserDetails getMember(String userName);
}
