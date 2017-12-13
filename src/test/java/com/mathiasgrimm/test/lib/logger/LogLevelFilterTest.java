package com.mathiasgrimm.test.lib.logger;

import com.mathiasgrimm.lib.logger.LogLevel;
import com.mathiasgrimm.lib.logger.LogRecord;
import com.mathiasgrimm.lib.logger.handler.filter.LogLevelFilter;
import junit.framework.TestCase;
import org.junit.Test;

public class LogLevelFilterTest {

    @Test
    public void itRestrictsGreaterLogLevels() {
        LogLevelFilter verifier = new LogLevelFilter();
        LogRecord logRecord       = new LogRecord("test", LogLevel.DEBUG);

        TestCase.assertFalse(verifier.shouldHandle(logRecord, LogLevel.ERROR));
    }

    @Test
    public void itAllowsLowerLogLevels() {
        LogLevelFilter verifier = new LogLevelFilter();
        LogRecord logRecord       = new LogRecord("test", LogLevel.CRITITAL);

        TestCase.assertTrue(verifier.shouldHandle(logRecord, LogLevel.ERROR));
    }
    @Test
    public void itAllowsSameLogLevels() {
        LogLevelFilter verifier = new LogLevelFilter();
        LogRecord logRecord       = new LogRecord("test", LogLevel.ERROR);

        TestCase.assertTrue(verifier.shouldHandle(logRecord, LogLevel.ERROR));
    }
}
