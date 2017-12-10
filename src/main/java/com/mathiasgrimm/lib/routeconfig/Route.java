package com.mathiasgrimm.lib.routeconfig;

import java.util.List;

public class Route {
    private String httpMethod;
    private String urlPattern;
    private String controller;
    private String controllerMethod;
    private String name;
    private List<String> controllerMethodParams;

    public List<String> getControllerMethodParams() {
        return controllerMethodParams;
    }

    public void setControllerMethodParams(List<String> controllerMethodParams) {
        this.controllerMethodParams = controllerMethodParams;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getControllerMethod() {
        return controllerMethod;
    }

    public void setControllerMethod(String controllerMethod) {
        this.controllerMethod = controllerMethod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String controllerMethodParams = null;
        if (this.controllerMethodParams != null) {
            controllerMethodParams = String.join(",", this.controllerMethodParams);
        }

        return "httpMethod: "                 + this.httpMethod
                + " | urlPattern: "             + this.urlPattern
                + " | controller: "             + this.controller
                + " | controllerMethod: "       + this.controllerMethod
                + " | name: "                   + this.name
                + " | controllerMethodParams: " + controllerMethodParams;


    }
}
