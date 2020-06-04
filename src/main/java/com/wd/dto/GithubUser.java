package com.wd.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String id;
    private String login;
    private String bio;
    private String avatar_url;

}
