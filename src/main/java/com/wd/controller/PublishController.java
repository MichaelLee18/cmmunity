package com.wd.controller;

import com.wd.model.Question;
import com.wd.model.User;
import com.wd.service.QuestionService;
import com.wd.vo.QuestionVo;
import com.wd.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("allTags", TagVo.getAllCategoryTags());
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id")Integer id, Model model){
        QuestionVo questionVo = questionService.findQuestionVoById(id);
        model.addAttribute("question",questionVo);
        model.addAttribute("allTags", TagVo.getAllCategoryTags());
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
        Question question = null;
        if(user!=null){
            if(id!=null){
                question = questionService.findQuestionById(id);

            }else{
                question = new Question();
                question.setId(id);
                question.setCreator(user.getId());
                question.setCommentCount(0);
                question.setLikeCount(0);
                question.setViewCount(0);
            }
            question.setTitle(title);
            question.setDescription(descr);
            question.setTag(tags);
            questionService.insertOrUpdate(question);
        }
        return "redirect:/";
    }
}
