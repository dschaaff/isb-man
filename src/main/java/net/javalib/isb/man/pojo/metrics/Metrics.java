package net.javalib.isb.man.pojo.metrics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.javalib.isb.man.pojo.EnvFilter;
import net.javalib.isb.man.utils.CloneUtils;
import net.javalib.isb.man.utils.FilterUtils;
import net.javalib.isb.man.utils.MetricsUtils;

public class Metrics {
    private Collection<Timer> timers = new ArrayList<Timer>();
    private Collection<Counter> counters = new ArrayList<Counter>();
    private Collection<Gauge> gauges = new ArrayList<Gauge>();
    private Collection<Meter> meters = new ArrayList<Meter>();

    private Map<String, Metric> tempLookup = new HashMap<String, Metric>();

    private String tempLookupKey(String environment, String serviceType, Metric metric) {
        return environment + "::" + serviceType + "::" + metric.getType() + "::" + metric.getRegistry() + "::" + metric.getName();
    }

    public Metrics filterByLevel(EnvFilter filter) {
        FilterUtils.filterByLevel(timers, filter);
        FilterUtils.filterByLevel(counters, filter);
        FilterUtils.filterByLevel(gauges, filter);
        FilterUtils.filterByLevel(meters,filter);
        return this;
    }

    public static Metrics fromMetrics(Collection<Metric> metrics) {
        Metrics retVal = new Metrics();
        for (Metric metric : metrics) {
            retVal.process(metric);
        }
        return retVal;
    }

    public int count() {
        return timers.size()+counters.size()+gauges.size()+meters.size();
    }

    private void process(Metric metric) {
        add(metric);
        updateEnvTypeMetric(metric);
        updateEnvMetric(metric);
    }

    public void add(Metric metric) {
        if (Metric.TIMER.equals(metric.getType())) {
            timers.add((Timer) metric);
        } else if (Metric.COUNTER.equals(metric.getType())) {
            counters.add((Counter) metric);
        } else if (Metric.GAUGE.equals(metric.getType())) {
            gauges.add((Gauge) metric);
        } else if (Metric.METER.equals(metric.getType())) {
            meters.add((Meter) metric);
        }
    }

    void updateEnvTypeMetric(Metric metric) {
        String tempLookupKey = tempLookupKey(metric.getEnv(), metric.getServiceType(), metric);
        Metric envTypeMetric = tempLookup.get(tempLookupKey);
        if (envTypeMetric == null) {
            envTypeMetric = CloneUtils.clone(metric);
            envTypeMetric.getHosts().clear();
            envTypeMetric.setHostname(null);
            tempLookup.put(tempLookupKey, envTypeMetric);
            add(envTypeMetric);
        }
        MetricsUtils.merge(metric, envTypeMetric);
    }

    void updateEnvMetric(Metric metric) {
        String tempLookupKey = tempLookupKey(metric.getEnv(), null, metric);
        Metric envMetric = tempLookup.get(tempLookupKey);
        if (envMetric == null) {
            envMetric = CloneUtils.clone(metric);
            envMetric.getHosts().clear();
            envMetric.setHostname(null);
            envMetric.setServiceType(null);
            tempLookup.put(tempLookupKey, envMetric);
            add(envMetric);
        }
        MetricsUtils.merge(metric, envMetric);
    }

    // -----------
    // GET/SET
    // -----------

    public Collection<Timer> getTimers() {
        return timers;
    }

    public void setTimers(Collection<Timer> timers) {
        this.timers = timers;
    }

    public Collection<Counter> getCounters() {
        return counters;
    }

    public void setCounters(Collection<Counter> counters) {
        this.counters = counters;
    }

    public Collection<Meter> getMeters() {
        return meters;
    }

    public void setMeters(Collection<Meter> meters) {
        this.meters = meters;
    }

    public Collection<Gauge> getGauges() {
        return gauges;
    }

    public void setGauges(Collection<Gauge> gauges) {
        this.gauges = gauges;
    }


}
