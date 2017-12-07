package lib.container.di.resolver;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

import lib.container.di.Inject;

public class ConstructorResolver {
	
	public Parameter[] resolve(Class<?> c) throws Exception
	{
		Constructor<?>[] constructors = c.getConstructors(); 
		
		if (constructors.length == 0) {
			return null;
		}
		
		Constructor<?> ctt = this.getInjectableConstructor(constructors);
		
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
	public void validate(Constructor<?>[] constructors) throws Exception
	{
		int totalInject = 0;
		
		for (Constructor<?> ct : constructors) {
			if (ct.isAnnotationPresent(Inject.class)) {
				totalInject++;
			}
			
			if (totalInject > 1) {
				throw new MultipleInjectableConstructorException(ct.getDeclaringClass());
			}
		}
	}
	
	public Constructor<?> getInjectableConstructor(Constructor<?>[] constructors) throws Exception
	{
		this.validate(constructors);
		
		Constructor<?> ctt = null;
		
		for (Constructor<?> ct : constructors) {
			if (ct.isAnnotationPresent(Inject.class)) {
				ctt = ct;
			}
		}
		
		return ctt;
	}
}
