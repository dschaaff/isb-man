package net.javalib.isb.man.pojo.metrics;

import java.io.Serializable;

public class Counter extends Metric implements Serializable {
    private MetricValue metric = new MetricValue();

    public Counter() {
        this.type = COUNTER;
    }

    public MetricValue getMetric() {
        return metric;
    }

    public void setMetric(MetricValue metric) {
        this.metric = metric;
    }

    public static class MetricValue implements Serializable {
        private long count;

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

    }

}
