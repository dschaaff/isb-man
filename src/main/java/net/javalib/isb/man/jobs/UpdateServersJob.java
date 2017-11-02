package net.javalib.isb.man.jobs;

import net.javalib.isb.man.services.ServersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UpdateServersJob {
    private ServersService serversService;

    @Autowired
    public UpdateServersJob(ServersService serversService) {
        this.serversService = serversService;
    }

    @Scheduled(cron="${update_servers_job.cron}")
    public void run() {
        serversService.update();
    }

}
