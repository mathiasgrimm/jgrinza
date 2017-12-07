package com.mathiasgrimm.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpControllerInterface {

    HttpServletRequest getRequest();
    void setRequest(HttpServletRequest request);


    HttpServletResponse getResponse();
    void setResponse(HttpServletResponse response);
    
}
