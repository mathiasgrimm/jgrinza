package com.mathiasgrimm.lib.http.middleware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

// TODO tests
public class Middleware {

    private List<MiddlewareHandler> requestChain;
    private List<MiddlewareHandler> responseChain;

    public Middleware(List<MiddlewareHandler> requestChain, List<MiddlewareHandler> responseChain) {
        this.requestChain  = requestChain;
        this.responseChain = responseChain;
    }

    public boolean processRequestChain(HttpServletRequest request, HttpServletResponse response) {
        return this.processChain(this.requestChain, request, response);
    }

    public boolean processResponseChain(HttpServletRequest request, HttpServletResponse response) {
        return this.processChain(this.responseChain, request, response);
    }

    private boolean processChain(List<MiddlewareHandler> chain, HttpServletRequest request, HttpServletResponse response) {
        if (chain == null || chain.size() == 0) {
            return true;
        }

        for (MiddlewareHandler handler : chain) {
            if (!handler.handle(request, response)) {
                return false;
            }
        }

        return true;
    }

}
