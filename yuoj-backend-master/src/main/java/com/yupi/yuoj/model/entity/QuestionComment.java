package com.yupi.yuoj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论表
 * @TableName question_comment
 */

@Data
@TableName(value ="question_comment")
public class QuestionComment implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 题目 id
     */
    private Long questionid;

    /**
     * 创建用户 id
     */
    private Long userid;

    /**
     * 评论
     */
    private String content;

    /**
     * 评论等级
     */
    private Long pid;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;



    /**
     * 回复用户id
     */
    private Long commentid;

    /**
     * 查看状态
     */

    private  Boolean requested;

    /**
     *回复id
     */
    private  Long commentids;
}