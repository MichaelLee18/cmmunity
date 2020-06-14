package com.wd.controller;

import com.wd.mapper.QuestionMapper;
import com.wd.mapper.UserMapper;
import com.wd.model.Question;
import com.wd.model.User;
import com.wd.service.QuestionService;
import com.wd.service.UserService;
import com.wd.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id")Integer id, Model model){
        QuestionVo questionVo = questionService.findById(id);
        model.addAttribute("question",questionVo);
        return "publish";
    }

    @PostMapping("/publish")
    public String publishQuestion(
            @RequestParam("title")String title,
            @RequestParam("descr")String descr,
            @RequestParam("tags")String tags,
            @RequestParam("id")Integer id,
            HttpServletRequest request
    ){
        //检查用户是否登录
        User user = (User) request.getSession().getAttribute("user");
        if(user!=null){
            Question question = new Question();
            question.setId(id);
            question.setTitle(title);
            question.setDescription(descr);
            question.setCreator(user.getId());
            question.setTag(tags);
            question.setCommentCount(0);
            question.setLikeCount(0);
            question.setViewCount(0);
            questionService.insertOrUpdate(question);
        }
        return "redirect:/";
    }
}
