package com.mathiasgrimm.lib.logger;

import com.mathiasgrimm.lib.logger.handler.LogHandlerInterface;

import java.util.List;

public class Logger {

    private List<LogHandlerInterface> handlers;

    public Logger(List<LogHandlerInterface> handlers) throws Exception {
        if (handlers == null) {
            throw new Exception("handlers not initialised");
        }

        this.handlers = handlers;
    }

    public void log(String message, LogLevel logLevel) {
        LogRecord logRecord = new LogRecord(message, logLevel);

        for (LogHandlerInterface handler : this.handlers) {
            try {
                handler.handle(logRecord);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void debug(String message) {
        this.log(message, LogLevel.DEBUG);
    }

    public void info(String message) {
        this.log(message, LogLevel.INFO);
    }

    public void notice(String message) {
        this.log(message, LogLevel.NOTICE);
    }

    public void warning(String message) {
        this.log(message, LogLevel.WARNING);
    }

    public void error(String message) {
        this.log(message, LogLevel.ERROR);
    }

    public void critical(String message) {
        this.log(message, LogLevel.CRITITAL);
    }

    public void alert(String message) {
        this.log(message, LogLevel.ALERT);
    }

    public void emerg(String message) {
        this.log(message, LogLevel.EMERG);
    }
}
