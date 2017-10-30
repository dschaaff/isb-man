package net.javalib.isb.man.controllers;


import com.codahale.metrics.annotation.Timed;
import net.javalib.isb.utils.PingStatusHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    private static final Logger LOG = LoggerFactory.getLogger(PingController.class);

    @Autowired
    private PingStatusHelper pingStatusHelper;

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    @Timed
    public ResponseEntity<String> ping() {
        return pingStatusHelper.ping();
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    @Timed
    public ResponseEntity<String> status() {
        return pingStatusHelper.status();
    }

}
