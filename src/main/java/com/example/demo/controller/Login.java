package com.example.demo.controller;

import com.example.demo.Entity.RolesEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Alex on 2018/1/10.
 * 权限验证必须 继承AbstractController
 */
@RequestMapping("/login")
@RestController
public class Login extends AbstractController {

    /*日志不说了*/
    private final static Logger logger = LoggerFactory.getLogger(Login.class);

    @Autowired
    private RolesEntity rolesEntity;


    /*登录方法*/
    // TODO: 2018/1/10 localhost:8080/login/login?name=admin&pass=admin
    @PostMapping("/login")
    public boolean login(@RequestParam("name") String name, @RequestParam("pass") String pass) {

        Subject subject = SecurityUtils.getSubject();
        pass = new Sha256Hash(pass).toHex();//sha256加密
        UsernamePasswordToken token = new UsernamePasswordToken(name, pass);

        subject.login(token);//此方法会跳到UserRealm里的doGetAuthenticationInfo方法验证  因为UserRealm 继承了AuthorizingRealm
        return true;
    }


    /*获取自己的权限
    * 此处所在的类继承AbstractController
    * 加@RequiresPermissions标签 标签里的字符串是自定字符串
    * 举个例子标签库设置了admin:user,admin:admin,user:user逗号分隔 三个标签。此处标记admin:user标签，
    * 访问此方法的用户在权限里必须拥有admin:user标签，不然无法访问此方法
    * 拥有@RequiresPermissions注解 api进入前，先进入UserRealm里的 doGetAuthorizationInfo 方法进行验证  继承了AuthorizingRealm
    * 如果当前用户的标签没有 admin:user 则不能访问此方法
    * */
    // TODO: 2018/1/10 localhost:8080/login/get
    @RequestMapping("/get")
    @RequiresPermissions("admin:user")
    public List<String> get() {
        logger.info("角色组：" + rolesEntity.getList().toString());
        return rolesEntity.getList();
    }
}
