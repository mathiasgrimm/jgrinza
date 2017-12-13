package com.mathiasgrimm.lib.http;

import com.mathiasgrimm.lib.AppConfig;
import com.mathiasgrimm.lib.container.Container;
import com.mathiasgrimm.lib.container.di.Inject;
import com.mathiasgrimm.lib.http.router.HttpExceptionHandlerInterface;
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
    private HttpExceptionHandlerInterface exceptionHandler;
    private Router router;
    private Container container;

    @Inject
    public HttpHandler(
            AppConfig appConfig,
            HttpExceptionHandlerInterface exceptionHandler,
            Router router
    ) {
        this.appConfig        = appConfig;
        this.exceptionHandler = exceptionHandler;
        this.router           = router;
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, Container container) {
        try {
            String uri   = this.getNormalisedUri(request.getRequestURI());
            Match match  = router.match(request.getMethod(), uri);

            if (match == null) {
                throw new PageNotFoundException();
            } else {
                this.dispatchController(match, container, request, response);
            }
        } catch (Throwable e) {
            try {
                this.exceptionHandler.handle(request, response, e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private String getNormalisedUri(String requestUri) {
        String urlPrefix = this.appConfig.get().getString("router-url-prefix");
        String uri       = requestUri.replaceAll("/" + urlPrefix, "");

        if (uri.equals("")) {
            uri = "/";
        }

        return uri;
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
            for (int i = 0; i < route.getControllerMethodParams().size(); i++) {
                classes[i + 2] = Class.forName("java.lang." + route.getControllerMethodParams().get(i));
            }

            for (String key : match.getRoute().getNamedParams()) {
                params.add(match.getParams().get(key));
            }
        }

        controller
            .getClass()
            .getDeclaredMethod(route.getControllerMethod(), classes)
            .invoke(controller, params.toArray());
    }
}
