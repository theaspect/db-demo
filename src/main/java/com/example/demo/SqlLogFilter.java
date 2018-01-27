package com.example.demo;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class SqlLogFilter extends Filter<ILoggingEvent> {
    private static final Logger log = LoggerFactory.getLogger(SqlLogFilter.class);

    private static final Map<String, Long> queryCounter = new ConcurrentHashMap<>();

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (event.getLoggerName().equals("org.hibernate.SQL")) {
            queryCounter.compute(event.getThreadName(), (k, v) -> v + 1);
        }
        return FilterReply.NEUTRAL;
    }

    /**
     * TODO create assert counter for SQL queries
     */
    public static <V> V withCounter(Callable<V> closure) {
        Long begin = System.nanoTime();
        String id = Thread.currentThread().getName();
        queryCounter.put(id, 0L);

        V result;
        try {
            result = closure.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        log.info("*** In thread {} executed {} queries {} ms", id, queryCounter.get(id), (System.nanoTime() - begin) / 1_000_000);
        queryCounter.put(id, 0L);
        return result;
    }
}
