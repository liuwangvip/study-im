package com.isoler.studyim.business.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.isoler.studyim.business.user.model.bean.SysUser;
import com.isoler.studyim.business.user.model.dto.RegisterDto;
import com.isoler.studyim.business.user.model.dto.UserInfoDto;

import java.util.List;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author AutoGenerated
 * @since 2022-07-28
 */
public interface ISysUserService extends IService<SysUser> {
    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    SysUser getByUserName(String username);
    

    /**
     * 注册用户
     *
     * @param dto
     * @return
     */
    SysUser register(RegisterDto dto);

    /**
     * 更新在线状态
     *
     * @param id
     * @param onlineStatus
     */
    void updateOnlineStatus(String id, String onlineStatus);

    /**
     * 获取在线用户列表
     *
     * @param dto
     * @return
     */
    List<SysUser> listUser(UserInfoDto dto);

    /**
     * 获取在线人数
     *
     * @param onlineStatus 在线状态
     * @return
     */
    long countOnlineUser(String onlineStatus);
}
