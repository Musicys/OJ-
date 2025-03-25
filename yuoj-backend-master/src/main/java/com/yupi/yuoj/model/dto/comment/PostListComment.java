package com.yupi.yuoj.model.dto.comment;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yupi.yuoj.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 点赞表
 * @TableName commntquestionadd
 */
@Data
@TableName(value ="commntquestionadd")
public class PostListComment extends PageRequest implements Serializable {


    /**
     * 题目 id
     */
    private Long questionid;

    /**
     * 回复 id
     */
    private  Long pid;





}