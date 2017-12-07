package com.mathiasgrimm.lib.container.di.resolver;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

import com.mathiasgrimm.lib.container.di.Inject;

public class ConstructorResolver {
	
	public <T> Parameter[] resolve(Class<T> c) throws Exception
	{
		@SuppressWarnings("unchecked")
		Constructor<T>[] constructors = (Constructor<T>[]) c.getConstructors(); 
		
		if (constructors.length == 0) {
			return null;
		}
		
		Constructor<T> ctt = this.getInjectableConstructor(constructors);
		
		if (ctt == null) {
			return null;
		}
		
		return ctt.getParameters();
	}
	
	/**
	 * checks if a given class has only one @Inject constructor
	 * 
	 * @param constructors
	 * @throws Exception
	 */
	public <T> void validate(Constructor<T>[] constructors) throws Exception
	{
		int totalInject = 0;
		
		for (Constructor<T> ct : constructors) {
			if (ct.isAnnotationPresent(Inject.class)) {
				totalInject++;
			}
			
			if (totalInject > 1) {
				throw new MultipleInjectableConstructorException(ct.getDeclaringClass());
			}
		}
	}
	
	public <T> Constructor<T> getInjectableConstructor(Constructor<T>[] constructors) throws Exception
	{
		this.validate(constructors);

		@SuppressWarnings("unchecked")
		Constructor<T> ctt = null;
		
		for (Constructor<?> ct : constructors) {
			if (ct.isAnnotationPresent(Inject.class)) {
				ctt = (Constructor<T>) ct;

			}
		}
		
		return ctt;
	}
}
