package org.benchmark;

public class SensorReading2 {
    public String id;      // 传感器名称
    public Long timeStamp;     // 时间戳
    public Double temperature;     // 温度

    public SensorReading2() {
    }

    public SensorReading2(String id, Long timeStamp, Double temperature) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.temperature = temperature;
    }

    public String getId() {
        return id;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "SensorReading2{" +
                "id='" + id + '\'' +
                ", timeStamp=" + timeStamp +
                ", temperature=" + temperature +
                '}';
    }
}
