package com.mathiasgrimm.lib.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import com.mathiasgrimm.lib.container.di.resolver.ConstructorResolver;
import com.mathiasgrimm.lib.http.HttpHandler;
import com.mathiasgrimm.lib.http.router.Router;
import com.mathiasgrimm.lib.routeconfig.Route;

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

		System.out.println("resolving " + type.getCanonicalName());

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

	public void register(ServiceProviderInterface serviceProvider) throws Exception {
        this.serviceProviders.add(serviceProvider);
        serviceProvider.register(this);
    }

	/**
	 * Due to the nature of the servlets, everything has to be booted prior to the servlet serving any request otherwise
	 * it would have to be synchronised which would greatly hit the performance.
	 *
	 * Every binding and every controller will be loaded (singleton) on this method
	 *
	 * Make sure all your code can run in a threaded environment, therefore, if you don't want to use
	 * synchronized everywhere, don't keep any state in your components
	 *
	 *
	 * @throws Exception
	 */
	public void boot() throws Exception
    {
		System.out.println("booting service providers");
        for (ServiceProviderInterface serviceProvider : this.serviceProviders) {
            serviceProvider.boot(this);
        }

        // initialising every registered binding to avoid any need for synchronisation if it was run in the context
		// of the servlet handling
		System.out.println("booting bindings");
        for (Class<?> type : this.binders.keySet()) {
			this.get(type);
		}

		this.get(HttpHandler.class);

		System.out.println("booting controllers");
		Router router = this.get(Router.class);

		for (String httpMethod : router.getRoutes().keySet()) {
			for (Route route : router.getRoutes().get(httpMethod)) {
				this.get(Class.forName(route.getController()));
			}
		}
    }
}
