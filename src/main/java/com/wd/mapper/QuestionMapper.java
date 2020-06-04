package com.wd.mapper;

import com.wd.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modify,creator,comment_count,reply_count,like_count,tag) " +
            "values(#{title},#{description},#{gmt_create},#{gmt_modify},#{creator},#{comment_count},#{reply_count},#{like_count},#{tag})")
     void save(Question question);
}
