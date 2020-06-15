package com.wd.controller;

import com.wd.service.CommentService;
import com.wd.service.QuestionService;
import com.wd.vo.CommentVo;
import com.wd.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id")Integer id, Model model){
        QuestionVo questionVo = questionService.findById(id);
        List<CommentVo> commentVoList = commentService.findByQuestionId(id);
        questionService.incView(id);
        model.addAttribute("commentVos",commentVoList);
        model.addAttribute("question",questionVo);
        return "question";
    }
}
