package com.mathiasgrimm.test.lib.http.router;

import com.mathiasgrimm.lib.http.router.Matcher;
import com.mathiasgrimm.lib.routeconfig.Route;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MatcherTest {



    @Test
    public void test() throws ClassNotFoundException {

        System.out.println(Class.forName("java.lang." + "Integer").getCanonicalName());

        Route r1 = new Route();
        r1.setName("home");
        r1.setUrlPattern("/");
        r1.setHttpMethod("GET");

        List<Route> routes = new ArrayList<>();
        routes.add(r1);


        String uri = "/";

        Matcher matcher = new Matcher();
        matcher.match(routes, uri);

    }

}
