package com.wd.service;

import com.wd.mapper.UserMapper;
import com.wd.model.User;
import com.wd.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public void updateOrInsert(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());

        List<User> userList = userMapper.selectByExample(userExample);
        if(userList.size()!=0){
            User dbUser = userList.get(0);
            //update
            dbUser.setToken(user.getToken());
            dbUser.setName(user.getName());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setGmtModify(System.currentTimeMillis());
            userMapper.updateByExample(dbUser,userExample);
        }else{
            //insert
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModify(user.getGmtCreate());
            userMapper.insert(user);
        }
    }
}
