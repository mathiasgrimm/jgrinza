package com.mathiasgrimm.lib.logger.handler;

import com.mathiasgrimm.lib.logger.LogLevel;
import com.mathiasgrimm.lib.logger.LogRecord;
import com.mathiasgrimm.lib.logger.formatter.RecordFormatterInterface;
import com.mathiasgrimm.lib.logger.handler.filter.LogFilterInterface;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

public class LogFileHandler implements LogHandlerInterface {

    private String path;
    private RecordFormatterInterface formatter;
    private LogLevel logLevel;
    private Writer writer;
    private List<LogFilterInterface> filters;

    public LogFileHandler(Writer writer, RecordFormatterInterface formatter, List<LogFilterInterface> filters) {
        this.writer    = writer;
        this.formatter = formatter;
        this.logLevel  = logLevel;
        this.filters   = filters;
    }

    public LogFileHandler(Writer writer, RecordFormatterInterface formatter) {
        this.writer = writer;
        this.formatter = formatter;
    }

    @Override
    public void handle(LogRecord logRecord) throws Exception{

        if (this.filters != null && this.filters.size() > 0) {
            for (LogFilterInterface filter : this.filters) {
                if (!filter.allowed(logRecord)) {
                    return;
                }
            }
        }

        this.write(this.formatter.format(logRecord));
    }

    protected void write(String contents) throws Exception {
        this.writer.append(contents).flush();
    }

    // -----------------------------------------------------------------------------------------------------------------
    // FACTORY METHODS
    // -----------------------------------------------------------------------------------------------------------------

//    public static LogFileHandler createForStringPath(String path, RecordFormatterInterface formatter) throws Exception {
//        return LogFileHandler.createForStringPath(path, formatter, LogLevel.DEBUG);
//    }
//
//    public static LogFileHandler createForStringPath(
//            String path,
//            RecordFormatterInterface formatter,
//            List<>
//    ) throws Exception {
//
//        FileWriter fw      = new FileWriter(path, true);
//        BufferedWriter bw  = new BufferedWriter(fw);
//        Writer writer      = new PrintWriter(bw);
//
//        return new LogFileHandler(writer, formatter, logLevel);
//    }
}
