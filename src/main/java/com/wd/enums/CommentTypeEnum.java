package com.wd.enums;

import com.wd.model.Question;

public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);

    private final Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExistCommentType(Integer type) {
        for (CommentTypeEnum typeEnum:CommentTypeEnum.values()){
            if(typeEnum.getType()==type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }
}
