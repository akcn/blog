package com.xiaoyanming.blog.vo;

import lombok.Data;

import java.util.Date;

@Data
public class PostVo {

    private String title;

    private String html;

    private String author;

    private Date date;
}
