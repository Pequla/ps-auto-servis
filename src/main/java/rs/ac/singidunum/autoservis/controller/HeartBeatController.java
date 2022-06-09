package rs.ac.singidunum.autoservis.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/signal")
@CrossOrigin
public class HeartBeatController {

    @GetMapping
    public String sendHeartbeat() {
        return "OK";
    }
}
