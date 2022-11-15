package com.monitor.drop.pressure.kafka.config.serde;

import model.Influx;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public final class CustomSerdes {

    private CustomSerdes() {}

    public static Serde<Influx> InfluxSerde() {
        JsonSerializer<Influx> serializer = new JsonSerializer<>();
        JsonDeserializer<Influx> deserializer = new JsonDeserializer<>(Influx.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }


}
