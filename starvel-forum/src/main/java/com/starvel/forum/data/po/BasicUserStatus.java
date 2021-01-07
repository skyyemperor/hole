package com.starvel.forum.data.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by skyyemperor on 2020-12-23 3:21
 * Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "basic_user_status")
public class BasicUserStatus implements Serializable {
    /**
     * basic用户ID
     */
    @TableId(value = "basic_user_id", type = IdType.INPUT)
    private Long basicUserId;

    /**
     * 上次最近登录时间
     */
    @TableField(value = "last_login_time")
    private Date lastLoginTime;

    /**
     * 状态，0为离线，1为在线，2为未知状态
     */
    @TableField(value = "status")
    private Integer status;

    public BasicUserStatus(Long basicUserId, Date lastLoginTime) {
        this.basicUserId = basicUserId;
        this.lastLoginTime = lastLoginTime;
    }

    private static final long serialVersionUID = 1L;

    public static final String COL_BASIC_USER_ID = "basic_user_id";

    public static final String COL_LAST_LOGIN_TIME = "last_login_time";

    public static final String COL_STATUS = "status";
}