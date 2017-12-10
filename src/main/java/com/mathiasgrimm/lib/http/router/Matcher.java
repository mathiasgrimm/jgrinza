package com.mathiasgrimm.lib.http.router;

import com.mathiasgrimm.lib.routeconfig.Route;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Matcher {

    public Match match(List<Route> routes, String uri)
    {
        Match match = new Match();
        match.setRoute(routes.get(0));

        Map<String,Object> params = new Hashtable<>();

        params.put("age",    23);
        params.put("weight", 100);

        match.setParams(params);

        return match;
    }
}
