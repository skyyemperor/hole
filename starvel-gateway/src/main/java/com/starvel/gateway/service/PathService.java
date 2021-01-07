package com.starvel.gateway.service;

import com.starvel.gateway.data.po.PathPermission;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Created by skyyemperor on 2020-12-29 0:14
 * Description :
 */
public interface PathService extends IService<PathPermission> {

    Boolean needFilter(String path);

    Boolean filterByUserIdAndPath(Long userId, String path);

}
