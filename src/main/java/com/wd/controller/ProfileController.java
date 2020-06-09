package com.wd.controller;

import com.wd.mapper.UserMapper;
import com.wd.model.User;
import com.wd.service.QuestionService;
import com.wd.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(
            HttpServletRequest request,
            @PathVariable("action")String action, Model model,
            @RequestParam(value = "page", defaultValue = "1")int page,
            @RequestParam(value = "pageSize",defaultValue = "2")int pageSize
    ){
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        User user = (User) request.getSession().getAttribute("user");

        if (user==null){
            return "redirect:/";
        }
        PageVo pageVo = questionService.list(user.getId(),page, pageSize);
        model.addAttribute("pageVo",pageVo);
        return "profile";
    }
}
