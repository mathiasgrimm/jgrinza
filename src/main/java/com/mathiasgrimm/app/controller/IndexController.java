package com.mathiasgrimm.app.controller;

import com.mathiasgrimm.lib.AppConfig;
import com.mathiasgrimm.lib.container.di.Inject;
import com.mathiasgrimm.lib.logger.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexController {

    private Logger logger;

    @Inject
    public IndexController(AppConfig appConfig, Logger logger) {
        this.logger = logger;
    }

    public void index(
            HttpServletRequest request,
            HttpServletResponse response,
            Integer age,
            Double weight

    ) throws Exception {
        // throw new Exception("opa");

         this.logger.error("opa!");

        response.getWriter().write("age: " + age + " weight: " + weight);
    }
}
