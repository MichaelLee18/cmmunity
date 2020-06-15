package com.wd.controller;

import com.wd.dto.CommentDTO;
import com.wd.enums.CustomizeErrorStatus;
import com.wd.model.Comment;
import com.wd.model.User;
import com.wd.service.CommentService;
import com.wd.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentDTO commentDTO, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return ResultVO.errorOf(CustomizeErrorStatus.USER_NOT_LOGIN);
        }
        if(commentDTO==null|| (commentDTO!=null&&StringUtils.isBlank(commentDTO.getContent()))){
            return ResultVO.errorOf(CustomizeErrorStatus.COMMENT_CONTENT_NOT_EMPTY);
        }
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setLikeCount(0);
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModify(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        commentService.insert(comment);
        return ResultVO.okOf();
    }
}
