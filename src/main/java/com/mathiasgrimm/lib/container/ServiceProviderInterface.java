package com.mathiasgrimm.lib.container;

public interface ServiceProviderInterface {
    public void register(Container container) throws Exception;
    public void boot(Container container) throws Exception;
}
