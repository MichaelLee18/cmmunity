package com.wd.interceptor;

import com.wd.mapper.UserMapper;
import com.wd.model.User;
import com.wd.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("=============="+request.getRequestURL());
        Cookie[] cookies = request.getCookies();
        if (cookies!=null&&cookies.length>0){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String value = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria().andTokenEqualTo(value);
                    List<User> users = userMapper.selectByExample(userExample);
                    User user = users.size()>0?users.get(0):null;
                    if (user!=null){
                        request.getSession().setAttribute("user",user);
                        break;
                    }
                }
            }
        }
        return true;
    }
}
