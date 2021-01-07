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
 * Created by skyyemperor on 2020-12-23 2:34
 * Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "basic_cas_user")
public class BasicCasUser implements Serializable {
    @TableId(value = "basic_user_id", type = IdType.INPUT)
    private Long basicUserId;

    @TableField(value = "stu_num")
    private String stuNum;

    @TableField(value = "cas_password")
    private String casPassword;

    private static final long serialVersionUID = 1L;

    public static final String COL_BASIC_USER_ID = "basic_user_id";

    public static final String COL_STU_NUM = "stu_num";

    public static final String COL_CAS_PASSWORD = "cas_password";
}