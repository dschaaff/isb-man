package net.javalib.isb.man.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.javalib.isb.man.pojo.metrics.*;

public class MetricsUtils {

    public static void merge(Metrics src, Metrics dest) {
        merge(src.getTimers(),dest.getTimers());
        merge(src.getCounters(),dest.getCounters());
        merge(src.getGauges(),dest.getGauges());
        merge(src.getMeters(),dest.getMeters());
    }

    public static <T extends Metric> void merge(Collection<T> src, Collection<T> dest) {
        for (Metric srcMetric : src) {
            Metric destMetric = getMetric(srcMetric.getName(),dest);
            if (destMetric == null) {
                dest.add((T)srcMetric);
            } else {
                merge(srcMetric,destMetric);
            }
        }
    }

    public static Metric getMetric(String name, Collection<? extends Metric> metrics) {
        for (Metric metric : metrics) {
            if (name.equals(metric.getName())) {
                return metric;
            }
        }
        return null;
    }

    public static void merge(Metric src, Metric dest) {
        if (Metric.TIMER.equals(src.getType())) {
            mergeTimers((Timer) src, (Timer) dest);
        } else if (Metric.COUNTER.equals(src.getType())) {
            mergeCounters((Counter) src, (Counter) dest);
        } else if (Metric.GAUGE.equals(src.getType())) {
            mergeGauge((Gauge) src, (Gauge) dest);
        } else if (Metric.METER.equals(src.getType())) {
            mergeMeters((Meter) src, (Meter) dest);
        }
    }

    public static void mergeTimers(Timer src, Timer dest) {
        Timer.MetricValue srcValue = src.getMetric();
        Timer.MetricValue destValue = dest.getMetric();
        long srcCount = srcValue.getCount();
        if (srcCount == 0) {
            if (src.getHostname() != null) {
                dest.getHosts().add(src.getHostname());
            }
            return;
        }
        long destCount = destValue.getCount();
        long totalCount = srcCount + destCount;
        Timer.Snapshot srcSnap = srcValue.getSnapshot();
        Timer.Snapshot destSnap = destValue.getSnapshot();

        destSnap.set75thPercentile((destCount * destSnap.get75thPercentile() + srcCount * srcSnap.get75thPercentile()) / totalCount);
        destSnap.set95thPercentile((destCount * destSnap.get95thPercentile() + srcCount * srcSnap.get95thPercentile()) / totalCount);
        destSnap.set98thPercentile((destCount * destSnap.get98thPercentile() + srcCount * srcSnap.get98thPercentile()) / totalCount);
        destSnap.set99thPercentile((destCount * destSnap.get99thPercentile() + srcCount * srcSnap.get99thPercentile()) / totalCount);
        destSnap.set999thPercentile((destCount * destSnap.get999thPercentile() + srcCount * srcSnap.get999thPercentile()) / totalCount);
        if (destCount > 0) {
            destSnap.setMax(Math.max(destSnap.getMax(), srcSnap.getMax()));
            destSnap.setMin(Math.min(destSnap.getMin(), srcSnap.getMin()));
        }
        else {
            destSnap.setMax(srcSnap.getMax());
            destSnap.setMin(srcSnap.getMin());
        }
        destSnap.setMean((destCount * destSnap.getMean() + srcCount * srcSnap.getMean()) / totalCount);
        destSnap.setMedian((destCount * destSnap.getMedian() + srcCount * srcSnap.getMedian()) / totalCount);
        destSnap.setStdDev((destCount * destSnap.getStdDev() + srcCount * srcSnap.getStdDev()) / totalCount);

        destValue.setOneMinuteRate(destValue.getOneMinuteRate() + srcValue.getOneMinuteRate());
        destValue.setFiveMinuteRate(destValue.getFiveMinuteRate() + srcValue.getFiveMinuteRate());
        destValue.setFifteenMinuteRate(destValue.getFifteenMinuteRate() + srcValue.getFifteenMinuteRate());
        destValue.setMeanRate(destValue.getMeanRate() + srcValue.getMeanRate());
        destValue.setCount(totalCount);
        if (src.getHostname() != null) {
            dest.getHosts().add(src.getHostname());
        }
    }

