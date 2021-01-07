package com.starvel.hole.data.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by skyyemperor on 2020-12-24 0:13
 * Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "hole_user")
public class HoleUser implements Serializable {
    @TableId(value = "hole_user_id", type = IdType.AUTO)
    private Long holeUserId;

    @TableField(value = "hole_user_name")
    private String holeUserName;

    @TableField(value = "hole_token")
    @JsonIgnore
    private String holeToken;

    /**
     * hole用户状态，0为默认状态，1为离线，-1为注销
     */
    @TableField(value = "status")
    private Integer status;

    public HoleUser(String holeToken) {
        this.holeToken = holeToken;
    }

    public HoleUser(String holeUserName, String holeToken) {
        this.holeUserName = holeUserName;
        this.holeToken = holeToken;
    }

    private static final long serialVersionUID = 1L;

    public static final String COL_HOLE_USER_ID = "hole_user_id";

    public static final String COL_HOLE_USER_NAME = "hole_user_name";

    public static final String COL_HOLE_TOKEN = "hole_token";

    public static final String COL_STATUS = "status";
}