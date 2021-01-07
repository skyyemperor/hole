package com.starvel.auth.data.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by skyyemperor on 2021-01-07 17:38
 * Description : 
 */ 
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "security_user_role")
public class SecurityUserRole implements Serializable {
    @TableId(value = "basic_user_id", type = IdType.INPUT)
    private Long basicUserId;

    @TableField(value = "role")
    private String role;

    private static final long serialVersionUID = 1L;

    public static final String COL_BASIC_USER_ID = "basic_user_id";

    public static final String COL_ROLE = "role";
}