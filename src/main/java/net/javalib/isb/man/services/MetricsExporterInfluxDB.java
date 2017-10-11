package net.javalib.isb.man.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.javalib.isb.man.pojo.metrics.*;
import net.javalib.isb.man.services.influxdb.InfluxDBService;
import net.javalib.isb.man.services.influxdb.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty("influxdb.url")
public class MetricsExporterInfluxDB implements MetricsExporter {
    private static final Logger logger = LoggerFactory.getLogger(MetricsExporterInfluxDB.class);
    private InfluxDBService db;

    @Autowired
    public MetricsExporterInfluxDB(InfluxDBService db) {
        this.db = db;
    }

    @Override
    public int exportMetrics(Collection<Metric> metrics) {
        List<Point> points = new ArrayList<>();
        long timestamp = System.currentTimeMillis();
        for (Metric metric : metrics) {
            Point point = convertToPoint(metric,timestamp);
            if (point != null) {
                points.add(point);
            }
        }
        db.store("isb",points);
        logger.info("exported {} points",points.size());
        return points.size();
    }

    private Point convertToPoint(Metric metric, long timestamp) {
        if (metric.is(Metric.GAUGE)) {
            boolean isNumber = ((Gauge)metric).getMetric().getValue() instanceof Number;
            if (!isNumber) {
                return null;
            }
        }
        Point.Builder p = Point.measurement(measurementName(metric))
                .tag("env",metric.getEnv())
                .tag("service_type",metric.getServiceType())
                .tag("host",metric.getHostname())
                .tag("registry",metric.getRegistry())
                .tag("identifier",metric.getName())
                .tag("type",metric.getType())
                .time(timestamp, TimeUnit.MILLISECONDS);
        if (metric.is(Metric.TIMER)) {
            append((Timer)metric,p);
        } else if (metric.is(Metric.COUNTER)) {
            append((Counter)metric,p);
        } else if (metric.is(Metric.GAUGE)) {
            append((Gauge) metric,p);
        } else if (metric.is(Metric.METER)) {
            append((Meter) metric,p);
        }
        return p.build();
    }

    private String measurementName(Metric metric) {
        return metric.getType();
    }

    private void append(Timer timer, Point.Builder p) {
        Timer.MetricValue value = timer.getMetric();
        p.addField("count",value.getCount());
        if (isNumber(value.getMeanRate()))
            p.addField("meanRate",value.getMeanRate());
        if (isNumber(value.getOneMinuteRate()))
            p.addField("oneMinuteRate",value.getOneMinuteRate());
        if (isNumber(value.getFifteenMinuteRate()))
            p.addField("fifteenMinuteRate",value.getFifteenMinuteRate());
        if (isNumber(value.getFiveMinuteRate()))
            p.addField("fiveMinuteRate",value.getFiveMinuteRate());

        p.addField("snapshot_min",value.getSnapshot().getMin());
        p.addField("snapshot_max",value.getSnapshot().getMax());
        p.addField("snapshot_98thPercentile",value.getSnapshot().get98thPercentile());
        p.addField("snapshot_99thPercentile",value.getSnapshot().get99thPercentile());
        p.addField("snapshot_95thPercentile",value.getSnapshot().get95thPercentile());
        p.addField("snapshot_median",value.getSnapshot().getMedian());
        p.addField("snapshot_75thPercentile",value.getSnapshot().get75thPercentile());
        p.addField("snapshot_999thPercentile",value.getSnapshot().get999thPercentile());
        if (isNumber(value.getSnapshot().getMean()))
            p.addField("snapshot_mean",value.getSnapshot().getMean());
        if (isNumber(value.getSnapshot().getStdDev()))
            p.addField("snapshot_stdDev",value.getSnapshot().getStdDev());

    }

    private void append(Counter counter, Point.Builder p) {
        p.addField("count",counter.getMetric().getCount());
    }

    private void append(Gauge gauge, Point.Builder p) {
        Number number = (Number)gauge.getMetric().getValue();
        p.addField("value",number.doubleValue());
    }

    private void append(Meter meter, Point.Builder p) {
        Meter.MetricValue value = meter.getMetric();
        p.addField("count",value.getCount());
        if (isNumber(value.getMeanRate()))
            p.addField("meanRate",value.getMeanRate());
        if (isNumber(value.getOneMinuteRate()))
            p.addField("oneMinuteRate",value.getOneMinuteRate());
        if (isNumber(value.getFifteenMinuteRate()))
            p.addField("fifteenMinuteRate",value.getFifteenMinuteRate());
        if (isNumber(value.getFiveMinuteRate()))
            p.addField("fiveMinuteRate",value.getFiveMinuteRate());
    }

    private boolean isNumber(double value) {
        return !Double.isNaN(value);
    }
}
