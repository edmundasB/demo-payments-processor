package com.demo.payment.service.ipresolver;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.slf4j.event.Level;

import java.util.List;
import java.util.stream.Collectors;

public class MemoryAppender extends ListAppender<ILoggingEvent> {
    public List<ILoggingEvent> search(String string) {
        return this.list.stream()
                .filter(event -> event.getMessage().toString().contains(string))
                .collect(Collectors.toList());
    }

    public List<ILoggingEvent> search(String string, Level level) {
        return this.list.stream()
                .filter(event -> event.getMessage().toString().contains(string)
                        && event.getLevel().equals(level))
                .collect(Collectors.toList());
    }
}
