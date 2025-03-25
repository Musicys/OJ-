package com.yupi.yuoj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.yupi.yuoj.common.BaseResponse;
import com.yupi.yuoj.common.DeleteRequest;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.common.ResultUtils;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.exception.ThrowUtils;


import com.yupi.yuoj.mapper.QuestionCommentMapper;
import com.yupi.yuoj.model.dto.comment.*;
import com.yupi.yuoj.model.entity.QuestionComment;
import com.yupi.yuoj.model.entity.QuestionLikes;
import com.yupi.yuoj.model.entity.User;


import com.yupi.yuoj.service.QuestionCommentService;
import com.yupi.yuoj.service.QuestionLikesService;
import com.yupi.yuoj.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 评论
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/comment")
@Slf4j
public class QuestionCommentController {

    @Resource
    private QuestionCommentService questionCommentService;

    @Resource
    private QuestionLikesService questionLikesService;

    @Resource
    private QuestionCommentMapper questionCommentMapper;
    @Resource
    private UserService userService;

    private final static Gson GSON = new Gson();

    // region 增删改查

    /**
     * 添加查看状态
     *
     * @param
     * @param request
     * @return
     */
    @GetMapping("/add/requested ")
    public BaseResponse<String> AddRequested(Long Commentid, HttpServletRequest request) {
        User user = userService.getLoginUser(request);

        Long userId = user.getId();
        if (userId  == null) {

            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "未登录");


        }

      questionCommentMapper.updateRequested(userId, Commentid);
        // 用户id 评论 id  修改数据

        return ResultUtils.success("ok");
    }


    /**
     *
     * 用户回复表 分页
     */
    @PostMapping("/list/page/requested")
    public BaseResponse<Page<DtoRequested>> listPostRequestedByPage(@RequestBody Requested requested,
                                                              HttpServletRequest request) {
        long current = requested.getCurrent();
        long size =requested.getPageSize();

        User loginUser = userService.getLoginUser(request);
        Long id = loginUser.getId();
        if (id == null) {

            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "未登录");


        }

        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        QueryWrapper<QuestionComment> commentWrapper = new QueryWrapper<>();
        commentWrapper.eq("commentid",id);
        commentWrapper.orderBy(true,false,"createTime");
        Page<QuestionComment> postPage = questionCommentService.page(new Page<>(current, size), commentWrapper);
        return ResultUtils.success(questionCommentService.getPostquestedPage(postPage, id));
    }


    /**
     *
     */



    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deletePost(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        QuestionComment oldPost = questionCommentService.getById(id);
        if (oldPost == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "评论不存在");
        }


        ThrowUtils.throwIf(oldPost == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldPost.getUserid().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = questionCommentService.removeById(id);
        return ResultUtils.success(b);
    }


    /**
     * 分页获取列表（封装类）
     *
     * @param postQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<DtoComment>> listPostCmentByPage(@RequestBody PostListComment postQueryRequest,
                                                              HttpServletRequest request) {
        long current = postQueryRequest.getCurrent();
        long size = postQueryRequest.getPageSize();

        User loginUser = userService.getLoginUser(request);
        Long id = loginUser.getId();
        if (id == null) {

            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "不能查看评论");


        }

        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        QueryWrapper<QuestionComment> commentWrapper = questionCommentService.getCommentWrapper(postQueryRequest);
        Page<QuestionComment> postPage = questionCommentService.page(new Page<>(current, size), commentWrapper);


        return ResultUtils.success(questionCommentService.getPostCommentPage(postPage, id));
    }


    /**
     * 分页获取子评论列表（封装类）
     *
     * @param postQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/son")
    public BaseResponse<Page<Commentson>> listPostComentByPageSon(@RequestBody PostListComment postQueryRequest,
                                                                  HttpServletRequest request) {


        long current = postQueryRequest.getCurrent();
        long size = postQueryRequest.getPageSize();
        // 限制爬虫



        User loginUser = userService.getLoginUser(request);
        Long id = loginUser.getId();
        Long pidcon = postQueryRequest.getPid();

        if (id == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "不能查看评论");
        }
        //id
        if (pidcon == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }

        return ResultUtils.success(questionCommentService.getListCommentSon(postQueryRequest, id));

    }

    // endregion


    /**
     * 添加（用户）
     *
     * @param postaddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> editPost(@RequestBody PostAddComment postaddRequest, HttpServletRequest request) {

        User user = userService.getLoginUser(request);
        //
        Long id = user.getId();
        if (id == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        QuestionComment questionComment = new QuestionComment();
        String content = postaddRequest.getContent();
        Long pid = postaddRequest.getPid();
        Long questionid = postaddRequest.getQuestionid();
        Long commentid = postaddRequest.getCommentid();
        Long commentids = postaddRequest.getCommentids();

        if (content.isEmpty() || questionid == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (content.length() > 512) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "评论长度超出");
        }

        questionComment.setQuestionid(questionid);
        questionComment.setUserid(id);
        questionComment.setContent(content);
        questionComment.setPid(pid);
        questionComment.setCommentid(commentid );
        questionComment.setCommentids(commentids);

        boolean result = questionCommentService.save(questionComment);

        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtils.success(result);
    }


    /**
     * 点赞
     */

    @GetMapping("/get/like")
    public BaseResponse<Boolean> addLike(Long id, HttpServletRequest request) {


        User user = userService.getLoginUser(request);
        //判断是否登录
        Long userId = user.getId();
        if (userId == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }


        QuestionLikes questionLikes = new QuestionLikes();


        questionLikes.setUserid(userId);
        questionLikes.setCommentid(id);


        boolean save = questionLikesService.save(questionLikes);

        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }


        return ResultUtils.success(true);
    }


    /**
     * 取消点赞
     */

    @GetMapping("/del/liek")
    public BaseResponse<Boolean> delLike(Long id, HttpServletRequest request) {


        User user = userService.getLoginUser(request);
        //判断是否登录
        Long userId = user.getId();
        if (userId == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }


        QueryWrapper<QuestionLikes> qw = new QueryWrapper<>();

        qw.eq("userid", userId);
        qw.eq("commentId", id);

        boolean remove = questionLikesService.remove(qw);


        if (!remove) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }


        return ResultUtils.success(true);
    }


}




