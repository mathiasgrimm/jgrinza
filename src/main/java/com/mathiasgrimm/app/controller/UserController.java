package com.mathiasgrimm.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserController {

    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write("Hi there from the user controller");
    }
}
