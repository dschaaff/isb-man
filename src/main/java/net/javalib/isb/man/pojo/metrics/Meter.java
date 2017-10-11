package net.javalib.isb.man.pojo.metrics;

import java.io.Serializable;

public class Meter extends Metric implements Serializable {
    private MetricValue metric = new MetricValue();

    public Meter() { this.type = METER; }

    public MetricValue getMetric() {
        return metric;
    }

    public void setMetric(MetricValue metric) {
        this.metric = metric;
    }

    public static class MetricValue implements Serializable {
        private long count;
        private double meanRate;
        private double oneMinuteRate;
        private double fifteenMinuteRate;
        private double fiveMinuteRate;

        public double getMeanRate() {
            return meanRate;
        }

        public void setMeanRate(double meanRate) {
            this.meanRate = meanRate;
        }

        public double getOneMinuteRate() {
            return oneMinuteRate;
        }

        public void setOneMinuteRate(double oneMinuteRate) {
            this.oneMinuteRate = oneMinuteRate;
        }

        public double getFifteenMinuteRate() {
            return fifteenMinuteRate;
        }

        public void setFifteenMinuteRate(double fifteenMinuteRate) {
            this.fifteenMinuteRate = fifteenMinuteRate;
        }

        public double getFiveMinuteRate() {
            return fiveMinuteRate;
        }

        public void setFiveMinuteRate(double fiveMinuteRate) {
            this.fiveMinuteRate = fiveMinuteRate;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }
    }

}
