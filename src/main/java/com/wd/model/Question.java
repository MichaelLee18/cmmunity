package com.wd.model;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private Long gmt_create;
    private Long gmt_modify;
    private Integer creator;
    private Integer comment_count;
    private Integer reply_count;
    private Integer like_count;
    private String tag;

}
