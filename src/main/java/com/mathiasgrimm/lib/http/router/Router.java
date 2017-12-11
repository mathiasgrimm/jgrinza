package com.mathiasgrimm.lib.http.router;

import com.mathiasgrimm.lib.AppConfig;
import com.mathiasgrimm.lib.routeconfig.Route;

import java.util.Hashtable;
import java.util.List;

public class Router {

    private Hashtable<String,List<Route>> routes;
    private Matcher matcher;

    public Hashtable<String, List<Route>> getRoutes() {
        return routes;
    }

    public Router(AppConfig appConfig, Hashtable<String, List<Route>> routes, Matcher matcher) {
        this.routes    = routes;
        this.matcher   = matcher;
    }

    public Match match(String httpMethod, String uri) throws Exception {
        if (!this.routes.containsKey(httpMethod)) {
            return null;
        }

        return this.matcher.match(this.routes.get(httpMethod), uri);
    }

}
