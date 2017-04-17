package com.genband.util.dummy.gateway.service.listener;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller;

import com.genband.util.broker.model.Message;
import com.genband.util.broker.model.OperationReceipt;
import com.genband.util.broker.rabbitmq.annotation.RabbitmqMessageController;
import com.genband.util.broker.rabbitmq.annotation.RabbitmqMessageHandler;
import com.genband.util.broker.rabbitmq.annotation.RabbitmqMessageHandlerConstructor;
import com.genband.util.dummy.gateway.service.DummyRabbitmqService;
import com.genband.util.log.slf4j.GbLogger;
import com.genband.util.log.slf4j.GbLoggerFactory;

@RabbitmqMessageController
@Controller
public class RabbitmqController {
    private static GbLogger log = GbLoggerFactory.getGbLogger(RabbitmqController.class.getName());

    @Autowired
    private DummyRabbitmqService dummyService;

    @RabbitmqMessageHandlerConstructor(instanceType = "singleton", springbootEnabled = true, invokeProp = false)
    public RabbitmqController() {
        log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        log.info("%%%%%%%%%%% Default Constructor %%%%%%%%");
        log.info("%%%%%%% Do your preparation here. %%%%%%");
        log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    }

    @RabbitmqMessageHandler(listenChannel = "self")
    public OperationReceipt handleMessage(Message message) {
        OperationReceipt receipt = new OperationReceipt();
        this.dummyService.handleRoutedMessage(message);
        return receipt;
    }

    @RabbitmqMessageHandler(listenChannel = "unallocated")
    public OperationReceipt handleNewCommingMessage(Message message) {
        OperationReceipt receipt = new OperationReceipt();
        this.dummyService.handleUnroutedMessage(message);
        return receipt;
    }
}
