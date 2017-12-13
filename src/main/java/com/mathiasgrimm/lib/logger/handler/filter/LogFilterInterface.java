package com.mathiasgrimm.lib.logger.handler.filter;

import com.mathiasgrimm.lib.logger.LogRecord;

public interface LogFilterInterface {

    public boolean allowed(LogRecord logRecord);
}
