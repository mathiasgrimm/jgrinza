package com.mathiasgrimm.lib.logger;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class LogRecord {

    private ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of("UTC"));
    private String message;
    private LogLevel logLevel = LogLevel.DEBUG;
    private String channel = "default";

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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public LogRecord(String message, String channel, LogLevel logLevel, ZonedDateTime dateTime) {
        this.message  = message;
        this.channel  = channel;
        this.logLevel = logLevel;
        this.dateTime = dateTime;
    }

    public LogRecord(String message, String channel, LogLevel logLevel) {
        this.message  = message;
        this.channel  = channel;
        this.logLevel = logLevel;
    }

    public LogRecord(String message, String channel) {
        this.message = message;
        this.channel = channel;
    }

    public LogRecord(String message) {
        this.message = message;
    }
}
