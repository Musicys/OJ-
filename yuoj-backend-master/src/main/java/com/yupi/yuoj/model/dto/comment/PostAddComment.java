package com.yupi.yuoj.model.dto.comment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yupi.yuoj.model.entity.Usercoment;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 点赞表
 * @TableName commntquestionadd
 */
@Data
@TableName(value ="commntquestionadd")
public class PostAddComment implements Serializable {


    private Long commentid;
    /**
     * 评论
     */
    private String content;
    /**
     * 回复一级id
     */
    private  Long pid;

    /**
     * 题目 id
     */
    private Long questionid;
    /**
     * 回复评论的id
     */
    private  Long commentids;



}