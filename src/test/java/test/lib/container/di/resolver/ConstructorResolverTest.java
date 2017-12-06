package test.lib.container.di.resolver;

import org.junit.Test;

import junit.framework.TestCase;
import lib.container.di.Inject;
import lib.container.di.resolver.ConstructorResolver;
import lib.container.di.resolver.MultipleInjectableConstructorException;

public class ConstructorResolverTest {

	@Test(expected = MultipleInjectableConstructorException.class)
	public void itCanOnlyHaveOneInjectableConstructor() throws Exception {
		TestCase.assertTrue(true);

		ConstructorResolver dr = new ConstructorResolver();
		dr.resolve(ClassWithTwoInjectableConstructors.class);
	}
}

class ClassWithTwoInjectableConstructors {
	@Inject
	public ClassWithTwoInjectableConstructors(String s) {
		//
	}

	@Inject
	public ClassWithTwoInjectableConstructors(Integer i) {
		//
	}
}