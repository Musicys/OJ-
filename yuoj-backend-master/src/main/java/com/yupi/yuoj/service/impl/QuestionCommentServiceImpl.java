package com.yupi.yuoj.service.impl;

import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.constant.CommonConstant;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.exception.ThrowUtils;
import com.yupi.yuoj.model.dto.comment.Commentson;
import com.yupi.yuoj.model.dto.comment.DtoRequested;
import com.yupi.yuoj.model.dto.comment.PostListComment;
import com.yupi.yuoj.model.dto.comment.DtoComment;
import com.yupi.yuoj.model.entity.QuestionComment;
import com.yupi.yuoj.model.entity.QuestionLikes;
import com.yupi.yuoj.model.entity.User;
import com.yupi.yuoj.service.QuestionCommentService;
import com.yupi.yuoj.mapper.QuestionCommentMapper;
import com.yupi.yuoj.service.QuestionLikesService;
import com.yupi.yuoj.service.UserService;
import com.yupi.yuoj.utils.SqlUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author
 * @description 针对表【question_comment(评论表)】的数据库操作Service实现
 * @createDate 2025-03-16 09:38:24
 */
@Service
public class QuestionCommentServiceImpl extends ServiceImpl<QuestionCommentMapper, QuestionComment>
        implements QuestionCommentService {
    @Resource
    private UserService userService;
    @Resource
    private QuestionLikesService questionLikesService;


    @Override
    public QueryWrapper<QuestionComment> getCommentWrapper(PostListComment postQueryRequest) {


        QueryWrapper<QuestionComment> queryWrapper = new QueryWrapper<>();
        if (postQueryRequest == null) {
            return queryWrapper;
        }
        Long questionid = postQueryRequest.getQuestionid();
        String sortField = postQueryRequest.getSortField();
        String sortOrder = postQueryRequest.getSortOrder();
        // 拼接查询条件
        queryWrapper.eq("questionId", questionid);
        queryWrapper.isNull("pid");

        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public QueryWrapper<QuestionComment> getCommentSonWrapper(PostListComment postQueryRequest) {

        QueryWrapper<QuestionComment> queryWrapper = new QueryWrapper<>();
        if (postQueryRequest == null) {
            return queryWrapper;
        }
        Long questionid = postQueryRequest.getPid();
        String sortField = postQueryRequest.getSortField();
        String sortOrder = postQueryRequest.getSortOrder();
        // 拼接查询条件
        queryWrapper.eq("pid", questionid);


        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public Page<DtoComment> getPostCommentPage(Page<QuestionComment> postPage, Long id) {
        List<QuestionComment> records = postPage.getRecords();
        Page<DtoComment> questionCommentPage = new Page<>(postPage.getCurrent(), postPage.getSize(), postPage.getTotal());
        if (CollectionUtils.isEmpty(records)) {
            return questionCommentPage;
        }

        // 1. 关联查询用户信息
        Set<Long> userIdSet = records.stream().map(QuestionComment::getUserid).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 填充信息
        List<DtoComment> questionCommentList = records.stream().map(questionComment -> {

                    Long pid = questionComment.getPid();
                    Long id1 = questionComment.getId();

                    if (pid != null) {
                        return null;
                    }


                    Long userId = questionComment.getUserid();
                    User user = null;
                    if (userIdUserListMap.containsKey(userId)) {
                        user = userIdUserListMap.get(userId).get(0);
                    }
                    DtoComment dtoComment = DtoComment.objComment(questionComment, user);

                    QueryWrapper<QuestionLikes> qw = new QueryWrapper<>();
                    qw.eq("userId", id);
                    qw.eq("commentId", id1);
                    long count = questionLikesService.count(qw);
                    boolean Isgood = count != 0;
                    dtoComment.setLike(Isgood);


                    questionLikesService.count();
                    QueryWrapper<QuestionLikes> qw2 = new QueryWrapper<>();
                    qw2.eq("commentId", id1);
                    long count2 = questionLikesService.count(qw2);
                    //查询点赞表
                    dtoComment.setGood((int) count2);


                    //默认查询2条

                    PostListComment postListComment = new PostListComment();




                    postListComment.setPid(id1);
                    postListComment.setCurrent(1);
                    postListComment.setPageSize(2);


                    Page<Commentson> listCommentSon = this.getListCommentSon(postListComment, id);


                  dtoComment.setChiden(listCommentSon);


                    return dtoComment;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());


        questionCommentPage.setRecords(questionCommentList);
        questionCommentPage.setSize(questionCommentList.size());
        return questionCommentPage;

    }

    @Override
    public Page<Commentson> getListCommentSon(PostListComment postQueryRequest,
                                              Long id) {


        long current = postQueryRequest.getCurrent();
        long size = postQueryRequest.getPageSize();



        Page<QuestionComment> postPage = this.page(new Page<>(current, size),
                this.getCommentSonWrapper(postQueryRequest));



        List<QuestionComment> records = postPage.getRecords();
        Page<Commentson> questionCommentPage = new Page<>(postPage.getCurrent(), postPage.getSize(), postPage.getTotal());
        if (CollectionUtils.isEmpty(records)) {
            return questionCommentPage;
        }
        // 1. 关联查询用户信息
        Set<Long> userIdSet = records.stream().map(QuestionComment::getUserid).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 填充信息
        List<Commentson> questionCommentList = records.stream().map(questionComment -> {

                    Long pid = questionComment.getPid();

                    if (pid == null) {
                        return null;
                    }


                    Long userId = questionComment.getUserid();
                    User user = null;
                    if (userIdUserListMap.containsKey(userId)) {
                        user = userIdUserListMap.get(userId).get(0);
                    }
                    Commentson commentSon = Commentson.objCommentSon(questionComment, user);

                    QueryWrapper<QuestionLikes> qw = new QueryWrapper<>();
                    qw.eq("userId", id);
                    qw.eq("commentId", questionComment.getId());
                    long count = questionLikesService.count(qw);
                    boolean Isgood = count != 0;
                    commentSon.setLike(Isgood);


                    questionLikesService.count();
                    QueryWrapper<QuestionLikes> qw2 = new QueryWrapper<>();
                    qw2.eq("commentId", questionComment.getId());
                    long count2 = questionLikesService.count(qw2);
                    //查询点赞表
                    commentSon.setGood((int) count2);



                    //根据回复用户的id，默认是 获取用户名称 用户id，让用户信息+1
                    Long commentid = questionComment.getCommentid();
                    User comituser = userService.getById(commentid);
                    commentSon.setName(comituser.getUserName());
                    commentSon.setUserid(comituser.getId());


                    return commentSon;
                })
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());


        questionCommentPage.setRecords(questionCommentList);






        return questionCommentPage;




    }

    @Override
    public Page<DtoRequested> getPostquestedPage(Page<QuestionComment> postPage, Long id) {

        List<QuestionComment> records = postPage.getRecords();
        Page<DtoRequested> questionCommentPage = new Page<>(postPage.getCurrent(), postPage.getSize(), postPage.getTotal());
        if (CollectionUtils.isEmpty(records)) {
            return questionCommentPage;
        }

        // 1. 关联查询用户信息
        Set<Long> userIdSet = records.stream().map(QuestionComment::getUserid).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 填充信息
        List<DtoRequested> questionCommentList = records.stream().map(questionComment -> {

                    Long pid = questionComment.getPid();
                    Long id1 = questionComment.getId();

                    if (pid == null) {
                        return null;
                    }
                    Long userId = questionComment.getUserid();
                    User user = null;
                    if (userIdUserListMap.containsKey(userId)) {
                        user = userIdUserListMap.get(userId).get(0);
                    }
                    DtoRequested dtoComment = DtoRequested.objComment(questionComment, user);
                    QueryWrapper<QuestionLikes> qw = new QueryWrapper<>();
                    qw.eq("userId", id);
                    qw.eq("commentId", id1);
                    long count = questionLikesService.count(qw);
                    boolean Isgood = count != 0;
                    dtoComment.setLike(Isgood);
                    questionLikesService.count();
                    QueryWrapper<QuestionLikes> qw2 = new QueryWrapper<>();
                    qw2.eq("commentId", id1);
                    long count2 = questionLikesService.count(qw2);
                    //查询点赞表
                    dtoComment.setGood((int) count2);

                    //设置回复留言
                    Long commentids = questionComment.getCommentids();
                    QuestionComment byId = this.getById(commentids);
                    dtoComment.setQuestionComment(byId);

                    //默认查询2条
                    return dtoComment;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());


        questionCommentPage.setRecords(questionCommentList);
        questionCommentPage.setSize(questionCommentList.size());
        return questionCommentPage;
    }
}




