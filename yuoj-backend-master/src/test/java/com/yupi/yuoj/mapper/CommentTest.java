package com.yupi.yuoj.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yuoj.constant.CommonConstant;
import com.yupi.yuoj.model.entity.QuestionComment;
import com.yupi.yuoj.service.QuestionCommentService;
import com.yupi.yuoj.utils.SqlUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class CommentTest {

    @Resource
    QuestionCommentService questionCommentService;


    @Test
    public  void lsitPage(){


        QueryWrapper<QuestionComment> commentWrapper= new QueryWrapper<>();

            commentWrapper.eq("questionId",2);

        Page<QuestionComment> postPage = questionCommentService.page(new Page<>(10, 1),commentWrapper);


        System.out.println(postPage);
    }
}
