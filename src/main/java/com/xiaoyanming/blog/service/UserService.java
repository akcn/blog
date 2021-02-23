package com.xiaoyanming.blog.service;

import com.xiaoyanming.blog.form.RegisterForm;
import com.xiaoyanming.blog.model.User;

public interface UserService {

    User findByUsername(String username);

    boolean hasUser(String username);

    String registerSuccessful(RegisterForm registerForm);
}
