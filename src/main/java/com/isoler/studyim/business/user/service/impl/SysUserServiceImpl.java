package com.isoler.studyim.business.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isoler.studyim.business.user.mapper.SysUserMapper;
import com.isoler.studyim.business.user.model.bean.SysUser;
import com.isoler.studyim.business.user.model.dto.RegisterDto;
import com.isoler.studyim.business.user.model.dto.UserInfoDto;
import com.isoler.studyim.business.user.model.eo.UserStatusEnum;
import com.isoler.studyim.business.user.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author AutoGenerated
 * @since 2022-07-28
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public SysUser getByUserName(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("c_username", username);
        final List<SysUser> list = this.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        if (list.size() > 1) {
            log.warn("用户名重复:{}", username);
        }
        return list.get(0);
    }

    @Override
    public SysUser register(RegisterDto dto) {
        final SysUser byUserName = this.getByUserName(dto.getUsername());
        if (byUserName != null) {
            throw new RuntimeException("注册失败，用户昵称已存在");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        SysUser user = new SysUser();
        BeanUtils.copyProperties(dto, user);
        user.setUsername(dto.getUsername());
        user.setLoginId(user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        user.setStatus(UserStatusEnum.NORMAL.getStatus());
        this.save(user);
        return user;
    }

    @Override
    public void updateOnlineStatus(String id, String onlineStatus) {
        UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("c_online_status", onlineStatus);
        updateWrapper.eq("c_id", id);
        this.update(updateWrapper);
    }

    @Override
    public List<SysUser> listUser(UserInfoDto dto) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(dto.getOnlineStatus())) {
            queryWrapper.eq("c_online_status", dto.getOnlineStatus());
        }
        return this.list(queryWrapper);
    }

    @Override
    public long countOnlineUser(String onlineStatus) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("c_online_status", onlineStatus);
        return this.count(queryWrapper);
    }
}
