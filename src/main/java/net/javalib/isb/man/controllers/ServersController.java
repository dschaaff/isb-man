package net.javalib.isb.man.controllers;

import java.util.Collection;
import net.javalib.isb.man.pojo.ServerInstance;
import net.javalib.isb.man.services.ServersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ServersController {
    @Autowired
    private ServersService serversService;

    @RequestMapping(value = "/servers", method = RequestMethod.GET)
    public Collection<ServerInstance> servers() {
        return serversService.getServers();
    }

}
