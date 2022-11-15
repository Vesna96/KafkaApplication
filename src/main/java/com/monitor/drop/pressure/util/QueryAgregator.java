package com.monitor.drop.pressure.util;

import org.apache.kafka.streams.kstream.Aggregator;

public class QueryAgregator implements Aggregator<String, String, byte[]> {
    @Override
    public byte[] apply(String s, String s2, byte[] bytes) {
        return new byte[0];
    }
}
