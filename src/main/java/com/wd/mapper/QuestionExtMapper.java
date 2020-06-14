package com.wd.mapper;

import com.wd.model.Question;
import com.wd.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question question);
    int incCommentCount(Question question);
}