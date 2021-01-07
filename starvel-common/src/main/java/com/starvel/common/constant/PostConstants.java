package com.starvel.common.constant;

/**
 * Created by skyyemperor on 2020-12-26 2:13
 * Description :
 */
public class PostConstants {
    /**
     * 帖子正常状态
     */
    public static final int POST_NORMAL = 0;

    /**
     * 帖子顶置状态
     */
    public static final int POST_OVERHEAD = 1;

    /**
     * 帖子封禁状态
     */
    public static final int POST_DISABLE = -1;

    /**
     * 用户对帖子或树洞点赞与点踩的类型
     */
    public static final int LIKE = 1;
    public static final int HATE = -1;

    /**
     * 最大点踩点赞差，超过此数帖子将会被封禁
     */
    public static final int MAX_DISABLE_CNT = 10;

}
