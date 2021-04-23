package com.example.oauth.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 系统用户
 *
 * @author  zyred
 * @since V 0.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_sys_user")
public class SysUser implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "user_pwd")
    private String userPwd;

    @TableField(value = "sex")
    private String sex;

    @TableField(value = "address")
    private String address;

    /**
     * 后台用户的权限属性
     */
    @TableField(exist = false)
    private List<String> auths;


    /**
     * 封装权限
     *
     * @return      权限集合
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(auths)) {
            auths.forEach(auth -> authorities.add(new SimpleGrantedAuthority(auth)));
        }
        return authorities;
    }


    public String getUsername() {
        return this.userName;
    }


    @Override
    public String getPassword() {
        return this.userPwd;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }
}