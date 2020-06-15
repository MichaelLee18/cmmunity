package com.wd.vo;

import com.wd.model.Comment;
import com.wd.model.User;
import lombok.Data;

@Data
public class CommentVo {
    private User user;
    private Comment comment;

}
