package rs.ac.singidunum.autoservis.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import rs.ac.singidunum.autoservis.domain.Customer;
import rs.ac.singidunum.autoservis.service.CustomerService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeartBeatControllerTest {

    // UNIT TEST

    @Test
    void sendHeartbeat() {
       HeartBeatController controller = new HeartBeatController();
        String response = controller.sendHeartbeat();
        Assertions.assertEquals("OK", response);
    }
}