package com.mathiasgrimm.lib.http.middleware.handler;

import com.mathiasgrimm.lib.AppConfig;
import com.mathiasgrimm.lib.container.di.Inject;
import com.mathiasgrimm.lib.http.middleware.MiddlewareHandler;
import com.mathiasgrimm.lib.logger.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultContentTypeMiddlewareHandler implements MiddlewareHandler {

    private AppConfig appConfig;

    @Inject
    public DefaultContentTypeMiddlewareHandler(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public boolean handle(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(appConfig.get().getString("default-content-type"));

        return true;
    }
}
