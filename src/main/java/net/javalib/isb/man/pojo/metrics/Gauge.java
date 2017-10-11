package net.javalib.isb.man.pojo.metrics;

import java.io.Serializable;

public class Gauge extends Metric implements Serializable {
    private MetricValue metric;

    public Gauge() {
        this.type = GAUGE;
    }

    public MetricValue getMetric() {
        return metric;
    }

    public void setMetric(MetricValue metric) {
        this.metric = metric;
    }

    public class MetricValue implements Serializable {
        private Object value;

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }

}
