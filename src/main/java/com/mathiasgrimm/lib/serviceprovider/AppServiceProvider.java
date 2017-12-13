package com.mathiasgrimm.lib.serviceprovider;

import com.mathiasgrimm.lib.container.Container;
import com.mathiasgrimm.lib.container.ServiceProviderInterface;
import com.mathiasgrimm.lib.http.router.Router;
import com.mathiasgrimm.lib.logger.Logger;
import com.mathiasgrimm.lib.logger.formatter.RecordFormatterInterface;
import com.mathiasgrimm.lib.logger.formatter.TextRecordFormatter;
import com.mathiasgrimm.lib.logger.handler.LogFileHandler;
import com.mathiasgrimm.lib.logger.handler.LogHandlerInterface;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class AppServiceProvider implements ServiceProviderInterface {

    @Override
    public void register(Container container) {
        container.set(Logger.class, (ct, t) -> {
            // TODO too ugly!
            String resourcePath = this.getClass().getResource("/.").getPath();

            URL path = this.getClass().getResource("/app.log");
            Path newFilePath  = null;
            String targetPath = null;

            if (path == null) {
                newFilePath = Paths.get(resourcePath + "/app.log");
                Files.createFile(newFilePath);
                targetPath = resourcePath + "/app.log";
            } else {
                targetPath = path.getPath();
            }

            RecordFormatterInterface formatter = new TextRecordFormatter();
            LogHandlerInterface handler = LogFileHandler.createForStringPath(targetPath, formatter);

            return new Logger(Arrays.asList(handler));
        });
    }

    @Override
    public void boot(Container container) {

    }
}
