package com.xiaoyanming.blog.service.impl;

import com.xiaoyanming.blog.form.RegisterForm;
import com.xiaoyanming.blog.model.User;
import com.xiaoyanming.blog.repository.UserRepository;
import com.xiaoyanming.blog.service.NotificationService;
import com.xiaoyanming.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceJpaImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final NotificationService notificationService;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean hasUser(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public String registerSuccessful(RegisterForm registerForm) {
        User user = new User();
        user.setUsername(registerForm.getUsername());
        user.setPasswordHash(bCryptPasswordEncoder.encode(registerForm.getPassword()));
        userRepository.save(user);
        log.info("user:{}", user);
        notificationService.addInfoMessage("Register successful");
        return "redirect:/";
    }
}
