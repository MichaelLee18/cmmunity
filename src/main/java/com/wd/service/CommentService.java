package com.wd.service;

import com.wd.enums.CommentTypeEnum;
import com.wd.enums.CustomizeErrorStatus;
import com.wd.exception.CustomizeException;
import com.wd.mapper.CommentMapper;
import com.wd.mapper.QuestionExtMapper;
import com.wd.mapper.QuestionMapper;
import com.wd.mapper.UserMapper;
import com.wd.model.Comment;
import com.wd.model.CommentExample;
import com.wd.model.Question;
import com.wd.model.User;
import com.wd.vo.CommentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Transactional
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

    public List<CommentVo> findById(Integer id,CommentTypeEnum commentTypeEnum) {
        //根据问题id和回复
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id)
        .andTypeEqualTo(commentTypeEnum.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        //获取userId集合
        Set<Integer> set = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        Map<Integer, User> userMap = set.stream().collect(Collectors.toMap(Integer::intValue, integer ->
                userMapper.selectByPrimaryKey(integer)
        ));
        //转换成CommentVo
        List<CommentVo> commentVos = comments.stream().map(comment -> {
            CommentVo commentVo = new CommentVo();
            commentVo.setComment(comment);
            commentVo.setUser(userMap.get(comment.getCommentator()));
            return commentVo;
        }).collect(Collectors.toList());
        return commentVos;
    }
}
