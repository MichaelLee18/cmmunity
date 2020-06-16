package com.wd.controller;

import com.wd.dto.CommentDTO;
import com.wd.enums.CommentTypeEnum;
import com.wd.enums.CustomizeErrorStatus;
import com.wd.model.Comment;
import com.wd.model.User;
import com.wd.service.CommentService;
import com.wd.vo.CommentVo;
import com.wd.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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


    @ResponseBody
    @GetMapping("/comment/{id}")
    public ResultVO<List<CommentVo>> comment(@PathVariable(name="id") Integer id,HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return ResultVO.errorOf(CustomizeErrorStatus.USER_NOT_LOGIN);
        }
        List<CommentVo> commentVos = commentService.findById(id, CommentTypeEnum.COMMENT);
        return ResultVO.okOf(commentVos);
    }
}
