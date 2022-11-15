package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

public class Influx {

    private String time;
    private double value;
    private String device_identifier;


    private static final Logger logger = LoggerFactory.getLogger(Influx.class);



    private Integer size = 0;
    private double sum = 0.0;

    private double standardDeviation = 0.0;
    private double lastStandardDeviation = 0.0;

    private double variance = 0.0;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);

        this.time = time;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDevice_identifier() {
        return device_identifier;
    }

    public void setDevice_identifier(String device_identifier) {
        this.device_identifier = device_identifier;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public double getLastStandardDeviation() {
        return lastStandardDeviation;
    }

    public void setLastStandardDeviation(double lastStandardDeviation) {
        this.lastStandardDeviation = lastStandardDeviation;
    }

    public double getVariance() {
        return variance;
    }

    public void setVariance(double variance) {
        this.variance = variance;
    }
}
