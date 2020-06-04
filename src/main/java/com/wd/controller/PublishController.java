package com.wd.controller;

import com.wd.mapper.QuestionMapper;
import com.wd.mapper.UserMapper;
import com.wd.model.Question;
import com.wd.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String publishQuestion(
            @RequestParam("title")String title,
            @RequestParam("descr")String descr,
            @RequestParam("tags")String tags,
            HttpServletRequest request
    ){
        //检查用户是否登录
        User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String value = cookie.getValue();
                    user = userMapper.findByToken(value);
                    if (user!=null){
                        request.getSession().setAttribute("user",user);
                        break;
                    }

                }
            }
        }

        if(user!=null){
            Question question = new Question();
            question.setTitle(title);
            question.setDescription(descr);
            question.setCreator(user.getId());
            question.setTag(tags);
            question.setGmt_create(System.currentTimeMillis());
            question.setGmt_modify(question.getGmt_create());
            question.setComment_count(0);
            question.setLike_count(0);
            question.setReply_count(0);
            questionMapper.save(question);
            return "redirect:/";
        }
        return "redirect:/";
    }
}
