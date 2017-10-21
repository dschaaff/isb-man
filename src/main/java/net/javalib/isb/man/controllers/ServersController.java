package net.javalib.isb.man.controllers;

import java.util.Collection;
import com.fasterxml.jackson.databind.JsonNode;
import net.javalib.isb.man.pojo.ServerInstance;
import net.javalib.isb.man.services.ServersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class ServersController {
    @Autowired
    private ServersService serversService;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/servers", method = RequestMethod.GET)
    public Collection<ServerInstance> servers() {
        return serversService.getServers();
    }

    @RequestMapping(value = "/servers/{address}/{port}/env", method = RequestMethod.GET)
    public JsonNode getEnv(@PathVariable String address, @PathVariable int port) {
        return restTemplate.getForObject("http://"+address+":"+port+"/manage/env",JsonNode.class);
    }

    @RequestMapping(value = "/servers/{address}/{port}/trace", method = RequestMethod.GET)
    public JsonNode getTrace(@PathVariable String address, @PathVariable int port) {
        return restTemplate.getForObject("http://"+address+":"+port+"/manage/trace",JsonNode.class);
    }

}
