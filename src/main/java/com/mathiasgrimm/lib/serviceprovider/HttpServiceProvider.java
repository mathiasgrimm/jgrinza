package com.mathiasgrimm.lib.serviceprovider;

import com.mathiasgrimm.lib.AppConfig;
import com.mathiasgrimm.lib.container.Container;
import com.mathiasgrimm.lib.container.ServiceProviderInterface;
import com.mathiasgrimm.lib.http.ExceptionHandler;
import com.mathiasgrimm.lib.http.router.HttpExceptionHandlerInterface;
import com.mathiasgrimm.lib.http.router.Matcher;
import com.mathiasgrimm.lib.http.router.Router;
import com.mathiasgrimm.lib.routeconfig.Parser;
import com.mathiasgrimm.lib.routeconfig.Route;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class HttpServiceProvider implements ServiceProviderInterface {

    @Override
    public void register(Container container) throws Exception {

        System.out.println("register HttpServiceProvider");

        // TODO create a loader
        container.set(Router.class, (ct, t) -> {
            System.out.println("loading routes");

            Parser parser = new Parser();
            List<Route> configRoutes = parser.parseForResource("/route.config");

            Hashtable<String,List<Route>> routes = new Hashtable<>();

            for (Route route : configRoutes) {
                if (!routes.containsKey(route.getHttpMethod())) {
                    routes.put(route.getHttpMethod(), new ArrayList<>());
                }

                routes.get(route.getHttpMethod()).add(route);
            }

            return new Router(ct.get(AppConfig.class), routes, ct.get(Matcher.class));
	    });

        // you can define your own exception handler by using the following statement in your
        // own Service Provider
        container.set(HttpExceptionHandlerInterface.class, (ct, t) -> {
            return ct.get(ExceptionHandler.class);
        });
    }

    @Override
    public void boot(Container container) throws Exception {

    }
}
