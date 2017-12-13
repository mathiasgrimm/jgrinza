package com.mathiasgrimm.lib.http.middleware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MiddlewareHandler {

    /**
     * if this method returns true it means the next link in the chain will be processed. False will stop the chain
     *
     * @param request
     * @param response
     * @return
     */
    public boolean handle(HttpServletRequest request, HttpServletResponse response);
}
