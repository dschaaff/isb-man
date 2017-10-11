package net.javalib.isb.man.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import com.codahale.metrics.annotation.Timed;
import net.javalib.isb.man.pojo.ServerInstance;
import net.javalib.isb.man.utils.ConcurrentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@ConditionalOnProperty(value="server.instances.registry.urls", matchIfMissing = false)
public class ServerInstancesRegistryURL implements ServerInstancesRegistry {
    private static Logger logger = LoggerFactory.getLogger(ServerInstancesRegistryURL.class);
    private List<String> urls = new ArrayList<>();
    private RestTemplate restTemplate;

    @Autowired
    public ServerInstancesRegistryURL(@Value("${server.instances.registry.urls}") String urls, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        for (String url : urls.split(",")) {
            this.urls.add(url);
            logger.info("registered : {}",url);
        }
    }

    @Override
    @Timed
    public Collection<ServerInstance> getServerInstances() {
        Collection<ServerInstance> instances = Collections.synchronizedCollection(new ArrayList<>());
        ConcurrentUtil.processItems(urls, urls.size(), new ConcurrentUtil.ConcurrentProcessor<String>() {
            @Override
            public void process(String url) {
                processUrl(url,instances);
            }
        });
        return instances;
    }

    private void processUrl(String url, Collection<ServerInstance> instances) {
        try {
            ServerInstance[] result = restTemplate.getForObject(url, ServerInstance[].class);
            for (ServerInstance instance : result) {
                instances.add(instance);
            }
        } catch (Exception e) {
            logger.error("Unable to retrieve servers. {}",e.getMessage());
        }
    }

}
