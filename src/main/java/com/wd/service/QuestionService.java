package com.wd.service;

import com.wd.mapper.QuestionMapper;
import com.wd.mapper.UserMapper;
import com.wd.model.Question;
import com.wd.model.User;
import com.wd.vo.QuestionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public List<QuestionVo> list(){
        List<QuestionVo> vos = new ArrayList<>();
        List<Question> questions = questionMapper.findAll();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionVo vo = new QuestionVo();
            BeanUtils.copyProperties(question,vo);
            vo.setUser(user);
            vos.add(vo);
        }
        return vos;
    }
}
