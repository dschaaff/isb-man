package net.javalib.isb.man.utils;

import net.javalib.isb.man.pojo.metrics.*;
import org.apache.commons.lang3.SerializationUtils;

public class CloneUtils {

    public static Metric clone(Metric metric) {
        if (Metric.TIMER.equals(metric.getType())) {
            return cloneTimer((Timer) metric);
        }
        if (Metric.COUNTER.equals(metric.getType())) {
            return cloneCounter((Counter) metric);
        }
        if (Metric.GAUGE.equals(metric.getType())) {
            return cloneGauge((Gauge) metric);
        }
        if (Metric.METER.equals(metric.getType())) {
            return cloneMeter((Meter) metric);
        }
        return null;
    }

    public static Timer cloneTimer(Timer timer) {
        Timer clone = SerializationUtils.clone(timer);
        clone.setMetric(new Timer.MetricValue());
        return clone;
    }

    public static Counter cloneCounter(Counter counter) {
        Counter clone = SerializationUtils.clone(counter);
        clone.getMetric().setCount(0);
        return clone;
    }

    public static Meter cloneMeter(Meter meter) {
        Meter clone = SerializationUtils.clone(meter);
        clone.setMetric(new Meter.MetricValue());
        return clone;
    }

    public static Gauge cloneGauge(Gauge gauge) {
        Gauge clone = SerializationUtils.clone(gauge);
        clone.getMetric().setValue(null);
        return clone;
    }
}

