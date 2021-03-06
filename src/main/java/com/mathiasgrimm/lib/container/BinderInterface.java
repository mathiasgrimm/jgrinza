package com.mathiasgrimm.lib.container;

@FunctionalInterface
public interface BinderInterface<T> {
	public T bind(Container container, Class<T> type) throws Exception;
}
