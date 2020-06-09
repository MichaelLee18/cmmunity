package com.wd.vo;

import lombok.Data;

import java.util.List;
@Data
public class PageVo {
    private List<QuestionVo> questionVoList;
    private Integer page;
    private List<Integer> pages;
    private boolean showPre;
    private boolean showNext;
    private Integer totalPage;
}
