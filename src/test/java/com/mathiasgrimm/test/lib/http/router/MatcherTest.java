package com.mathiasgrimm.test.lib.http.router;

import com.mathiasgrimm.lib.http.router.Match;
import com.mathiasgrimm.lib.http.router.Matcher;
import com.mathiasgrimm.lib.routeconfig.Route;
import junit.framework.TestCase;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MatcherTest {

    @Test
    public void itMatchesExactRoute() throws Exception {
        List<Route> routes = new ArrayList<>();

        Route r1 = new Route();
        r1.setName("user.index");
        r1.setUrlPattern("/user");
        r1.setHttpMethod("GET");
        routes.add(r1);

        Route r2 = new Route();
        r2.setName("user.show");
        r2.setUrlPattern("/user/:id");
        r2.setHttpMethod("GET");
        routes.add(r2);

        String uri = "/user";

        Matcher matcher = new Matcher();
        Match match = matcher.match(routes, uri);

        TestCase.assertEquals("user.index", match.getRoute().getName());
        TestCase.assertEquals(0, match.getParams().size());
    }

    @Test
    public void itMatchesPattern() throws Exception {
        List<Route> routes = new ArrayList<>();

        Route r1 = new Route();
        r1.setName("user.index");
        r1.setUrlPattern("/user");
        r1.setHttpMethod("GET");
        routes.add(r1);

        Route r2 = new Route();
        r2.setName("user.show");
        r2.setUrlPattern("/user/test/:id/:locale");
        r2.setHttpMethod("GET");
        routes.add(r2);

        String uri = "/user/test/1/en/";


        Matcher matcher = new Matcher();
        Match match = matcher.match(routes, uri);

        TestCase.assertEquals("user.show", match.getRoute().getName());
        TestCase.assertEquals("1", match.getParams().get("id"));
        TestCase.assertEquals("en", match.getParams().get("locale"));
    }

    @Test
    public void itReturnsNullWhenNoMatchIsFound() throws Exception {
        List<Route> routes = new ArrayList<>();

        Route r1 = new Route();
        r1.setName("user.index");
        r1.setUrlPattern("/user");
        r1.setHttpMethod("GET");
        routes.add(r1);

        String uri = "/user/test/1/en/";


        Matcher matcher = new Matcher();
        Match match = matcher.match(routes, uri);

        TestCase.assertNull(match);
    }
}
