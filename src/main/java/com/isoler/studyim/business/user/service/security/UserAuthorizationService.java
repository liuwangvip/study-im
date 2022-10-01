package com.isoler.studyim.business.user.service.security;

import com.isoler.studyim.business.user.model.bean.SysUser;
import com.isoler.studyim.business.user.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class UserAuthorizationService implements UserDetailsService {

    @Resource
    private ISysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            log.error("用户名不能为空");
            return null;
        }
        SysUser user = sysUserService.getByUserName(username);
        if (user == null) {
            log.error("用户:{}不存在", username);
        }
        return user;
    }
}
