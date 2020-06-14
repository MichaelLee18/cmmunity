package com.wd.service;

import com.wd.enums.CustomizeErrorStatus;
import com.wd.exception.CustomizeException;
import com.wd.mapper.QuestionExtMapper;
import com.wd.mapper.QuestionMapper;
import com.wd.mapper.UserMapper;
import com.wd.model.Question;
import com.wd.model.QuestionExample;
import com.wd.model.User;
import com.wd.vo.PageVo;
import com.wd.vo.QuestionVo;
import org.apache.ibatis.session.RowBounds;
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
    @Autowired
    private QuestionExtMapper questionExtMapper;

    public PageVo list(Integer userId,Integer page, Integer pageSize){
        PageVo pageVo = new PageVo();
        Integer totalCounts = 0;
        if(userId!=null){
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andCreatorEqualTo(userId);
            totalCounts = (int)questionMapper.countByExample(questionExample);
        }else{
            totalCounts = (int)questionMapper.countByExample(new QuestionExample());
        }

        int totalPages = 0;

        if(totalCounts%pageSize==0){
            totalPages = totalCounts/pageSize;
        }else{
            totalPages = totalCounts/pageSize+1;
        }
        if(page<1){
            page = 1;
        }
        if(page>totalPages){
            page = totalPages;
        }
        List<QuestionVo> vos = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        int offset = (page-1)*pageSize;
        if(userId!=null){
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andCreatorEqualTo(userId);

            questions.addAll(questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(offset,pageSize)));
        }else{
            questions.addAll(questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,pageSize)));
        }
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionVo vo = new QuestionVo();
            BeanUtils.copyProperties(question,vo);
            vo.setUser(user);
            vos.add(vo);
        }
        pageVo.setQuestionVoList(vos);
        pageVo.setPage(page);
        List<Integer> pages = new ArrayList<>();
        int start = page-2;
        int end = page+2;
        if(page<=3){
            start = 1;
            if (totalPages>=5)
                end = 5;
            else
                end = totalPages;
        }
        if(page==totalPages&&totalPages<=4){
            start = 1;
            end = totalPages;
        }
        if(page>=totalPages-2&&totalPages>4){
            start = totalPages-4;
            end = totalPages;
        }

        for (int i = start; i <=end ; i++) {
            pages.add(i);
        }
        pageVo.setPages(pages);
        pageVo.setShowNext(true);
        pageVo.setShowNext(true);
        pageVo.setTotalPage(totalPages);
        return pageVo;
    }

    public QuestionVo findById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question==null){
            throw new CustomizeException(CustomizeErrorStatus.QUESTION_NOT_FOUND);
        }
        QuestionVo questionVo = new QuestionVo();
        BeanUtils.copyProperties(question,questionVo);

        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionVo.setUser(user);
        return questionVo;
    }

    public void insertOrUpdate(Question question) {
        if(question.getId()!=null){
            //update
            question.setGmtModify(System.currentTimeMillis());
            int r = questionMapper.updateByPrimaryKey(question);
            if(r!=1){
                throw new CustomizeException(CustomizeErrorStatus.QUESTION_NOT_FOUND);
            }
        }else{
            //insert
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModify(question.getGmtCreate());
            questionMapper.insert(question);
        }
    }

    public void incView(Integer id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }
}
