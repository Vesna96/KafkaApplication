package com.monitor.drop.pressure.kafka.stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitor.drop.pressure.kafka.config.serde.CustomSerdes;
import model.Influx;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Function;

@Component
public class PressureDetectionStream {

    @Bean
    public Serde<Influx> influxSerde() {

        return CustomSerdes.InfluxSerde();
    }

    @Value("${window.value}")
    public Integer window;

        @Bean
    public Function<KStream<String, Influx>, KStream<String, Influx>> pressureDetectStreams() {

        Predicate<Object, Influx> isP1 = (k, v) -> v.getDevice_identifier().equals("P1");

        ObjectMapper om = new ObjectMapper();

        return input -> input.map((k, v) -> KeyValue.pair(v.getDevice_identifier(), v))
                //.filter(isP1)
                .groupByKey(Grouped.with("group-by",Serdes.String(), CustomSerdes.InfluxSerde()))
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(window)))
                .aggregate(Influx::new, (key, value, aggregate) -> {

                            aggregate.setSize(aggregate.getSize() + 1);
                            aggregate.setSum(aggregate.getSum() + value.getValue());
                            aggregate.setDevice_identifier(value.getDevice_identifier());
                            aggregate.setTime(LocalDateTime.now().toString());

                            aggregate.setLastStandardDeviation(aggregate.getStandardDeviation());

                            double mean =  aggregate.getSum() / aggregate.getSize();
                            double standardDeviation = 0;
                            double sq = 0;
                            double res = 0;
                            standardDeviation = standardDeviation + Math.pow((value.getValue() - mean), 2);

                            sq = standardDeviation / aggregate.getSize();
                            res = Math.sqrt(sq);

                            aggregate.setStandardDeviation(res);

                            if(aggregate.getStandardDeviation() != 0 && aggregate.getLastStandardDeviation() != 0) {
                                double variance = (aggregate.getStandardDeviation() / aggregate.getLastStandardDeviation()) * 100;

                                aggregate.setVariance(variance);

                                if(variance > 0.02) {
                                    System.out.println(" VARIANCE: " + variance );
                                }
                                try {
                                    System.out.println(" StV: " + om.writeValueAsString(aggregate) + "\n" + "VARIANCE: " + variance );
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                    return aggregate;


                }, Materialized.with(Serdes.String(), CustomSerdes.InfluxSerde())).toStream().map((k,v) -> KeyValue.pair(k.key(), v));

    }

}
