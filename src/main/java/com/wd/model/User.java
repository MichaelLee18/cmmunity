package com.wd.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String account_id;
    private String name;
    private String token;
    private Long gmt_create;
    private Long gmt_modify;
    private String avatar_url;
}
