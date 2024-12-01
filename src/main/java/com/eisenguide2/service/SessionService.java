package com.eisenguide2.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.eisenguide2.model.User;

@Service
public class SessionService {

    public void createSession(User user, HttpSession session) {
        session.setAttribute("user", user);
    }

    public void invalidateSession(HttpSession session) {
        session.invalidate();
    }

    public User getSessionUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }
}
