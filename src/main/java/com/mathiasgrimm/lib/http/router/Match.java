package com.mathiasgrimm.lib.http.router;

import com.mathiasgrimm.lib.routeconfig.Route;

import java.util.List;
import java.util.Map;

public class Match {
    private Route route;
    private Map<String, Object> params;

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
