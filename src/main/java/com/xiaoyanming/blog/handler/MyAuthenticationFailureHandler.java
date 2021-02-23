package com.xiaoyanming.blog.handler;

import com.xiaoyanming.blog.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private NotificationService notificationService;

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        displayAppropriateLoginErrorMessage(request, response);

        redirectStrategy.sendRedirect(request, response, "/user/login");
    }

    private void displayAppropriateLoginErrorMessage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (httpServletRequest.getParameter("username").isEmpty() ||
                httpServletRequest.getParameter("password").isEmpty()) {
            notificationService.addErrorMessage("Please fill the form correctly!");
        } else {
            notificationService.addErrorMessage("Login not successful");
        }
    }
}
