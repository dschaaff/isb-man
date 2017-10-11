package net.javalib.isb.man.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.annotation.Timed;
import net.javalib.isb.man.pojo.ServerInstance;
import net.javalib.isb.man.pojo.metrics.Metric;
import net.javalib.isb.man.utils.ConcurrentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MetricsServiceImpl implements MetricsService {
    private static final Logger logger = LoggerFactory.getLogger(MetricsServiceImpl.class);
    private ServersService serversService;
    private RestTemplate restTemplate;

    @Autowired
    public MetricsServiceImpl(ServersService serversService,
                              RestTemplate restTemplate) {
        this.serversService = serversService;
        this.restTemplate = restTemplate;
        registerMetrics();
    }

    @Override
    @Timed
    @Cacheable("metrics")
    public Collection<Metric> getMetrics() {
        return update();
    }

    @Override
    @Timed
    public Collection<Metric> update() {
        logger.info("updating metrics...");
        Collection<ServerInstance> servers = serversService.getServers();
        List<Metric> metrics = Collections.synchronizedList(new ArrayList<>());
        ConcurrentUtil.processItems(servers, servers.size(), new ConcurrentUtil.ConcurrentProcessor<ServerInstance>() {
            @Override
            public void process(ServerInstance server) {
                Collection<Metric> serverMetrics = getMetrics(server);
                if (serverMetrics != null) {
                    metrics.addAll(serverMetrics);
                }
            }
        });
        logger.info("found {} metrics",metrics.size());
        return metrics;
    }

    private Collection<Metric> getMetrics(ServerInstance server) {
        try {
            Metric[] metrics = restTemplate.getForObject(metricsUrl(server), Metric[].class);
            Collection<Metric> retVal = new ArrayList<>();
            for (Metric metric : metrics) {
                metric.getHosts().add(server.getHostname());
                metric.setEnv(server.getEnvironment());
                metric.setServiceType(server.getServiceType());
                metric.setHostname(server.getHostname());
                if ("SimpleGauge".equals(metric.getType())) {
                    metric.setType(Metric.GAUGE);
                }
                if (metric.is(Metric.TIMER) && metric.getRegistry().startsWith("jdbc.")) {
                    metric.setName(metric.getName().substring(metric.getName().indexOf('.') + 1).toLowerCase());
                }
                retVal.add(metric);
            }
            return retVal;
        } catch (Exception e) {
            logger.error("Unable to get metrics for {} : {}",server.getHostname(),e.getMessage(),e);
            return null;
        }
    }

    private String metricsUrl(ServerInstance server) {
        return "http://"+server.getAddress()+":"+server.getManagePort()+"/manage/isb_metrics";
    }

    private void registerMetrics() {
        SharedMetricRegistries.getOrCreate("app").register("metrics.count", new Gauge<Integer>() {
            @Override
            public Integer getValue() {
                Collection<Metric> metrics = getMetrics();
                if (metrics != null) {
                    return metrics.size();
                }
                return 0;
            }
        });

    }
}
