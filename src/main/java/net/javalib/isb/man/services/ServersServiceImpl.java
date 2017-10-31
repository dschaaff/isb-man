package net.javalib.isb.man.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.annotation.Timed;
import net.javalib.isb.man.pojo.ServerInstance;
import net.javalib.isb.man.utils.ConcurrentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ServersServiceImpl implements ServersService {
    private static final Logger logger = LoggerFactory.getLogger(ServersServiceImpl.class);
    private Collection<ServerInstancesRegistry> serverRegistries;

    @Autowired
    public ServersServiceImpl(Collection<ServerInstancesRegistry> serverRegistries) {
        this.serverRegistries = serverRegistries;
    }

    @Override
    @Timed
    @Cacheable("servers")
    public Collection<ServerInstance> getServers() {
        return update();
    }

    @Override
    @Timed
    @CachePut("servers")
    public Collection<ServerInstance> update() {
        List<ServerInstance> instances = Collections.synchronizedList(new ArrayList<ServerInstance>());
        ConcurrentUtil.processItems(serverRegistries, serverRegistries.size(), new ConcurrentUtil.ConcurrentProcessor<ServerInstancesRegistry>() {
            @Override
            public void process(ServerInstancesRegistry registry) {
                instances.addAll(registry.getServerInstances());
            }
        });
        logger.info("found {} servers",instances.size());
        return instances;
    }

    private void registerMetrics() {
        SharedMetricRegistries.getOrCreate("app").register("servers.count", new Gauge<Integer>() {
            @Override
            public Integer getValue() {
                Collection<ServerInstance> instances = getServers();
                if (instances != null) {
                    return instances.size();
                }
                return 0;
            }
        });
    }

}
