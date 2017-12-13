package com.mathiasgrimm.test.lib;

import com.mathiasgrimm.lib.Application;
import com.mathiasgrimm.lib.ApplicationFactory;
import org.junit.Test;

public class ApplicationFactoryTest {
    @Test
    public void itBootsWithoutFailing() throws Exception {
        ApplicationFactory factory = new ApplicationFactory();
        Application app = factory.create();
    }
}
