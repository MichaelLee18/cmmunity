package com.wd.controller;

import com.wd.dto.GithubUser;
import com.wd.dto.AccessTokenDto;
import com.wd.mapper.UserMapper;
import com.wd.model.User;
import com.wd.provider.AuthorizeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUti;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthorizeProvider authorizeProvider;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state,
                           HttpServletResponse response
    ){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setRedirect_uri(redirectUti);
        String accessToke = authorizeProvider.getAccessToke(accessTokenDto);
        GithubUser githubUser = authorizeProvider.getUser(accessToke);
        if (githubUser!=null){
            User user = new User();
            user.setAccount_id(githubUser.getId());
            user.setName(githubUser.getLogin());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modify(user.getGmt_create());
            user.setAvatar_url(githubUser.getAvatar_url());
            userMapper.save(user);
            response.addCookie(new Cookie("token",token));
        }
        return "redirect:/";

    }

}
