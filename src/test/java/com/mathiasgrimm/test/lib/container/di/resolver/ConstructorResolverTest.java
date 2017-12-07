package com.mathiasgrimm.test.lib.container.di.resolver;

import junit.framework.TestCase;
import org.junit.Test;

import com.mathiasgrimm.lib.container.di.Inject;
import com.mathiasgrimm.lib.container.di.resolver.ConstructorResolver;
import com.mathiasgrimm.lib.container.di.resolver.MultipleInjectableConstructorException;

public class ConstructorResolverTest {

    @Test(expected = MultipleInjectableConstructorException.class)
    public void itCanOnlyHaveOneInjectableConstructor() throws Exception {
        ConstructorResolver cr = new ConstructorResolver();
        cr.resolve(ClassWithTwoInjectableConstructors.class);
    }

    @Test
    public void whenItHasNoInjectableConstructorItShouldReturnNull() throws Exception {
        ConstructorResolver cr = new ConstructorResolver();
        TestCase.assertNull(cr.resolve(ClassWithNoInjectableConstructor.class));

        TestCase.assertNull(cr.resolve(ClassWithNoConstructor.class));
    }
}

class ClassWithNoConstructor {
    //
}

class ClassWithNoInjectableConstructor {
    //
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