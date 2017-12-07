package com.mathiasgrimm.lib.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import com.mathiasgrimm.lib.container.di.resolver.ConstructorResolver;

public class Container {

	private ConstructorResolver constructorResolver;

	private Map<Class<?>, BinderInterface<?>> binders = new Hashtable<>();
	private Map<Class<?>, Object> bindings = new Hashtable<>();
	private List<ServiceProviderInterface> serviceProviders = new ArrayList<>();

	public Container(ConstructorResolver constructorResolver) {
		this.constructorResolver = constructorResolver;
	}

	/**
	 * register the binding that will get bound the the get method is called
	 * 
	 * you can overwrite a binding unless it has already been initialised (calling
	 * the get method)
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

	public boolean has(Class<?> type) {
		return this.binders.containsKey(type);
	}

	/**
	 * every binding is going to be a singleton
	 * 
	 * first it tries to get from the existing bindings. second it tries to see if
	 * its a registered binding. and third it tries to automatically resolve it
	 * 
	 * @param type
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
		this.bindings.put(type, resolved);

		return resolved;
	}

	/**
	 * recursively resolves the class dependencies
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	protected <T> T resolve(Class<T> type) throws Exception {
		if (this.has(type)) {
			return this.get(type);
		}

		Parameter[] params = this.constructorResolver.resolve(type);

		if (params == null || params.length == 0) {
			return type.getDeclaredConstructor().newInstance();
		}

		List<Object> cttParams = new ArrayList<Object>();

		for (Parameter parameter : params) {
			cttParams.add(this.resolve(parameter.getType()));
		}

		@SuppressWarnings("unchecked")
		Constructor<T>[] constructors = (Constructor<T>[]) type.getConstructors();
		Constructor<T> ctt = (Constructor<T>) this.constructorResolver.getInjectableConstructor(constructors);
		return ctt.newInstance(cttParams.toArray());
	}

	public void register(ServiceProviderInterface serviceProvider)
    {
        this.serviceProviders.add(serviceProvider);
        serviceProvider.register(this);
    }

    public void boot()
    {
        for (ServiceProviderInterface serviceProvider : this.serviceProviders) {
            serviceProvider.boot(this);
        }
    }
}
