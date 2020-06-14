package com.wd.advice;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.wd.enums.CustomizeErrorStatus;
import com.wd.exception.CustomizeException;
import com.wd.vo.ResultVO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeAdviceController {
    @ExceptionHandler(Exception.class)
    ModelAndView handleException(HttpServletRequest request, Throwable ex, Model model, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("error");
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
            ResultVO resultVO = null;

            //json方式处理
            if (ex instanceof CustomizeException){
                resultVO = ResultVO.errorOf((CustomizeException) ex);
            }else {
                resultVO = ResultVO.errorOf(CustomizeErrorStatus.SYSTEM_ERROR);
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("utf8");
            try {
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultVO));
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }else {
            //页面方式处理
            if (ex instanceof CustomizeException){
                model.addAttribute("message", ex.getMessage());
            }else {
                model.addAttribute("message", CustomizeErrorStatus.SYSTEM_ERROR.getMessage());
            }
            return modelAndView;
        }
    }


}
