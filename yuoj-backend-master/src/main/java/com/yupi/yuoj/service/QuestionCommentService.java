package com.yupi.yuoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yuoj.model.dto.comment.Commentson;
import com.yupi.yuoj.model.dto.comment.DtoRequested;
import com.yupi.yuoj.model.dto.comment.PostListComment;
import com.yupi.yuoj.model.dto.comment.DtoComment;
import com.yupi.yuoj.model.entity.QuestionComment;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 黎旺
* @description 针对表【question_comment(评论表)】的数据库操作Service
* @createDate 2025-03-16 09:38:24
*/
public interface QuestionCommentService extends IService<QuestionComment> {

    QueryWrapper<QuestionComment> getCommentWrapper(PostListComment postQueryRequest);


    QueryWrapper<QuestionComment> getCommentSonWrapper(PostListComment postQueryRequest);



    Page<DtoComment> getPostCommentPage(Page<QuestionComment> postPage, Long userid);


    Page<Commentson> getListCommentSon(PostListComment postQueryRequest,
                                       Long userid);


    Page<DtoRequested>   getPostquestedPage(Page<QuestionComment> postQueryRequest,
                                            Long userid);


}
