package com.mathiasgrimm.lib.logger;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class LogRecord {

    private ZonedDateTime dateTime;
    private String message;
    private LogLevel logLevel;

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public LogRecord(String message, LogLevel logLevel, ZonedDateTime dateTime) {
        this.message  = message;
        this.logLevel = logLevel;
        this.dateTime = dateTime;
    }

    public LogRecord(String message, LogLevel logLevel) {
        this(message, logLevel, ZonedDateTime.now(ZoneId.of("UTC")));
    }

    public LogRecord(String message) {
        this(message, LogLevel.DEBUG);
    }
}
