package net.javalib.isb.man.jobs;

import java.util.Collection;
import net.javalib.isb.man.pojo.metrics.Metric;
import net.javalib.isb.man.services.MetricsExporter;
import net.javalib.isb.man.services.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MetricsExportJob {
    private MetricsService metricsService;
    private Collection<MetricsExporter> exporters;


    @Autowired
    public MetricsExportJob(MetricsService metricsService, Collection<MetricsExporter> exporters) {
        this.metricsService = metricsService;
        this.exporters = exporters;
    }


    @Scheduled(cron = "${export_job.cron}")
    public void run() {
        Collection<Metric> metrics = metricsService.update();
        for (MetricsExporter exporter : exporters) {
            exporter.exportMetrics(metrics);
        }
    }

}
