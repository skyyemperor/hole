package com.starvel.gateway.service.impl;

import com.alibaba.fastjson.JSON;
import com.starvel.gateway.data.po.Role;
import com.starvel.gateway.mapper.RoleMapper;
import com.starvel.gateway.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starvel.gateway.data.po.PathPermission;
import com.starvel.gateway.mapper.PathPermissionMapper;
import com.starvel.gateway.service.PathService;

import java.util.List;

/**
 * Created by skyyemperor on 2020-12-29 0:14
 * Description :
 */
@Service
public class PathServiceImpl extends ServiceImpl<PathPermissionMapper, PathPermission> implements PathService {

    @Autowired
    private PathPermissionMapper pathPermissionMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    public Boolean needFilter(String path) {
        // Provider和身份认证接口强制不进行过滤
        if (path.startsWith("/pvd/") || path.startsWith("/api/auth/")) {
            return false;
        }

        PathPermission pathPermission = pathPermissionMapper.selectById(path);
        return pathPermission != null && pathPermission.getPermId() > 0;
    }

    @Override
    public Boolean filterByUserIdAndPath(Long userId, String path) {
        //获取用户的权限
        String role = userRoleMapper.selectById(userId).getRole();
        String perms = roleMapper.selectById(role).getPerms();
        List<Integer> permList = JSON.parseArray(perms, Integer.class);

        //获取path的权限
        PathPermission pathPermission = pathPermissionMapper.selectById(path);
        if (pathPermission == null) return false;

        return permList.contains(pathPermission.getPermId());
    }
}
