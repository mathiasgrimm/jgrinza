package com.mathiasgrimm.lib.logger;

public enum LogLevel {

    /**
     * System is unusable
     */
    EMERG(0, "emerg"),

    /**
     * Action must be taken immediately.
     *
     * Example: Entire website down, database unavailable, etc. This should
     * trigger the SMS alerts and wake you up.
     */
    ALERT(1, "alert"),

    /**
     * Critical conditions.
     *
     * Example: Application component unavailable, unexpected exception.
     */
    CRITITAL(2, "critical"),

    /**
     * Runtime errors that do not require immediate action but should typically
     * be logged and monitored.
     */
    ERROR(3, "error"),

    /**
     * Exceptional occurrences that are not errors.
     *
     * Example: Use of deprecated APIs, poor use of an API, undesirable things
     * that are not necessarily wrong.
     */
    WARNING(4, "warning"),

    /**
     * Normal but significant events.
     */
    NOTICE(5, "notice"),

    /**
     * Interesting events.
     *
     * Example: User logs in, SQL logs.
     */
    INFO(6, "info"),

    /**
     * Detailed debug information
     */
    DEBUG(7, "debug");

    private final String name;
    private final Integer level;

    public String getName() {
        return name;
    }

    public Integer getLevel() {
        return level;
    }

    LogLevel(Integer level, String name) {
        this.level = level;
        this.name  = name;
    }
}
