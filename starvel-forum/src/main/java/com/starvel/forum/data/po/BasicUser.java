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
 * Created by skyyemperor on 2020-12-22 1:23
 * Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "basic_user")
public class BasicUser implements Serializable {
    /**
     * basic用户唯一递增ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 学号
     */
    @TableField(value = "stu_num")
    private String stuNum;

    /**
     * 用户名
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 性别
     */
    @TableField(value = "gender")
    private String gender;

    /**
     * 学院
     */
    @TableField(value = "academy")
    private String academy;

    /**
     * 介绍
     */
    @TableField(value = "introduction")
    private String introduction;

    /**
     * 校区
     */
    @TableField(value = "campus")
    private String campus;

    public BasicUser(String userName) {
        this.userName = userName;
    }

    private static final long serialVersionUID = 1L;

    public static final String COL_USER_ID = "user_id";

    public static final String COL_STU_NUM = "stu_num";

    public static final String COL_USER_NAME = "user_name";

    public static final String COL_GENDER = "gender";

    public static final String COL_ACADEMY = "academy";

    public static final String COL_INTRODUCTION = "introduction";

    public static final String COL_CAMPUS = "campus";
}