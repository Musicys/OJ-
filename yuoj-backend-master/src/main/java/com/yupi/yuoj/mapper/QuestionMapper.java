package com.yupi.yuoj.mapper;

import com.yupi.yuoj.model.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 李鱼皮
* @description 针对表【question(题目)】的数据库操作Mapper
* @createDate 2023-08-07 20:58:00
* @Entity com.yupi.yuoj.model.entity.Question
*/
public interface QuestionMapper extends BaseMapper<Question> {
    /**
     * 增加提交数量
     *
     * @param id 题目 ID
     */
    void incrementSubmitNum(@Param("id") Long id);

    /**
     * 增加通过数量
     *
     * @param id 题目 ID
     */
    void incrementAcceptedNum(@Param("id") Long id);

    /**
     * 增加点赞数
     *
     * @param id 题目 ID
     */
    void incrementThumbNum(@Param("id") Long id);

    /**
     * 增加收藏数
     *
     * @param id 题目 ID
     */
    void incrementFavourNum(@Param("id") Long id);

    /**
     * 减少提交数量
     *
     * @param id 题目 ID
     */
    void decrementSubmitNum(@Param("id") Long id);

    /**
     * 减少通过数量
     *
     * @param id 题目 ID
     */
    void decrementAcceptedNum(@Param("id") Long id);

    /**
     * 减少点赞数
     *
     * @param id 题目 ID
     */
    void decrementThumbNum(@Param("id") Long id);

    /**
     * 减少收藏数
     *
     * @param id 题目 ID
     */
    void decrementFavourNum(@Param("id") Long id);



}




