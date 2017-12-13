package com.mathiasgrimm.lib;

import com.mathiasgrimm.lib.container.Container;
import com.mathiasgrimm.lib.container.ServiceProviderInterface;
import com.mathiasgrimm.lib.http.HttpHandler;
import com.mathiasgrimm.lib.http.router.HttpExceptionHandlerInterface;
import com.mathiasgrimm.lib.http.router.Router;
import org.json.JSONArray;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Hashtable;

public class Application {

    private Container container;
    private AppConfig config;
    private Hashtable<String,String> env;

    public Application(
            Container container,
            AppConfig config,
            Hashtable<String, String> env
    ) throws Exception {
        this.container = container;
        this.config    = config;
        this.env       = env;
        this.boot();
    }

    public void handleHttp(HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.container.get(HttpHandler.class).handle(request, response, this.container);
    }

    protected void boot() throws Exception
    {
        this.registerServiceProviders();

        this.container.set(AppConfig.class, (ct, t) -> {
            System.out.println("registering config");
            return this.config;
        });

        this.container.boot();
    }

    protected void registerServiceProviders() throws Exception {

        JSONArray serviceProviders = this.config.get().getJSONArray("service-providers");
        for (int i = 0; i < serviceProviders.length(); i++) {
            Class<?> serviceProviderClass = Class.forName(serviceProviders.getString(i));

            ServiceProviderInterface serviceProvider = (ServiceProviderInterface) serviceProviderClass
                    .getDeclaredConstructor()
                    .newInstance();

            this.container.register(serviceProvider);
        }
    }
}
