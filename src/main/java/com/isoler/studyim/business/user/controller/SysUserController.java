package com.isoler.studyim.business.user.controller;


import com.isoler.studyim.business.user.model.bean.SysUser;
import com.isoler.studyim.business.user.model.dto.UserDto;
import com.isoler.studyim.business.user.service.ISysUserService;
import com.isoler.studyim.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author AutoGenerated
 * @since 2022-07-28
 */
@RestController
@RequestMapping("user")
@Api(tags = "用户接口")
public class SysUserController {

    @Resource
    private ISysUserService sysUserService;

    /**
     * 获取当前用户
     *
     * @param authentication
     * @return
     */
    @GetMapping("name")
    @ApiOperation("获取当前用户")
    public CommonResult<SysUser> getCurrentUser(Authentication authentication) {
        SysUser user = (SysUser) authentication.getPrincipal();
        return CommonResult.success(user);
    }

    @PostMapping("register")
    @ApiOperation("注册用户")
    public CommonResult<SysUser> register(@Valid UserDto dto) {
        return CommonResult.success(sysUserService.register(dto));
    }
}
