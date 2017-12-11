package com.mathiasgrimm.lib.http.router;

import com.mathiasgrimm.lib.routeconfig.Route;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Matcher {

    // TODO refactor
    public Match match(List<Route> routes, String uri) throws Exception
    {
        if (routes == null || uri == null) {
            return null;
        }

        if (routes.size() < 1 || uri.length() == 0) {
            return null;
        }

        // removing leading/trailing slashes
        uri = uri.replaceAll("^/|/$", "");

        Map<String,Object> params;
        Match match;

        for (Route route : routes) {
            if (route.getUrlPattern().equals(uri)) {
                match = new Match();
                match.setRoute(route);
                match.setParams(new Hashtable<>());

                return match;
            }

            String[] uriParts = uri.split("/");

            if (uriParts.length != route.getNumberSegments()) {
                continue;
            }

            int prefixSegments  = route.getNumberSegments() - route.getNamedParams().size();
            String[] routeParts = route.getUrlPattern().replaceAll("^/|/$", "").split("/");

            // check if prefix matches
            String routePrefix = String.join("/", Arrays.copyOfRange(routeParts , 0, prefixSegments));
            String uriPrefix   = String.join("/", Arrays.copyOfRange(uriParts   , 0, prefixSegments));

            if (!routePrefix.equals(uriPrefix)) {
                continue;
            }

            match  = new Match();
            params = new Hashtable<>();

            int j = 0;
            for (int i = prefixSegments; i < route.getNumberSegments(); i++, j++) {
                String paramType  = route.getControllerMethodParams().get(j);
                Object paramValue = this.castParam(paramType, uriParts[i]);

                params.put(route.getNamedParams().get(j), paramValue);
            }

            match.setRoute(route);
            match.setParams(params);

            return match;
        }

        return null;
    }

    // TODO maybe make this dynamic
    private Object castParam(String type, String value) throws Exception {

        switch (type) {
            case "Integer": {
                return Integer.valueOf(value);
            }

            case "Long": {
                return Long.valueOf(value);
            }

            case "Double": {
                return Double.valueOf(value);
            }

            case "Byte": {
                return Byte.valueOf(value);
            }

            case "Float": {
                return Float.valueOf(value);
            }

            case "Boolean": {
                return Boolean.valueOf(value);
            }

            case "Char": {
                return value.charAt(0);
            }

            default: {
                throw new Exception("non supported type " + type);
            }
        }
    }
}
