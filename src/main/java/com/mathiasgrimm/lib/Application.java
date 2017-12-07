package com.mathiasgrimm.lib;

import com.mathiasgrimm.app.controller.HttpControllerInterface;
import com.mathiasgrimm.app.controller.IndexController;
import com.mathiasgrimm.lib.container.Container;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Application {

    private Container container;

    public Application(Container container)
    {
        this.container = container;
    }

    public void handleHttp(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // TODO delegate to HttpHandler
        HttpControllerInterface controller = new IndexController();
        controller.setRequest(request);
        controller.setResponse(response);

        ((IndexController) controller).index();
    }

    protected void boot()
    {
        this.container.boot();
    }
}
