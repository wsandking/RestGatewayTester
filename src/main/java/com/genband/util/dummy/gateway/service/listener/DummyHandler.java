package com.genband.util.dummy.gateway.service.listener;

import org.springframework.beans.factory.annotation.Autowired;

import com.genband.util.broker.model.Message;
import com.genband.util.broker.model.OperationReceipt;
import com.genband.util.broker.rabbitmq.annotation.RabbitmqMessageController;
import com.genband.util.broker.rabbitmq.annotation.RabbitmqMessageHandler;
import com.genband.util.broker.rabbitmq.annotation.RabbitmqMessageHandlerConstructor;
import com.genband.util.dummy.gateway.service.DummyRabbitmqService;
import com.genband.util.log.slf4j.GbLogger;
import com.genband.util.log.slf4j.GbLoggerFactory;

@RabbitmqMessageController(state = "work")
public class DummyHandler {
    private static GbLogger log = GbLoggerFactory.getGbLogger(DummyHandler.class.getName());

    @Autowired
    private DummyRabbitmqService dummyService;

    @RabbitmqMessageHandlerConstructor(instanceType = "singleton", springbootEnabled = false, invokeProp = false)
    public DummyHandler() {
        log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        log.info("%%%%%%%%%%% Default Constructor %%%%%%%%");
        log.info("%%%%%%% Do your preparation here. %%%%%%");
        log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

    }

    // @RabbitmqMessageHandlerConstructor(instanceType = "singleton", springbootEnabled = false, invokeProp = true)
    // public DummyHandler(ConfigProperties prop) {
    // log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    // log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    // log.info("%%%%%%%%%%% Default Constructor %%%%%%%%");
    // log.info("" + prop.getRabbitmqHost());
    // log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    // log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    // }

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
