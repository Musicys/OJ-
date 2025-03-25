package generators.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generators.domain.QuestionComment;
import generators.service.QuestionCommentService;
import generators.mapper.QuestionCommentMapper;
import org.springframework.stereotype.Service;

/**
* @author 黎旺
* @description 针对表【question_comment(评论表)】的数据库操作Service实现
* @createDate 2025-03-16 09:50:10
*/
@Service
public class QuestionCommentServiceImpl extends ServiceImpl<QuestionCommentMapper, QuestionComment>
    implements QuestionCommentService{

}




