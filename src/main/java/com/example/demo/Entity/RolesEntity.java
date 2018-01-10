package com.example.demo.Entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by panbingcan on 2018/1/10.
 * prefix标志从配置文件里读取 源application.yml里的roles节点 属性list对应上
 */
@Component
@ConfigurationProperties(prefix = "roles")
public class RolesEntity {
    private List<String> list;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
