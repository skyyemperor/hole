package com.starvel.hole.data.po;

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
 * Created by skyyemperor on 2020-12-24 0:11
 * Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "hole")
public class Hole implements Serializable {
    public static final String COL_STATE = "state";
    /**
     * hole唯一递增ID
     */
    @TableId(value = "hole_id", type = IdType.AUTO)
    private Long holeId;

    /**
     * 根评论
     */
    @TableField(value = "root_id")
    private Long rootId;

    /**
     * 父评论
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 洞主
     */
    @TableField(value = "hole_user_id")
    private Long holeUserId;

    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 发布时间
     */
    @TableField(value = "`date`")
    private Date date;

    /**
     * 点赞数
     */
    @TableField(value = "`like`")
    private Integer like;

    @TableField(value = "hate")
    private Integer hate;

    /**
     * 状态
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 类型
     */
    @TableField(value = "`type`")
    private Integer type;

    private static final long serialVersionUID = 1L;

    public static final String COL_HOLE_ID = "hole_id";

    public static final String COL_ROOT_ID = "root_id";

    public static final String COL_PARENT_ID = "parent_id";

    public static final String COL_HOLE_USER_ID = "hole_user_id";

    public static final String COL_CONTENT = "content";

    public static final String COL_DATE = "`date`";

    public static final String COL_LIKE = "`like`";

    public static final String COL_HATE = "hate";

    public static final String COL_STATUS = "`status`";

    public static final String COL_TYPE = "`type`";
}