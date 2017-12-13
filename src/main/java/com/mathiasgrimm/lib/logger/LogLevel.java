package com.mathiasgrimm.lib.logger;

public enum LogLevel {

    EMERG(0, "emerg"),
    ALERT(1, "alert"),
    CRITITAL(2, "critical"),
    ERROR(3, "error"),
    WARNING(4, "warning"),
    NOTICE(5, "notice"),
    INFO(6, "info"),
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
