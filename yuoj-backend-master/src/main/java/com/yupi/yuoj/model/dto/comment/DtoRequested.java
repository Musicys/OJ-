package com.yupi.yuoj.model.dto.comment;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yupi.yuoj.model.entity.Question;
import com.yupi.yuoj.model.entity.QuestionComment;
import com.yupi.yuoj.model.entity.User;
import com.yupi.yuoj.model.entity.Usercoment;
import lombok.Data;

import java.text.SimpleDateFormat;

@Data
@TableName(value ="question_comment")
public class DtoRequested {


    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;



    private Long pid;



    private  Long questionId;
    /**
     * 用户留言信息
     */

    private Usercoment usercoment;

    /**
     * 自己是否点赞
     */
    private Boolean like;


    /**
     * 点赞次数
     */
    private Integer good;

    /**
     * 评论
     */
    private String content;


    /**
     *
     */
    private QuestionComment questionComment;

    /**
     * 创建时间
     */
    private String createtime;


    private  Boolean requested;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    public static DtoRequested    objComment(QuestionComment question, User user) {
        if (question == null) {
            return null;
        }


        String userName = user.getUserName();
        String userAvatar = user.getUserAvatar();
        Long userId = user.getId();
        String userRole = user.getUserRole();


        Usercoment usercoment1 = new Usercoment();
        usercoment1.setId(userId);
        usercoment1.setUserName(userName);
        usercoment1.setUserAvatar(userAvatar);
        usercoment1.setUserRole(userRole);
        DtoRequested commentson = new DtoRequested();


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(question.getCreatetime());

        commentson.setUsercoment(usercoment1);
        commentson.setCreatetime(formattedDate);
        commentson.setContent(question.getContent());
        commentson.setId(question.getId());
        commentson.setRequested(question.getRequested());
        commentson.setPid(question.getPid());
        commentson.setQuestionId(question.getQuestionid());
        return commentson;
    }



}
