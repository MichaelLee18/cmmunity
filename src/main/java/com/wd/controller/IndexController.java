package com.wd.controller;

import com.wd.mapper.UserMapper;
import com.wd.model.User;
import com.wd.service.QuestionService;
import com.wd.vo.PageVo;
import com.wd.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index( Model model,
                        @RequestParam(value = "page",defaultValue = "1")int page,
                        @RequestParam(value = "pageSize",defaultValue = "2")int pageSize
                        ){


        PageVo pageVo = questionService.list(null,page, pageSize);
        model.addAttribute("pageVo",pageVo);
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
