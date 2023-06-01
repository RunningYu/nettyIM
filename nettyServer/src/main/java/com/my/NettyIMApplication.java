package com.my;

import com.my.config.NettyConfig;
import com.my.server.WebSocketNettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * netty服务端启动
 * @author : 其然乐衣Letitbe
 * @date : 2023/5/21
 */
@SpringBootApplication
public class NettyIMApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NettyIMApplication.class,args);
    }

    @Autowired
    private WebSocketNettyServer webSocketNettyServer;

    @Autowired
    private NettyConfig nettyConfig;

    @Override
    public void run(String... args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                webSocketNettyServer.start(nettyConfig.getPort());
            }
        }).start();
    }
}
