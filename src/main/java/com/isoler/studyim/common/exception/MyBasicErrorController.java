package com.isoler.studyim.common.exception;

import com.isoler.studyim.common.api.CommonResult;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 处理拦截器异常
 *
 * @author liuwang
 * @Date 2022-10-01
 */
@Controller
public class MyBasicErrorController extends BasicErrorController {

    public MyBasicErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    /**
     * 定义500的ModelAndView
     *
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(produces = "text/html", value = "/500")
    public ModelAndView errorHtml500(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(getStatus(request).value());
        Map<String, Object> model = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.TEXT_HTML));
        model.put("msg", "自定义错误信息");
        return new ModelAndView("error/500", model);
    }

    /**
     * 定义500的错误JSON信息
     *
     * @param request
     * @return
     */

    @RequestMapping(value = "/500")
    @ResponseBody
    public CommonResult<Map<String, Object>> error500(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.TEXT_HTML));
        HttpStatus status = getStatus(request);
        return CommonResult.failed(status, body);
    }
}