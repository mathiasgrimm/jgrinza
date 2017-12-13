Java MVC Framework Experiment
=============================

## Objective
Just trying to play a bit with SOLID concepts using java.

Implementing a basic MVC framework based on a Container and Dependency Injection (automatic or defined);

# Configuration
There are 3 files where the application can be configured and they are all in the resources folder:
- app.json
- env.properties (gitignored)
- route.config

## app.json
It's a json formatted file where the main application configuration is defined. e.g. Service Providers, Middlewares, etc...
In the app.json you can use varaibles like {{ my_var }} which are defined in the `env.properties`

## env.properties
This file is gitignored because usually it will contain sensitive data like credentials, encryptation keys and other
sensitive or environment-dependent variables. Any variable declared in this file can be used in the app.json file in the format
of {{ fb-app-secret }} where fb-app-secret is a key defined in env.properties 

## route.config
The format of every entry is `http-method url-pattern controller.controller-method(params) route_name` 

e.x: 
```
GET / com.mathiasgrimm.app.controller.IndexController.index()
GET /user/:id com.mathiasgrimm.app.controller.UserController.index(Integer)
```

Comments are allowed in any part of this file. To comment anything add a `#` anywhere

# Entry Point

The main entry point of the application is the com.mathiasgrimm.lib.servlet.MainServlet which boots the
com.mathiasgrimm.lib.Application

# Container
At the moment every component defined in the container will be treated as a singleton so keep in mind that any variables defined
in your classes can have a negative effect due to the threaded nature of the servlets.

You can register Services Provider in the rosource/app.json under the `service-providers` object.
The provider have to implement the `ServiceProviderInterface`. The providers will be registered in the same order they are
define in the config.

If your component depends one other components you can define the constructor param type with the types that are 
registered in the container and it will automatically inject them for you. Remeber to add `@Inject` to your constructor

```
public class IndexController {

    private AppConfig appConfig;
    private SomeConcretClass someConcretClass;

    @Inject
    public IndexController(AppConfig appConfig, SomeConcreteClass someConcreteClass) {
        this.appConfig = appConfig;
        this.someConcreteClass = someConcreteClass;
    }
}

public class SomeConcreteClass {

}
```

if the type of your parameter is a concrete class it does not need to be registered in the container 


## Service Providers
To register a component you have to use the service providers, ex.:

```
public class HttpServiceProvider implements ServiceProviderInterface {

    @Override
    public void register(Container container) throws Exception {
        container.set(MailerInterface.class, (Container ct, Class type) -> {
            AppConfig appConfig = ct.get(AppConfig.class);
            
            return new MailgunAdapter(
                appConfig.get()->getString('mailgun-key'),
                appConfig.get()->getString('mailgun-secret')
            );
        });
    }

    @Override
    public void boot(Container container) throws Exception {

    }
}

```

you can call (`container.get`) from any other service provided and it will be loaded independently of the order they were
declared

Before any HTTP request is served, all registered component will be loaded and available

# Controllers
Every controller method requires the first two arguments to the `HttpServletRequest` and `HttpServletRespose` as these
two object cannot be defined in the controller instance due to multi-threading.

# TODO - missing components
- Middleware
- ORM
- much more...