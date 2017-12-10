package com.mathiasgrimm.test.lib.routeconfig;

import com.mathiasgrimm.lib.routeconfig.Parser;
import com.mathiasgrimm.lib.routeconfig.Route;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParserTest {

    public String getConfig()
    {
        String config = "GET    /            com.mathiasgrimm.app.controller.IndexController.index() home_page\n";
        config += "GET    /user/:id    com.mathiasgrimm.app.controller.IndexController.user(Integer)            user_profile\n";
        config += "HEAD   /ping/:id/:price/:type/:all com.mathiasgrimm.app.controller.IndexController.user(Integer,Double,String,Boolean)no name\n";
        config += "#POST   /user com.mathiasgrimm.app.controller.UserController.store() save_user\n";

        return config;
    }

    @Test
    public void test() throws Exception
    {
        Parser parser      = new Parser();
        List<Route> routes = parser.parse(this.getConfig());

        Route r1 = routes.get(0);
        Route r2 = routes.get(1);
        Route r3 = routes.get(2);
        Route r4 = routes.get(3);

        TestCase.assertEquals("GET", r1.getHttpMethod());
        TestCase.assertEquals("/", r1.getUrlPattern());
        TestCase.assertEquals("com.mathiasgrimm.app.controller.IndexController", r1.getController());
        TestCase.assertEquals("index", r1.getControllerMethod());
        TestCase.assertEquals("home_page", r1.getName());
        TestCase.assertNull(r1.getControllerMethodParams());

        TestCase.assertEquals("GET", r2.getHttpMethod());
        TestCase.assertEquals("/user/:id", r2.getUrlPattern());
        TestCase.assertEquals("com.mathiasgrimm.app.controller.IndexController", r2.getController());
        TestCase.assertEquals("user", r2.getControllerMethod());
        TestCase.assertEquals("user_profile", r2.getName());
        TestCase.assertEquals("Integer", String.join(",", r2.getControllerMethodParams()));

        TestCase.assertEquals("HEAD", r3.getHttpMethod());
        TestCase.assertEquals("/ping/:id/:price/:type/:all", r3.getUrlPattern());
        TestCase.assertEquals("com.mathiasgrimm.app.controller.IndexController", r3.getController());
        TestCase.assertEquals("user", r3.getControllerMethod());
        TestCase.assertEquals("name", r3.getName());
        TestCase.assertEquals("Integer,Double,String,Boolean", String.join(",", r3.getControllerMethodParams()));

        TestCase.assertNull(r4.getHttpMethod());
        TestCase.assertNull(r4.getUrlPattern());
        TestCase.assertNull(r4.getController());
        TestCase.assertNull(r4.getControllerMethod());
        TestCase.assertNull(r4.getName());
        TestCase.assertNull(r4.getControllerMethodParams());
    }

}
