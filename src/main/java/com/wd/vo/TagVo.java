package com.wd.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Data
public class TagVo {
    private String category;
    private List<String> tags;

    public static List<TagVo> getAllCategoryTags(){
        List<TagVo> tagVos = new ArrayList<>();
        TagVo front = new TagVo();
        front.setCategory("前端");
        front.setTags(Arrays.asList("javascript" ,"vue.js","css","html","html5","node.js","react.js","jquery","css3"));
        tagVos.add(front);
        TagVo back = new TagVo();
        back.setCategory("后端");
        back.setTags( Arrays.asList("php","java","node.js","python","c++","c","golang","spring","springboot","django","flask","c#","swoole","ruby","asp.net","ruby-on-rails","scala","rust","lavarel","爬虫"));
        tagVos.add(back);
        TagVo database = new TagVo();
        database.setCategory("数据库");
        database.setTags( Arrays.asList("mysql","redis","mongodb","json","elasticsearch","nosql","memcached","postgresql","sqlite","mariadb"));
        tagVos.add(database);
        return tagVos;
    }
}

