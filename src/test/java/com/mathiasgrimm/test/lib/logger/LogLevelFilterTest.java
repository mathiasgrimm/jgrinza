package com.mathiasgrimm.test.lib.logger;

import com.mathiasgrimm.lib.logger.LogLevel;
import com.mathiasgrimm.lib.logger.LogRecord;
import com.mathiasgrimm.lib.logger.handler.filter.LogLevelFilter;
import junit.framework.TestCase;
import org.junit.Test;

public class LogLevelFilterTest {

    @Test
    public void itRestrictsGreaterLogLevels() {
        LogLevelFilter filter = new LogLevelFilter(LogLevel.ERROR);
        LogRecord logRecord   = new LogRecord("test", "test-channel", LogLevel.DEBUG);

        TestCase.assertFalse(filter.allowed(logRecord));
    }

    @Test
    public void itAllowsLowerLogLevels() {
        LogLevelFilter filter = new LogLevelFilter(LogLevel.ERROR);
        LogRecord logRecord       = new LogRecord("test", "test-channel", LogLevel.CRITITAL);

        TestCase.assertTrue(filter.allowed(logRecord));
    }
    @Test
    public void itAllowsSameLogLevels() {
        LogLevelFilter filter = new LogLevelFilter(LogLevel.ERROR);
        LogRecord logRecord       = new LogRecord("test", "test-channel", LogLevel.ERROR);

        TestCase.assertTrue(filter.allowed(logRecord));
    }
}
