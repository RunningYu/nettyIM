package com.my.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2023/5/22
 */
@Controller
public class ChatController {

    @RequestMapping("/")
    public String chat() {
        return "chat";
    }
}