package com.wd.service;

import com.wd.enums.CommentTypeEnum;
import com.wd.enums.CustomizeErrorStatus;
import com.wd.exception.CustomizeException;
import com.wd.mapper.CommentMapper;
import com.wd.mapper.QuestionExtMapper;
import com.wd.mapper.QuestionMapper;
import com.wd.model.Comment;
import com.wd.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    public void insert(Comment comment) {
        if (comment.getParentId()==null|| comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorStatus.TARGET_COMMENT_NOT_FOUND);
        }

        if(comment.getType()==null||!CommentTypeEnum.isExistCommentType(comment.getType())){
            throw new CustomizeException(CustomizeErrorStatus.COMMENT_TYPE_ERROR);
        }
        if (comment.getType()==CommentTypeEnum.QUESTION.getType()){
            //回复处理
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorStatus.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
        }else if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //评论处理
            Comment comment1 = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (comment1==null){
                throw new CustomizeException(CustomizeErrorStatus.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }
    }
}
