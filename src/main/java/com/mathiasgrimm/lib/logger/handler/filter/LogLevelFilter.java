package com.mathiasgrimm.lib.logger.handler.filter;

import com.mathiasgrimm.lib.logger.LogLevel;
import com.mathiasgrimm.lib.logger.LogRecord;

public class LogLevelFilter implements LogFilterInterface {

    private LogLevel logLevel;

    public LogLevelFilter(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public boolean allowed(LogRecord logRecord) {
        if (logRecord.getLogLevel().getLevel() > this.logLevel.getLevel()) {
            return false;
        } else {
            return true;
        }
    }
}
