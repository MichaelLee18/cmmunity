package com.wd.controller;

import com.wd.enums.CommentTypeEnum;
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
        QuestionVo questionVo = questionService.findQuestionVoById(id);
        List<QuestionVo> relatedQuestions = questionService.findTagRelatedQuestionsById(questionVo);
        List<CommentVo> commentVoList = commentService.findById(id, CommentTypeEnum.QUESTION);
        questionService.incView(id);
        model.addAttribute("commentVos",commentVoList);
        model.addAttribute("question",questionVo);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }
}
