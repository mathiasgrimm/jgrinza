package com.mathiasgrimm.test.lib.logger;

import com.mathiasgrimm.lib.logger.LogLevel;
import com.mathiasgrimm.lib.logger.LogRecord;
import com.mathiasgrimm.lib.logger.formatter.RecordFormatterInterface;
import com.mathiasgrimm.lib.logger.formatter.TextRecordFormatter;
import junit.framework.TestCase;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TextRecordFormatterTest {

    @Test
    public void itFormatsWhenUsingUtc() {

        LogRecord logRecord = new LogRecord(
                "testing 1...2...3...",
                "test-channel",
                LogLevel.DEBUG,
                ZonedDateTime.of(2017, 1, 31, 23, 59, 59, 123000000, ZoneId.of("UTC"))
        );

        RecordFormatterInterface formatter = new TextRecordFormatter();
        TestCase.assertEquals(
            "[Tue 2017-01-31 23:59:59.123 Z UTC] [test-channel] [debug(7)] testing 1...2...3...\n",
            formatter.format(logRecord)
        );
    }

    @Test
    public void itFormatForDifferentTimeZones() {
        LogRecord logRecord = new LogRecord(
            "testing 1...2...3...",
            "test-channel",
            LogLevel.DEBUG,
            ZonedDateTime.of(2017, 1, 31, 23, 59, 59, 123000000, ZoneId.of("UTC"))
        );

        RecordFormatterInterface formatter = new TextRecordFormatter(ZoneId.of("America/Sao_Paulo"));
        TestCase.assertEquals(
            "[Tue 2017-01-31 21:59:59.123 -02:00 America/Sao_Paulo] [test-channel] [debug(7)] testing 1...2...3...\n",
            formatter.format(logRecord)
        );
    }

}


