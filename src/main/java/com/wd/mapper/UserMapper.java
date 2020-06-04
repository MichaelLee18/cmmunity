package com.wd.mapper;

import com.wd.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modify,avatar_url) " +
            "values(#{account_id},#{name},#{token},#{gmt_create},#{gmt_modify},#{avatar_url})")
     void save(User user);
    @Select("select*from user where token=#{token}")
    User findByToken(@Param("token") String token);
}
