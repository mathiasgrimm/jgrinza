package com.mathiasgrimm.test.lib;

import com.mathiasgrimm.lib.Application;
import com.mathiasgrimm.lib.ApplicationFactory;
import com.mathiasgrimm.lib.logger.formatter.TextRecordFormatter;
import com.mathiasgrimm.lib.logger.handler.LogFileHandler;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

public class ApplicationFactoryTest {
    @Test
    public void itBootsWithoutFailing() throws Exception {
        ApplicationFactory factory = new ApplicationFactory();
        Application app = factory.create();
    }
}
