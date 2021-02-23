package com.xiaoyanming.blog.service.impl;

import com.xiaoyanming.blog.service.NotificationService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    public static final String NOTIFY_MSG_SESSION_KEY = "siteNotificationMessages";

    @Autowired
    private HttpSession httpSession;

    @Override
    public void addInfoMessage(String msg) {
        addNotificationMessage(NotificationMessageType.INFO, msg);
    }

    @Override
    public void addErrorMessage(String msg) {
        addNotificationMessage(NotificationMessageType.ERROR, msg);
    }

    @SuppressWarnings("unchecked")
    private void addNotificationMessage(NotificationMessageType type, String msg) {
        List<NotificationMessage> notifyMessages = (List<NotificationMessage>) httpSession.getAttribute(NOTIFY_MSG_SESSION_KEY);
        if (notifyMessages == null) {
            notifyMessages = new ArrayList<>();
        }
        notifyMessages.add(new NotificationMessage(type, msg));
        httpSession.setAttribute(NOTIFY_MSG_SESSION_KEY, notifyMessages);

    }

    public enum NotificationMessageType {
        INFO,
        ERROR
    }

    @Getter
    public static class NotificationMessage implements Serializable {
        NotificationMessageType type;
        String text;

        public NotificationMessage(NotificationMessageType type, String text) {
            this.type = type;
            this.text = text;
        }
    }
}