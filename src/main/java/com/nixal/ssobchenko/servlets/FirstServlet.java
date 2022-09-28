package com.nixal.ssobchenko.servlets;

import com.nixal.ssobchenko.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "FirstServlet", value = "/FirstServlet")
public class FirstServlet extends HttpServlet {
    private static final List<User> users = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User visitor = new User(req.getLocalAddr(), req.getHeader("User-Agent"), LocalDateTime.now());
        users.add(visitor);
        req.setAttribute("users", users);
        req.getRequestDispatcher("incomingUsers.jsp").forward(req, resp);
    }
}