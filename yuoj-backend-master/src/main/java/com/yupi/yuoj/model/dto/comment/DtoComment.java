package com.yupi.yuoj.model.dto.comment;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yuoj.model.dto.question.JudgeConfig;
import com.yupi.yuoj.model.entity.QuestionComment;
import com.yupi.yuoj.model.entity.User;
import com.yupi.yuoj.model.entity.Usercoment;

import com.yupi.yuoj.model.vo.QuestionVO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 返回
 * @TableName question_comment
 */
//
@Data
@TableName(value ="question_comment")
public class DtoComment implements Serializable {
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
    private  Boolean  like;


    /**
     * 点赞次数
     */
    private Integer good;

    /**
     * 评论
     */
    private String content;
    /**
     * 子评论
     */
    private Page<Commentson> chiden;


    /**
     * 创建时间
     */
    private String createtime;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    public static DtoComment objComment(QuestionComment question, User user) {
        if (question == null) {
            return null;
        }


        String userName = user.getUserName();
        String userAvatar = user.getUserAvatar();
        String userRole = user.getUserRole();
        Long ids = user.getId();


        Usercoment usercoment1 = new Usercoment();
        usercoment1.setUserName(userName);
        usercoment1.setUserRole( userRole);
        usercoment1.setUserAvatar(userAvatar);
        usercoment1.setId(ids);
        DtoComment dtoComment = new DtoComment();



        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(question.getCreatetime());

        dtoComment.setUsercoment(usercoment1);
        dtoComment.setCreatetime(formattedDate);
        dtoComment.setContent(question.getContent());
        dtoComment.setId(question.getId());


        return dtoComment;
    }
}