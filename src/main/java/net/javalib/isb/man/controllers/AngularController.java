package net.javalib.isb.man.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AngularController {

    @RequestMapping(value = "/ui/**", method = RequestMethod.GET)
    public String ui() {
        return "forward:/index.html";
    }

}
