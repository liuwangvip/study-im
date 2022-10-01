package com.isoler.studyim.business.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author liuwang
 */
@Controller
public class PageController {
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


    /**
     * 聊天
     *
     * @return
     */
    @GetMapping("im")
    public String getChatPage() {
        return "chat/chat";
    }
}
