package lib.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import lib.container.di.Inject;
import lib.container.di.resolver.ConstructorResolver;

// TODO remove void main and other classes from here!

public class Container {

	private ConstructorResolver constructorResolver;
	
	private Map<Class<?>, BinderInterface<?>> binders = new Hashtable<Class<?>, BinderInterface<?>>();
	private Map<Class<?>, Object> bindings = new Hashtable<Class<?>, Object>();

	public Container(ConstructorResolver constructorResolver) {
		this.constructorResolver = constructorResolver;
	}
	
	/**
	 * register the binding that will get bound the the get method is called
	 * 
	 * you can overwrite a binding unless it has already been initialised (calling the get method)
	 * 
	 * <code>
	 * c.set(A.class, (ct, t) -> {
	 *   return new AImpl();
	 * });
	 * </code>
	 * 
	 * @param type
	 * @param binder
	 */
	public void set(Class<?> type, BinderInterface<?> binder) {
		if (this.bindings.containsKey(type)) {
			throw new RuntimeException("type " + type.getCanonicalName() + " has already been initialised");
		}
		this.binders.put(type, binder);
	}
	
	public boolean has(Class<?> type)
	{
		return this.binders.containsKey(type);
	}

	/**
	 * every binding is going to be a singleton
	 * 
	 * first it tries to get from the existing bindings.
	 * second it tries to see if its a registered binding.
	 * and third it tries to automatically resolve it
	 * 
	 * @param type
	 * @return
	 * @throws Exception 
	 */
	public <T> T get(Class<T> type) throws Exception {
		// from existing binding
		if (this.bindings.containsKey(type)) {
			return (T) this.bindings.get(type);
		}

		// from the binders
		if (this.binders.containsKey(type)) {
			BinderInterface<T> binder = (BinderInterface<T>) this.binders.get(type);
			T bound = binder.bind(this, type);
			this.bindings.put(type, bound);
			
			return bound;	
		}

		// dynamically resolves it if possible
		T resolved = this.resolve(type);
		
		return resolved;
	}

	protected <T> T resolve(Class<T> type) throws Exception {
		System.out.println("Resolving type " + type.getCanonicalName());
		
		if (this.has(type)) {
			return this.get(type);
		}
		
		Parameter[] params = this.constructorResolver.resolve(type);
		
		if (params == null || params.length == 0) {
			System.out.println("no params");
			return type.getDeclaredConstructor().newInstance();
		}
		
		List<Object> cttParams =  new ArrayList<Object>();
		
		for (Parameter parameter : params) {
			System.out.println("parameter " + parameter.getClass().getCanonicalName());
			cttParams.add(this.resolve(parameter.getType()));
		}
		
		Constructor<T> ctt = (Constructor<T>) this.constructorResolver.getInjectableConstructor(type.getConstructors());
		return ctt.newInstance(cttParams.toArray());
	}

	public static void main(String[] args) throws Exception {
		Container c = new Container(new ConstructorResolver());

		c.set(A.class, (ct, t) -> {
			return new AImpl();
		});
		
		System.out.println(c.get(Service.class).getClass().getCanonicalName());
	}

}

interface A {
	public void doIt();
}

class AImpl implements A {
	public AImpl() {
		System.out.println("Constructor");
	}
	
	public void doIt() {
		System.out.println("doing something");
	}
}

class Service
{
	//@Inject
	public Service()
	{
		
	}
	
	@Inject
	public Service(A someA)
	{
		someA.doIt();
	}
}