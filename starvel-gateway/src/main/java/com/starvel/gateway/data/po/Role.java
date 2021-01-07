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
 * Created by skyyemperor on 2020-12-29 0:24
 * Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "security_role")
public class Role implements Serializable {
    @TableId(value = "name", type = IdType.INPUT)
    private String name;

    @TableField(value = "perms")
    private String perms;

    @TableField(value = "comment")
    private String comment;

    private static final long serialVersionUID = 1L;

    public static final String COL_NAME = "name";

    public static final String COL_PERMS = "perms";

    public static final String COL_COMMENT = "comment";
}