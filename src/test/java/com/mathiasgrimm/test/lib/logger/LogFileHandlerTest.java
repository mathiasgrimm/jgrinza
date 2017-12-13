package com.mathiasgrimm.test.lib.logger;

import com.mathiasgrimm.lib.logger.LogLevel;
import com.mathiasgrimm.lib.logger.LogRecord;
import com.mathiasgrimm.lib.logger.formatter.TextRecordFormatter;
import com.mathiasgrimm.lib.logger.handler.LogFileHandler;
import com.mathiasgrimm.lib.logger.handler.filter.LogLevelFilter;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.Writer;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;

public class LogFileHandlerTest {

    private Writer getWriterMock() {
        return spy(Writer.class);
    }

    @Test
    public void itWrites() throws Exception {
        Writer writer = this.getWriterMock();

        LogFileHandler handler = new LogFileHandler(writer, new TextRecordFormatter());
        ZonedDateTime dateTime = ZonedDateTime.of(2017, 1, 31, 23, 59, 59, 123000000, ZoneId.of("UTC"));
        LogRecord record       = new LogRecord("testing...", LogLevel.DEBUG, dateTime);

        handler.handle(record);

        Mockito.verify(writer).append("[Tue 2017-01-31 23:59:59.123 Z UTC] [debug(7)] testing...\n");
    }

    @Test
    public void itChecksLogLevel() throws Exception{
        Writer writer          = this.getWriterMock();
        LogFileHandler handler = new LogFileHandler(
            writer,
            new TextRecordFormatter(),
            Arrays.asList(new LogLevelFilter(LogLevel.ERROR))
        );

        LogRecord record = new LogRecord("testing...", LogLevel.DEBUG);
        handler.handle(record);

        // TODO could not get verify(write, never()) to work
        Mockito.verifyNoMoreInteractions(writer);

        record = new LogRecord("testing...", LogLevel.ERROR);
        handler.handle(record);

        verify(writer, times(1)).append(Mockito.anyString());
    }
}
