package com.xiaoyanming.blog.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginForm {

    @Size(min=2, max=30, message = "Username size should be in the range [2...30]")
    private String username;

    @NotNull
    @Size(min=1, max=50)
    private String password;
}
