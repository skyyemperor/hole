package com.starvel.hole.data.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by skyyemperor on 2020-12-26 1:44
 * Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "hole_user_like_link")
public class HoleUserLikeLink implements Serializable {
    @TableId(value = "hole_id", type = IdType.INPUT)
    private Long holeId;

    @TableField(value = "hole_user_id")
    private Long holeUserId;

    @TableField(value = "type")
    private Integer type;

    private static final long serialVersionUID = 1L;

    public static final String COL_HOLE_ID = "hole_id";

    public static final String COL_HOLE_USER_ID = "hole_user_id";

    public static final String COL_TYPE = "type";
}