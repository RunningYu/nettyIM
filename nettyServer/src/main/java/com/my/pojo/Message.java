package com.my.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息体
 * @author : 其然乐衣Letitbe
 * @date : 2023/5/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    /**
     * 发送者
     */
    private String send;

    /**
     * 接收者
     */
    private String receive;

    /**
     * 消息id
     */
    private String id;

    /**
     * 消息值
     */
    private String info;


    /**
     * 类型 1-上线 2-发消息
     */
    private int type;
}
