package com.bxc.servlet;

import com.bxc.annocation.WebServlet;
import com.bxc.tomcat.HttpServlet;

@WebServlet("/user")
public class UserServlet implements HttpServlet
{

    @Override
    public void doGet() {
        System.out.println("doGet");
    }

    @Override
    public void doPost() {
        System.out.println("doPost");
    }
}
