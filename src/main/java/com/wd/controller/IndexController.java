package com.wd.controller;

import com.wd.mapper.UserMapper;
import com.wd.model.User;
import com.wd.service.QuestionService;
import com.wd.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index(HttpServletRequest request, Model model){
        Cookie[] cookies = request.getCookies();
        if (cookies!=null&&cookies.length>0){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String value = cookie.getValue();
                    User user = userMapper.findByToken(value);
                    if (user!=null){
                        request.getSession().setAttribute("user",user);
                        break;
                    }
                }
            }
        }

        List<QuestionVo> questionVos = questionService.list();
        model.addAttribute("qvs",questionVos);
        return "index";
    }
}
