package com.starvel.gateway.data.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by skyyemperor on 2020-12-29 0:14
 * Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "security_path_permission")
public class PathPermission implements Serializable {
    @TableId(value = "path", type = IdType.INPUT)
    private String path;

    @TableField(value = "perm_id")
    private Integer permId;

    private static final long serialVersionUID = 1L;

    public static final String COL_PATH = "path";

    public static final String COL_PERM_ID = "perm_id";
}