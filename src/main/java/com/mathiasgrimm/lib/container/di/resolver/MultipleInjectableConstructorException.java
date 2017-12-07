package com.mathiasgrimm.lib.container.di.resolver;

public class MultipleInjectableConstructorException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public MultipleInjectableConstructorException(Class<?> c)
	{
		super("Class " + c.getName() + " has more than one constructor with @Inject");
	}
}
