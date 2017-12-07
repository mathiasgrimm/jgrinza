package com.mathiasgrimm.lib;

import com.mathiasgrimm.lib.container.Container;
import com.mathiasgrimm.lib.container.di.resolver.ConstructorResolver;

public class ApplicationFactory {

    public Application create()
    {
        Container container = new Container(new ConstructorResolver());
        return new Application(container);
    }

}
