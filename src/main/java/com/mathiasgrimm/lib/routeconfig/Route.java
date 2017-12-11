package com.mathiasgrimm.lib.routeconfig;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public List<String> getNamedParams() {
        List<String> params = new ArrayList<>();

        Matcher matcher = Pattern.compile(":(\\w[A-Za-z0-9_-]+)").matcher(this.getUrlPattern());

        while (matcher.find()) {
            params.add(matcher.group(1));
        }

        return params;
    }

    public Integer getNumberSegments() {
        return this.urlPattern.replaceAll("^/|/$", "").split("/").length;
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
