package com.wd.vo;

import com.wd.model.User;
import lombok.Data;

@Data
public class QuestionVo {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModify;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;
}
