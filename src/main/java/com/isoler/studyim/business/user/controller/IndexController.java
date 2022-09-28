package com.isoler.studyim.business.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    /**
     * 首页面
     *
     * @return
     */
    @GetMapping({"", "index"})
    public String getIndexPage() {
        return "index";
    }

    /**
     * 登录页
     *
     * @return
     */
    @GetMapping("login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("login2")
    public String getLogin2Page() {
        return "login/login";
    }


    @GetMapping("chat")
    public String getChatPage() {
        return "chat/chat";
    }
}
