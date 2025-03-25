package com.yupi.yuoj.mapper;

import com.yupi.yuoj.model.entity.QuestionComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
* @author 黎旺
* @description 针对表【question_comment(评论表)】的数据库操作Mapper
* @createDate 2025-03-16 09:38:24
* @Entity com.yupi.yuoj.model.entity.QuestionComment
*/
public interface QuestionCommentMapper extends BaseMapper<QuestionComment> {



    void updateRequested(@Param("userId") Long userId, @Param("commentId") Long commentId);

}




