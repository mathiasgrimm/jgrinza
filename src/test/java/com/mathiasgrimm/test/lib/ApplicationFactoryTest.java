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


        String path = this.getClass().getResource("/app.log").getPath();


        // File f = new File(path.replaceAll("file:", ""));
        // System.out.println("file exists: " + f.exists() + " path: " + path);



//        FileWriter fw      = new FileWriter(path, true);
//        BufferedWriter bw  = new BufferedWriter(fw);
//        Writer writer      = new PrintWriter(bw);
//
//        new LogFileHandler(writer, new TextRecordFormatter(), new ArrayList<>());
    }
}
