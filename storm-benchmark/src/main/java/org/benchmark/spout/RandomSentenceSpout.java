package org.benchmark.spout;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

public class RandomSentenceSpout extends BaseRichSpout {

    SpoutOutputCollector collector;
    Random rand;


    @Override
    public void open(Map<String, Object> conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        rand = new Random();
    }

    @Override
    public void nextTuple() {
        Utils.sleep(100);
        String[] sentences = new String[]{
                sentence("Using artificial intelligence to solve global atmosphere and ocean science problems and improve prediction accuracy of extreme disastrous weather has been popular these days ENSO EL Nino Southern Oscillation in the tropical Pacific Ocean is the strongest and most obvious yearly climate signal on earth It will cause extreme disasters like flood drought high temperature and snow disaster etc Extremely cold winter in 2020 in China also has something to do with ENSO Climate Dynamical Model costs much computing resource and does not work well in Spring With spatiotemporal series meteorological factor at T moment in last 12 months and historical climate observation and stimulation data you can build a deep learning model to predict ENSO and Nino 3.4 index in future 1-24 months This can help a lot to predict extreme weather and climate Historical Climate Observation and Stimulation Dataset is provided by Institute for Climate and Application Research ICAR There is historical stimulation data by CMIP5/6 mode and historical assimilated observation data by American SODA mode in nearly 100 years Each sample has following weather and spatiotemporal variableabnormal sea surface temperature SST abnormal thermal content T300, abnormal zonal wind Ua, abnormal meridional wind Va and data dimension including year month lat and lon Train set includes label data of Nino3.4 index in matching month Test set has 12 time series made of n sections randomly selected from multiple global sea data It is saved as NPY file"),
                sentence("Using artificial intelligence to solve global atmosphere and ocean science problems and improve prediction accuracy of extreme disastrous weather has been popular these days ENSO EL Nino Southern Oscillation in the tropical Pacific Ocean is the strongest and most obvious yearly climate signal on earth It will cause extreme disasters like flood drought high temperature and snow disaster etc Extremely cold winter in 2020 in China also has something to do with ENSO Climate Dynamical Model costs much computing resource and does not work well in Spring With spatiotemporal series meteorological factor at T moment in last 12 months and historical climate observation and stimulation data you can build a deep learning model to predict ENSO and Nino 3.4 index in future 1-24 months This can help a lot to predict extreme weather and climate Historical Climate Observation and Stimulation Dataset is provided by Institute for Climate and Application Research ICAR There is historical stimulation data by CMIP5/6 mode and historical assimilated observation data by American SODA mode in nearly 100 years Each sample has following weather and spatiotemporal variableabnormal sea surface temperature SST abnormal thermal content T300, abnormal zonal wind Ua, abnormal meridional wind Va and data dimension including year month lat and lon Train set includes label data of Nino3.4 index in matching month Test set has 12 time series made of n sections randomly selected from multiple global sea data It is saved as NPY file"),
                sentence("Using artificial intelligence to solve global atmosphere and ocean science problems and improve prediction accuracy of extreme disastrous weather has been popular these days ENSO EL Nino Southern Oscillation in the tropical Pacific Ocean is the strongest and most obvious yearly climate signal on earth It will cause extreme disasters like flood drought high temperature and snow disaster etc Extremely cold winter in 2020 in China also has something to do with ENSO Climate Dynamical Model costs much computing resource and does not work well in Spring With spatiotemporal series meteorological factor at T moment in last 12 months and historical climate observation and stimulation data you can build a deep learning model to predict ENSO and Nino 3.4 index in future 1-24 months This can help a lot to predict extreme weather and climate Historical Climate Observation and Stimulation Dataset is provided by Institute for Climate and Application Research ICAR There is historical stimulation data by CMIP5/6 mode and historical assimilated observation data by American SODA mode in nearly 100 years Each sample has following weather and spatiotemporal variableabnormal sea surface temperature SST abnormal thermal content T300, abnormal zonal wind Ua, abnormal meridional wind Va and data dimension including year month lat and lon Train set includes label data of Nino3.4 index in matching month Test set has 12 time series made of n sections randomly selected from multiple global sea data It is saved as NPY file"),
                sentence("Using artificial intelligence to solve global atmosphere and ocean science problems and improve prediction accuracy of extreme disastrous weather has been popular these days ENSO EL Nino Southern Oscillation in the tropical Pacific Ocean is the strongest and most obvious yearly climate signal on earth It will cause extreme disasters like flood drought high temperature and snow disaster etc Extremely cold winter in 2020 in China also has something to do with ENSO Climate Dynamical Model costs much computing resource and does not work well in Spring With spatiotemporal series meteorological factor at T moment in last 12 months and historical climate observation and stimulation data you can build a deep learning model to predict ENSO and Nino 3.4 index in future 1-24 months This can help a lot to predict extreme weather and climate Historical Climate Observation and Stimulation Dataset is provided by Institute for Climate and Application Research ICAR There is historical stimulation data by CMIP5/6 mode and historical assimilated observation data by American SODA mode in nearly 100 years Each sample has following weather and spatiotemporal variableabnormal sea surface temperature SST abnormal thermal content T300, abnormal zonal wind Ua, abnormal meridional wind Va and data dimension including year month lat and lon Train set includes label data of Nino3.4 index in matching month Test set has 12 time series made of n sections randomly selected from multiple global sea data It is saved as NPY file"),
                sentence("Using artificial intelligence to solve global atmosphere and ocean science problems and improve prediction accuracy of extreme disastrous weather has been popular these days ENSO EL Nino Southern Oscillation in the tropical Pacific Ocean is the strongest and most obvious yearly climate signal on earth It will cause extreme disasters like flood drought high temperature and snow disaster etc Extremely cold winter in 2020 in China also has something to do with ENSO Climate Dynamical Model costs much computing resource and does not work well in Spring With spatiotemporal series meteorological factor at T moment in last 12 months and historical climate observation and stimulation data you can build a deep learning model to predict ENSO and Nino 3.4 index in future 1-24 months This can help a lot to predict extreme weather and climate Historical Climate Observation and Stimulation Dataset is provided by Institute for Climate and Application Research ICAR There is historical stimulation data by CMIP5/6 mode and historical assimilated observation data by American SODA mode in nearly 100 years Each sample has following weather and spatiotemporal variableabnormal sea surface temperature SST abnormal thermal content T300, abnormal zonal wind Ua, abnormal meridional wind Va and data dimension including year month lat and lon Train set includes label data of Nino3.4 index in matching month Test set has 12 time series made of n sections randomly selected from multiple global sea data It is saved as NPY file")
        };
        final String sentence = sentences[rand.nextInt(sentences.length)];
        collector.emit(new Values(sentence));
    }

    protected String sentence(String input) {
        return input;
    }

    @Override
    public void ack(Object id) {
    }

    @Override
    public void fail(Object id) {
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }

    // Add unique identifier to each tuple, which is helpful for debugging
    public static class TimeStamped extends RandomSentenceSpout {
        private final String prefix;

        public TimeStamped() {
            this("");
        }

        public TimeStamped(String prefix) {
            this.prefix = prefix;
        }

        @Override
        protected String sentence(String input) {
            return prefix + currentDate() + " " + input;
        }

        private String currentDate() {
            return new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss.SSSSSSSSS").format(new Date());
        }
    }
}

