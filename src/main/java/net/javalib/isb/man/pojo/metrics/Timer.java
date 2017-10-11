package net.javalib.isb.man.pojo.metrics;


import java.io.Serializable;

public class Timer extends Metric implements Serializable {
    private MetricValue metric = new MetricValue();

    public Timer() {
        this.type = TIMER;
    }

    public MetricValue getMetric() {
        return metric;
    }

    public void setMetric(MetricValue metric) {
        this.metric = metric;
    }

    public static class MetricValue implements Serializable {
        private Snapshot snapshot = new Snapshot();
        private long count;
        private double meanRate;
        private double oneMinuteRate;
        private double fifteenMinuteRate;
        private double fiveMinuteRate;

        public Snapshot getSnapshot() {
            return snapshot;
        }

        public void setSnapshot(Snapshot snapshot) {
            this.snapshot = snapshot;
        }

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

    public static class Snapshot implements Serializable {
        // private long[] values;
        private long min;
        private double stdDev;
        private double mean;
        private long max;
        private long _98thPercentile;
        private long _99thPercentile;
        private long _95thPercentile;
        private long median;
        private long _75thPercentile;
        private long _999thPercentile;

// public long[] getValues() {
// return values;
// }
//
// public void setValues(long[] values) {
// this.values = values;
// }

        public long getMin() {
            return min;
        }

        public void setMin(long min) {
            this.min = min;
        }

        public double getStdDev() {
            return stdDev;
        }

        public void setStdDev(double stdDev) {
            this.stdDev = stdDev;
        }

        public double getMean() {
            return mean;
        }

        public void setMean(double mean) {
            this.mean = mean;
        }

        public long getMax() {
            return max;
        }

        public void setMax(long max) {
            this.max = max;
        }

        public long get98thPercentile() {
            return _98thPercentile;
        }

        public void set98thPercentile(long _98thPercentile) {
            this._98thPercentile = _98thPercentile;
        }

        public long get99thPercentile() {
            return _99thPercentile;
        }

        public void set99thPercentile(long _99thPercentile) {
            this._99thPercentile = _99thPercentile;
        }

        public long get95thPercentile() {
            return _95thPercentile;
        }

        public void set95thPercentile(long _95thPercentile) {
            this._95thPercentile = _95thPercentile;
        }

        public long getMedian() {
            return median;
        }

        public void setMedian(long median) {
            this.median = median;
        }

        public long get75thPercentile() {
            return _75thPercentile;
        }

        public void set75thPercentile(long _75thPercentile) {
            this._75thPercentile = _75thPercentile;
        }

        public long get999thPercentile() {
            return _999thPercentile;
        }

        public void set999thPercentile(long _999thPercentile) {
            this._999thPercentile = _999thPercentile;
        }

    }

}