    public static void mergeMeters(Meter src, Meter dest) {
        Meter.MetricValue srcValue = src.getMetric();
        Meter.MetricValue destValue = dest.getMetric();
        long srcCount = srcValue.getCount();
        if (srcCount == 0) {
            if (src.getHostname() != null) {
                dest.getHosts().add(src.getHostname());
            }
            return;
        }
        long destCount = destValue.getCount();
        long totalCount = srcCount + destCount;

        destValue.setOneMinuteRate(destValue.getOneMinuteRate() + srcValue.getOneMinuteRate());
        destValue.setFiveMinuteRate(destValue.getFiveMinuteRate() + srcValue.getFiveMinuteRate());
        destValue.setFifteenMinuteRate(destValue.getFifteenMinuteRate() + srcValue.getFifteenMinuteRate());
        destValue.setMeanRate(destValue.getMeanRate() + srcValue.getMeanRate());
        destValue.setCount(totalCount);
        if (src.getHostname() != null) {
            dest.getHosts().add(src.getHostname());
        }

    }

    public static void aggregateTimers(Timer src, Timer dest) {
        Timer.MetricValue srcValue = src.getMetric();
        Timer.MetricValue destValue = dest.getMetric();

        long srcCount = srcValue.getCount();
        long destCount = destValue.getCount();
        long totalCount = srcCount + destCount;

        destValue.setCount(srcCount + destCount);
        destValue.setMeanRate(srcValue.getMeanRate() + destValue.getMeanRate());
        destValue.setOneMinuteRate(srcValue.getOneMinuteRate() + destValue.getOneMinuteRate());
        destValue.setFifteenMinuteRate(srcValue.getFifteenMinuteRate() + destValue.getFifteenMinuteRate());
        destValue.setFiveMinuteRate(srcValue.getFiveMinuteRate() + destValue.getFiveMinuteRate());

        Timer.Snapshot srcSnap = srcValue.getSnapshot();
        Timer.Snapshot destSnap = destValue.getSnapshot();

        destSnap.setMin(Math.min(srcSnap.getMin(), destSnap.getMin()));
        destSnap.setMax(Math.max(srcSnap.getMax(), destSnap.getMax()));
        destSnap.setMin(Math.min(srcSnap.getMin(), destSnap.getMin()));
        destSnap.setMin(Math.min(srcSnap.getMin(), destSnap.getMin()));
        destSnap.setMean((destCount * destSnap.getMean() + srcCount * srcSnap.getMean()) / totalCount);
        destSnap.setMedian((destCount * destSnap.getMedian() + srcCount * srcSnap.getMedian()) / totalCount);
        destSnap.setStdDev((destCount * destSnap.getStdDev() + srcCount * srcSnap.getStdDev()) / totalCount);

        destSnap.set75thPercentile((destCount * destSnap.get75thPercentile() + srcCount * srcSnap.get75thPercentile()) / totalCount);
        destSnap.set95thPercentile((destCount * destSnap.get95thPercentile() + srcCount * srcSnap.get95thPercentile()) / totalCount);
        destSnap.set98thPercentile((destCount * destSnap.get98thPercentile() + srcCount * srcSnap.get98thPercentile()) / totalCount);
        destSnap.set99thPercentile((destCount * destSnap.get99thPercentile() + srcCount * srcSnap.get99thPercentile()) / totalCount);
        destSnap.set999thPercentile((destCount * destSnap.get999thPercentile() + srcCount * srcSnap.get999thPercentile()) / totalCount);

        Set<String> hosts = new HashSet<String>(dest.getHosts());
        hosts.addAll(src.getHosts());
        dest.setHosts(hosts);

    }

    public static void mergeCounters(Counter src, Counter dest) {
        if (src.getHostname() != null) {
            dest.getHosts().add(src.getHostname());
        }
        dest.getMetric().setCount(dest.getMetric().getCount() + src.getMetric().getCount());
    }

    public static void mergeGauge(Gauge src, Gauge dest) {
        if (src.getHostname() != null) {
            dest.getHosts().add(src.getHostname());
        }
        Object srcValue = src.getMetric().getValue();
        Object destValue = dest.getMetric().getValue();
        boolean isSrcNumeric = srcValue != null && srcValue instanceof Number;
        boolean isDestNumeric = destValue != null && destValue instanceof Number;
        if (destValue == null) {
            if (isSrcNumeric) {
                isDestNumeric = true;
                destValue = new Double(0);
            }
            else {
                destValue = new HashSet<Object>();
            }
        }
        if (srcValue == null) {
            return;
        }
        if (!isDestNumeric) {
            if (!(destValue instanceof Set)) {
                Set<Object> set = new HashSet<Object>();
                set.add(destValue);
                set.add(srcValue);
                destValue = set;
            }
            else {
                ((Set<Object>) destValue).add(srcValue);
            }
        }
        else if (isSrcNumeric) {
            double srcDouble = ((Number) srcValue).doubleValue();
            double destDouble = ((Number) destValue).doubleValue();
            int multiplier = dest.getHosts().size();
            destValue = (multiplier * destDouble + srcDouble) / (multiplier + 1);
        }
        dest.getMetric().setValue(destValue);

    }

}
