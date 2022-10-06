package com.isoler.studyim.common.websocket.util;

import com.isoler.studyim.business.user.model.bean.SysUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * description
 *
 * @author liuwang
 * @Date 2022-10-06
 */
public class SecurityUtil {

    public static SysUser getCurrentUser() {
        final SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        if (context.getAuthentication() == null) {
            return null;
        }
        if (context.getAuthentication() instanceof AnonymousAuthenticationToken) {
            return null;
        }
        if (context.getAuthentication().getPrincipal() == null) {
            return null;
        }
        if (context.getAuthentication().getPrincipal() instanceof SysUser) {
            return (SysUser) context.getAuthentication().getPrincipal();
        }
        return null;
    }
}
