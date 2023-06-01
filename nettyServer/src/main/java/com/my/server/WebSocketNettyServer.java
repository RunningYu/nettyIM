package com.my.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * netty服务端
 * 实现DisposableBean 在容器销毁前会调用destroy 方法进行线程组的关闭
 * @author : 其然乐衣Letitbe
 * @date : 2023/5/22
 */
@Slf4j
@Data
@Component
public class WebSocketNettyServer implements DisposableBean {

    /**
     * 通道初始化对象
     */
    @Autowired
    private WebSocketChannelInit webSocketChannelInit;

    /**
     * boos线程组
     */
    private EventLoopGroup boos;

    /**
     * work线程组
     */
    private EventLoopGroup work;


    /**
     * 自定义启动方法
     * @param port
     */
    public void start(int port) {
        // 设置boos线程组
        boos = new NioEventLoopGroup(1);
        // 设置work线程组
        EventLoopGroup work = new NioEventLoopGroup();
        // 创建启动助手
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boos,work)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler())
                .childHandler(webSocketChannelInit);
        // 绑定ip和端口启动服务端
        ChannelFuture sync = null;
        try {
            // 绑定netty的启动端口
            sync = serverBootstrap.bind(port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            close();
        }
        System.out.println("netty服务器启动成功"+"--端口:"+port);
        log.info("WebSocketNettyServer start " + "netty服务器启动成功"+",端口:"+port);
        sync.channel().closeFuture();
    }

    /**
     * 容器销毁前关闭线程组
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        close();
    }

    /**
     * 关闭方法
     */
    public void close() {
        if (boos!=null) {
            boos.shutdownGracefully();
        }
        if (work!=null) {
            work.shutdownGracefully();
        }
    }

}
