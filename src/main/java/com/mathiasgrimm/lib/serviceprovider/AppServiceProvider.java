package com.mathiasgrimm.lib.serviceprovider;

import com.mathiasgrimm.lib.container.Container;
import com.mathiasgrimm.lib.container.ServiceProviderInterface;
import com.mathiasgrimm.lib.http.router.Router;
import com.mathiasgrimm.lib.logger.Logger;
import com.mathiasgrimm.lib.logger.formatter.RecordFormatterInterface;
import com.mathiasgrimm.lib.logger.formatter.TextRecordFormatter;
import com.mathiasgrimm.lib.logger.handler.LogFileHandler;
import com.mathiasgrimm.lib.logger.handler.LogHandlerInterface;

import java.util.Arrays;

public class AppServiceProvider implements ServiceProviderInterface {

    @Override
    public void register(Container container) {
//        container.set(Logger.class, (ct, t) -> {
//            String path = this.getClass().getResource("/app.log").toURI().toString();
//            RecordFormatterInterface formatter = new TextRecordFormatter();
//            LogHandlerInterface handler = LogFileHandler.createForStringPath(path, formatter);
//
//            return new Logger(Arrays.asList(handler));
//        });
    }

    @Override
    public void boot(Container container) {

    }
}
