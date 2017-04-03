package com.genband.util.dummy.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.genband.util.broker.model.Message;
import com.genband.util.dummy.gateway.controller.model.RequestModel;
import com.genband.util.dummy.gateway.service.DummyRabbitmqService;
import com.genband.util.log.slf4j.GbLogger;
import com.genband.util.log.slf4j.GbLoggerFactory;

@RestController
@RequestMapping("/rest/version/1/user/")
public class RestGatewayController {

    private static GbLogger log = GbLoggerFactory.getGbLogger(RestGatewayController.class.getName());

    @Autowired
    private DummyRabbitmqService rabbitSvc;

    @RequestMapping(value = "{username}", method = RequestMethod.POST)
    public ResponseEntity<Message> Login(@PathVariable("username") String username, @RequestBody RequestModel model) {

        log.info(String.format("Message: %s to svc %s ----- for user: %s ", model.getMessage(), model.getServiceName(),
                username));
        ResponseEntity<Message> response;
        try {
            Message returnMessage = rabbitSvc.synchronziedSendMsg(model.getMessage(), username, model.getServiceName());
            response = new ResponseEntity<Message>(returnMessage, HttpStatus.CREATED);
        } catch (Exception e) {
            Message responseBody = null;
            response = new ResponseEntity<Message>(responseBody, HttpStatus.UNAUTHORIZED);
            e.printStackTrace();
        }
        return response;
    }
}
