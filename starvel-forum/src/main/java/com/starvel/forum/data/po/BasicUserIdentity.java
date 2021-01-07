package com.starvel.forum.data.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by skyyemperor on 2020-12-25 2:13
 * Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "basic_user_identity")
public class BasicUserIdentity implements Serializable {
    @TableId(value = "basic_user_id", type = IdType.INPUT)
    private Long basicUserId;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "password")
    private String password;

    @TableField(value = "email")
    private String email;

    @TableField(value = "stoken_cnt")
    private Integer stokenCnt;

    public BasicUserIdentity(Long basicUserId, String userName, String password) {
        this.basicUserId = basicUserId;
        this.userName = userName;
        this.password = password;
    }

    private static final long serialVersionUID = 1L;

    public static final String COL_BASIC_USER_ID = "basic_user_id";

    public static final String COL_USER_NAME = "user_name";

    public static final String COL_PASSWORD = "password";

    public static final String COL_EMAIL = "email";

    public static final String COL_STOKEN_CNT = "stoken_cnt";
}