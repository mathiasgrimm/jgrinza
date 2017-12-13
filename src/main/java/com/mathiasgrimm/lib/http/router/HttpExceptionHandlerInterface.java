package com.mathiasgrimm.lib.http.router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpExceptionHandlerInterface {
    public void handle(HttpServletRequest request, HttpServletResponse response, Throwable e) throws Exception;
}
