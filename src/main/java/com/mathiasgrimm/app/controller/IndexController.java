package com.mathiasgrimm.app.controller;

import java.io.IOException;

public class IndexController extends AbstractHttpController {

    public void index() throws IOException {
        this.response.getWriter().write("Hi there from the controller");
    }
}
