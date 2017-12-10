package com.mathiasgrimm.lib.http;

import com.mathiasgrimm.lib.AppConfig;
import com.mathiasgrimm.lib.container.Container;
import com.mathiasgrimm.lib.http.router.Match;
import com.mathiasgrimm.lib.http.router.Router;
import com.mathiasgrimm.lib.routeconfig.Route;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpHandler {

    private AppConfig appConfig;

    public HttpHandler(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, Router router, Container container) {
        try {
            String urlPrefix = this.appConfig.get().getString("router-url-prefix");
            String uri       = request.getRequestURI().replaceAll(urlPrefix + "/", "");
            Match match      = router.match(request.getMethod(), uri);

            if (match == null) {
                // TODO 404
            } else {
                this.dispatchController(match, container, request, response);
            }
        } catch (Throwable e) {
            try {
                // TODO exception handler
                response.getWriter().write("Ups! something went wrong: " + e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void dispatchController(
            Match match,
            Container container,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {
        String controllerName = match.getRoute().getController();
        Object controller     = container.get(Class.forName(controllerName));
        Route route           = match.getRoute();

        int methodParams = route.getControllerMethodParams() == null ? 2 : route.getControllerMethodParams().size() + 2;
        Class<?>[] classes = new Class[methodParams];
        classes[0] = HttpServletRequest.class;
        classes[1] = HttpServletResponse.class;

        List<Object> params = new ArrayList<>();
        params.add(request);
        params.add(response);

        if (route.getControllerMethodParams() != null) {
            for (int i = 2; i < route.getControllerMethodParams().size(); i++) {
                classes[i] = Class.forName("java.lang." + route.getControllerMethodParams().get(i));
            }

            for (String key : match.getParams().keySet()) {
                params.add(match.getParams().get(key));
            }
        }

        controller.getClass().getDeclaredMethod("index", classes).invoke(controller, params.toArray());
    }
}
