package com.xiaoyanming.blog.controller;

import com.xiaoyanming.blog.form.LoginForm;
import com.xiaoyanming.blog.form.RegisterForm;
import com.xiaoyanming.blog.service.NotificationService;
import com.xiaoyanming.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final NotificationService notificationService;
    private final UserService userService;

    @RequestMapping("/user/login")
    public String login(LoginForm loginForm) {
        return "user/login";
    }

    @RequestMapping("user/register")
    public String register(RegisterForm registerForm) {
        return "user/register";
    }

    @RequestMapping(value="user/register", method= RequestMethod.POST)
    public String registerPage(@Valid RegisterForm registerForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            notificationService.addErrorMessage("Please fill the form correctly!");
            return "user/register";
        }
        if (!registerForm.getPassword().equals(registerForm.getPassword2())) {
            notificationService.addErrorMessage("两次密码不一致!");
            return "user/register";
        }

        if (userService.hasUser(registerForm.getUsername())) {
            notificationService.addErrorMessage("User already exists!");
            return "user/register";
        }

        userService.registerSuccessful(registerForm);

        return "redirect:/user/login";

    }
}

