package generators.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generators.domain.QuestionLikes;
import generators.service.QuestionLikesService;
import generators.mapper.QuestionLikesMapper;
import org.springframework.stereotype.Service;

/**
* @author 黎旺
* @description 针对表【question_likes(点赞表)】的数据库操作Service实现
* @createDate 2025-03-16 09:50:10
*/
@Service
public class QuestionLikesServiceImpl extends ServiceImpl<QuestionLikesMapper, QuestionLikes>
    implements QuestionLikesService{

}




