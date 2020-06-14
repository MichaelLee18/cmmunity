package com.wd.vo;

import com.wd.enums.CustomizeErrorStatus;
import com.wd.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultVO {
    private String message;
    private Integer code;

    public static ResultVO errorOf(Integer code,String message){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(message);
        return resultVO;
    }

    public static ResultVO errorOf(CustomizeErrorStatus customizeErrorStatus) {
        return errorOf(customizeErrorStatus.getCode(),customizeErrorStatus.getMessage());
    }

    public static ResultVO okOf(){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(200);
        resultVO.setMessage("成功!!!");
        return resultVO;
    }

    public static ResultVO errorOf(CustomizeException ex) {
        return errorOf(ex.getCode(),ex.getMessage());
    }
}
