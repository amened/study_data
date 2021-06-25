package org.benchmark;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.Objects;
import java.util.Random;
import java.util.TreeMap;

public class SourceFromUdf {
    public static void main(String[] args) throws Exception{
        // 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 实现
        DataStreamSource<SensorReading1> sensorReading1DataStreamSource= env.addSource(new SourceFunction<SensorReading1>() {
            private boolean running = true;
            @Override
            public void run(SourceContext<SensorReading1> ctx) throws Exception {
                // 定义一个随机数发生器
                Random random = new Random();
                // 设置10个传感器的初始温度
                TreeMap<String, Double> sensorTempMap = new TreeMap<>();
                for (int i = 0 ; i < 10 ; i ++){
                    sensorTempMap.put("senesor_" + (i + 1) , 60 + random.nextGaussian() * 20);
                }
                while (running){
                    for (String sensorID:sensorTempMap.keySet()){
                        // 在当前温度基础上随机波动
                        double newTmp = sensorTempMap.get(sensorID) + random.nextGaussian();
                        // 更改之后的温度传上去
                        sensorTempMap.put(sensorID , newTmp);
                        // 发送数据
                        ctx.collect(new SensorReading1(sensorID , System.currentTimeMillis() , newTmp));
                    }
                    //控制输出频率
                    Thread.sleep(1000);
                }
            }
            @Override
            public void cancel() {
                this.running = false;
            }
        }).setParallelism(1);

        sensorReading1DataStreamSource.print();

        // 启动Job
        env.execute();
    }
}

class SensorReading1{
    private String id;      // 传感器名称
    private Long timeStamp;     // 时间戳
    private Double temperature;     // 温度

    public SensorReading1() {
    }

    public SensorReading1(String id, Long timeStamp, Double temperature) {
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
        SensorReading1 that = (SensorReading1) o;
        return Objects.equals(id, that.id) && Objects.equals(timeStamp, that.timeStamp) && Objects.equals(temperature, that.temperature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timeStamp, temperature);
    }
}
