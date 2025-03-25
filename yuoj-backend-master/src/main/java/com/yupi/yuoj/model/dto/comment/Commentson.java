package com.yupi.yuoj.model.dto.comment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yupi.yuoj.model.entity.QuestionComment;
import com.yupi.yuoj.model.entity.User;
import com.yupi.yuoj.model.entity.Usercoment;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * 点赞表
 *
 * @TableName question_likes
 */
@Data
@TableName(value = "question_likes")
public class Commentson implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 评论用户信息
     */

    private Usercoment usercoment;

    /**
     * 是否点赞
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
     * 回复人用户名称
     */

    private  String name;

    /**
     *回复id
     */

    private Long userid;
    /**
     * 创建时间
     */
    private String createtime;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    public static Commentson objCommentSon(QuestionComment question, User user) {
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
        Commentson commentson = new Commentson();


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(question.getCreatetime());

        commentson.setUsercoment(usercoment1);
        commentson.setCreatetime(formattedDate);
        commentson.setContent(question.getContent());
        commentson.setId(question.getId());

        return commentson;
    }

}