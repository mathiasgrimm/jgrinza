package com.mathiasgrimm.test.lib;

import com.mathiasgrimm.lib.Application;
import com.mathiasgrimm.lib.ApplicationFactory;
import org.junit.Test;

import java.io.IOException;

public class ApplicationFactoryTest {

    @Test
    public void test() throws Exception {
        ApplicationFactory factory = new ApplicationFactory();
        Application app = factory.create();


    }
}
