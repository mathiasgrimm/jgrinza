package com.mathiasgrimm.lib.logger.formatter;

import com.mathiasgrimm.lib.logger.LogRecord;

public interface RecordFormatterInterface {

    public String format(LogRecord logRecord);
}
