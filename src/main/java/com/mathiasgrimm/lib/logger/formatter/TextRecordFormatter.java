package com.mathiasgrimm.lib.logger.formatter;

import com.mathiasgrimm.lib.logger.LogRecord;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TextRecordFormatter  implements RecordFormatterInterface {

    private ZoneId zoneId = ZoneId.of("UTC");
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd k:mm:ss.SSS ZZZZZ VV");

    public ZoneId getZoneId() {
        return zoneId;
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public TextRecordFormatter() {

    }

    public TextRecordFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public TextRecordFormatter(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public TextRecordFormatter(ZoneId zoneId, DateTimeFormatter dateTimeFormatter) {
        this.zoneId            = zoneId;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public String format(LogRecord logRecord) {
        return "[" + logRecord.getDateTime().withZoneSameInstant(this.zoneId).format(this.dateTimeFormatter) + "] ["
                + logRecord.getLogLevel().getName()
                + "(" + logRecord.getLogLevel().getLevel() + ")] "
                + logRecord.getMessage()
                + "\n";
    }
}


