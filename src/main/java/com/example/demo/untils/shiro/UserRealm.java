package com.example.demo.untils.shiro;

import com.example.demo.Entity.RolesEntity;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by panbingcan on 2018/1/8.
 */
@Component
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private RolesEntity rolesEntity;

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //当前登录对象
        Object user = (Object) principals.getPrimaryPrincipal();

        //用户权限列表  可以根据当前用户获取拥有的权限标签
        Set<String> set = new HashSet<>(rolesEntity.getList());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(set);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        //查询用户信息
        if (!username.equals("admin"))
            throw new UnknownAccountException("账号或密码不正确");

        //密码错误
        if (!password.equals("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918")) {
            throw new IncorrectCredentialsException("账号或密码不正确");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(getName(), password, getName());
        return info;
    }

}
