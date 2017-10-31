package net.javalib.isb.man.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ConfigController {
    private Map<String,Object> config = new HashMap<>();

    @Autowired
    public ConfigController(
            @Value("${server.instances.registry.urls:#{null}}") String registryUrls,
            @Value("${influxdb.url:#{null}}") String influxDbUrl,
            @Value("${grafana.url:#{null}}") String grafanaUrl,
            @Value("${hawtio.url:#{null}}") String hawtioUrl) {
        config.put("server.instances.registry.urls",registryUrls);
        config.put("influxdb.url",influxDbUrl);
        config.put("grafana.url",grafanaUrl);
        config.put("hawtio.url",hawtioUrl);
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public Map<String,Object> config() {
        return config;
    }

}
