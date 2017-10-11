package net.javalib.isb.man.controllers;

import java.util.Collection;
import net.javalib.isb.man.pojo.ServerInstance;
import net.javalib.isb.man.pojo.metrics.Metric;
import net.javalib.isb.man.services.MetricsService;
import net.javalib.isb.man.services.ServersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private ServersService serversService;
    @Autowired
    private MetricsService metricsService;

    @RequestMapping(value = "/test/servers", method = RequestMethod.GET)
    public Collection<ServerInstance> servers() {
        return serversService.getServers();
    }

    @RequestMapping(value = "/test/metrics", method = RequestMethod.GET)
    public Collection<Metric> metrics() { return metricsService.getMetrics(); }

}
