package com.my.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取配置文件的netty参数
 * @author : 其然乐衣Letitbe
 * @date : 2023/5/21
 */
@Component
@ConfigurationProperties(prefix = "netty")
@Data
public class NettyConfig {

    /**
     * netty监听的端口
     */
    private int port;

    /**
     * websocket访问路径
     */
    private String path;
}
