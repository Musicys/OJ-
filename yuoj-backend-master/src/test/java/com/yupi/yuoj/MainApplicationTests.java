package com.yupi.yuoj;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.yuoj.mapper.QuestionCommentMapper;
import com.yupi.yuoj.mapper.QuestionMapper;

import com.yupi.yuoj.model.entity.Question;
import com.yupi.yuoj.model.entity.QuestionLikes;
import com.yupi.yuoj.service.QuestionLikesService;
import com.yupi.yuoj.service.QuestionService;
import com.yupi.yuoj.utils.MailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * 主类测试
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@SpringBootTest
class MainApplicationTests {
    @Autowired
    private QuestionMapper questionMapper;

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionLikesService questionLikesService;

    @Resource
    private QuestionCommentMapper questionCommentMapper;
    @Test
    public void testIncrementAndDecrement() {
        // 插入一条测试数据

//      questionCommentMapper.updateRequested(1895117906623131650L, 1L);




        //查询点赞表

        // 自增操作
//        questionMapper.incrementSubmitNum(id);
//        questionMapper.incrementAcceptedNum(id);
//        questionMapper.incrementThumbNum(id);
//        questionMapper.incrementFavourNum(id);

        // 验证自增后的值
/*        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Question updatedQuestion = questionMapper.selectOne(queryWrapper);
        assertEquals(1, updatedQuestion.getSubmitNum());
        assertEquals(1, updatedQuestion.getAcceptedNum());
        assertEquals(1, updatedQuestion.getThumbNum());
        assertEquals(1, updatedQuestion.getFavourNum());*/

        // 自减操作
//        questionMapper.decrementSubmitNum(id);
//        questionMapper.decrementAcceptedNum(id);
//        questionMapper.decrementThumbNum(id);
//        questionMapper.decrementFavourNum(id);
//
//        // 验证自减后的值
//        updatedQuestion = questionMapper.selectOne(queryWrapper);
//        assertEquals(0, updatedQuestion.getSubmitNum());
//        assertEquals(0, updatedQuestion.getAcceptedNum());
//        assertEquals(0, updatedQuestion.getThumbNum());
//        assertEquals(0, updatedQuestion.getFavourNum());
//
//        // 清理测试数据
//        questionMapper.deleteById(id);
    }


}
