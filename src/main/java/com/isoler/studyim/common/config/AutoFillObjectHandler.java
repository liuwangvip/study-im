package com.isoler.studyim.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.isoler.studyim.business.user.model.bean.SysUser;
import com.isoler.studyim.common.websocket.util.SecurityUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

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
        final SysUser currentUser = SecurityUtil.getCurrentUser();
        if (currentUser != null) {
            this.setFieldValByName(CommonProperty.CREATOR_ID.getName(), currentUser.getId(), metaObject);
            this.setFieldValByName(CommonProperty.UPDATOR_ID.getName(), currentUser.getId(), metaObject);
        }
    }


    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName(CommonProperty.UPDATE_TIME.getName(), new Date(), metaObject);
        final SysUser currentUser = SecurityUtil.getCurrentUser();
        if (currentUser != null) {
            this.setFieldValByName(CommonProperty.UPDATOR_ID.getName(), currentUser.getId(), metaObject);
        }
    }
}
