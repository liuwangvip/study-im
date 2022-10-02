package com.isoler.studyim.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.isoler.studyim.business.user.model.bean.SysUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

/**
 * 自动填充值
 *
 * @author admin
 */
@Configuration
public class AutoFillObjectHandler implements MetaObjectHandler {

    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName(CommonProperty.CREATE_TIME.getName(), new Date(), metaObject);
        this.setFieldValByName(CommonProperty.UPDATE_TIME.getName(), new Date(), metaObject);
        final SysUser loginUser = getLoginUser();
        if (loginUser != null) {
            this.setFieldValByName(CommonProperty.CREATOR_ID.getName(), loginUser.getId(), metaObject);
            this.setFieldValByName(CommonProperty.UPDATOR_ID.getName(), loginUser.getId(), metaObject);
        }
    }

    private SysUser getLoginUser() {
        final SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        if (context.getAuthentication() == null) {
            return null;
        }
        if (context.getAuthentication().getPrincipal() == null) {
            return null;
        }
        return (SysUser) context.getAuthentication().getPrincipal();
    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName(CommonProperty.UPDATE_TIME.getName(), new Date(), metaObject);
        final SysUser loginUser = getLoginUser();
        if (loginUser != null) {
            this.setFieldValByName(CommonProperty.UPDATOR_ID.getName(), loginUser.getId(), metaObject);
        }
    }
}
