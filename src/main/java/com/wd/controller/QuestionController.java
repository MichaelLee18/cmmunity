package com.wd.controller;

import com.wd.service.QuestionService;
import com.wd.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id")Integer id, Model model){
        QuestionVo questionVo = questionService.findById(id);
        questionService.incView(id);
        model.addAttribute("question",questionVo);
        return "question";
    }
}
