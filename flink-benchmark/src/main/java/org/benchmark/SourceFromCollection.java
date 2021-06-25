package org.benchmark;

import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class SourceFromCollection{
    public static void main(String[] args) throws Exception{
        // 创建执行环境
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        streamExecutionEnvironment.setParallelism(3);
        // 从集合中读取数据
        DataStream<SensorReading> sensorReadingDataStreamSource = streamExecutionEnvironment.fromCollection(Arrays.asList(
                new SensorReading("sensor_1", 1547718199L, 35.8),
                new SensorReading("sensor_6", 1547718201L, 15.4),
                new SensorReading("sensor_7", 1547718202L, 6.7),
                new SensorReading("sensor_10", 1547718205L, 38.1)
        )).setParallelism(1);

        DataStream<Integer> integerDataStreamSource = streamExecutionEnvironment.fromElements(1, 2, 3, 4009, 3434);

        DataStreamSink<Integer> dataStreamSink = streamExecutionEnvironment.fromCollection(new CustomIterator(), BasicTypeInfo.INT_TYPE_INFO).print();

        // 输出
        sensorReadingDataStreamSource.print("sensor");
        integerDataStreamSource.print("int");

        // 执行
        streamExecutionEnvironment.execute();
    }
}

class CustomIterator implements Iterator<Integer>, Serializable {
    private Integer i = 0;

    @Override
    public boolean hasNext() {
        return i < 1000;
    }

    @Override
    public Integer next() {
        i++;
        return i;
    }
}

class SensorReading{
    private String id;
    private Long timeStamp;
    private Double temperature;

    public SensorReading() {
    }

    public SensorReading(String id, Long timeStamp, Double temperature) {
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
        return "SensorReading{" +
                "id='" + id + '\'' +
                ", timeStamp=" + timeStamp +
                ", temperature=" + temperature +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorReading that = (SensorReading) o;
        return Objects.equals(id, that.id) && Objects.equals(timeStamp, that.timeStamp) && Objects.equals(temperature, that.temperature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timeStamp, temperature);
    }
}


