package lib.container;

@FunctionalInterface
public interface BinderInterface<T> {
	public T bind(Container container, Class<?> type);
}
