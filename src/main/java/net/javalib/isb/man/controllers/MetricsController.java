package net.javalib.isb.man.controllers;

import java.util.Collection;
import net.javalib.isb.man.pojo.EnvFilter;
import net.javalib.isb.man.pojo.metrics.Metric;
import net.javalib.isb.man.pojo.metrics.Metrics;
import net.javalib.isb.man.services.MetricsService;
import net.javalib.isb.man.utils.FilterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MetricsController {
    @Autowired
    private MetricsService metricsService;

    @RequestMapping(value = "/metrics", method = RequestMethod.GET)
    public Metrics metrics(@RequestParam(required = false) String env,
                           @RequestParam(required = false) String service,
                           @RequestParam(required = false) String hostname) {
        EnvFilter filter = new EnvFilter(env, service, hostname);
        Collection<Metric> metrics = metricsService.getMetrics();
        metrics = FilterUtils.filter(metrics, filter);
        Metrics retVal = Metrics.fromMetrics(metrics).filterByLevel(filter);
        return retVal;
    }
}

