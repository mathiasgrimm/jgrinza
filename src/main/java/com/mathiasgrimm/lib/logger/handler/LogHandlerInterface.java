package com.mathiasgrimm.lib.logger.handler;

import com.mathiasgrimm.lib.logger.LogRecord;

public interface LogHandlerInterface {

    public void handle(LogRecord logRecord) throws Exception;
}
