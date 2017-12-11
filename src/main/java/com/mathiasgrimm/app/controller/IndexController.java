package com.mathiasgrimm.app.controller;

import com.mathiasgrimm.lib.AppConfig;
import com.mathiasgrimm.lib.container.di.Inject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexController {

    @Inject
    public IndexController(AppConfig appConfig) {

    }

    public void index(
            HttpServletRequest request,
            HttpServletResponse response,
            Integer age,
            Double weight
    ) throws IOException {
        response.getWriter().write("age: " + age + " weight: " + weight);
    }
}
