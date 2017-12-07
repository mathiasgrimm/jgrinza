package com.mathiasgrimm.lib.container;

public interface ServiceProviderInterface {
    public void register(Container container);
    public void boot(Container container);
}
