package com.mathiasgrimm.lib.http;

import com.mathiasgrimm.lib.AppConfig;
import com.mathiasgrimm.lib.container.di.Inject;
import com.mathiasgrimm.lib.http.router.HttpExceptionHandlerInterface;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionHandler implements HttpExceptionHandlerInterface {

    private AppConfig appConfig;

    @Inject
    public ExceptionHandler(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Throwable e) throws Exception {
        JSONObject data = new JSONObject();

        int httpStatus = 500;
        data.put("error", "something went wrong");

        switch (e.getClass().getCanonicalName()) {
            case "com.mathiasgrimm.lib.http.PageNotFoundException": {
                httpStatus = 404;
                data.put("error", "page not found");
                break;
            }
        }

        if (this.appConfig.get().getString("env").equals("development")) {
            data.put("original-error", e.getMessage());
            data.put("stack-trace", e.getStackTrace());
            data.put("app-config", appConfig.get());
        }

        response.setContentType("application/json");
        response.setStatus(httpStatus);
        response.getWriter().write(data.toString());

        e.printStackTrace();

        // TODO log
    }
}
