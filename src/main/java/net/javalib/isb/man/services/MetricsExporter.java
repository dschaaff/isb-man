package net.javalib.isb.man.services;

import java.util.Collection;
import net.javalib.isb.man.pojo.metrics.Metric;

public interface MetricsExporter {

    int exportMetrics(Collection<Metric> metrics);

}
