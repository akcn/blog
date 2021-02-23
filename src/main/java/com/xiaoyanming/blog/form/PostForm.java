package com.xiaoyanming.blog.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostForm {

    @NotEmpty
    private String title;

    @NotEmpty
    private String body;
}
