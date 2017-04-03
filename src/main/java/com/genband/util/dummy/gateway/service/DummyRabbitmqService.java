package com.genband.util.dummy.gateway.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.genband.util.broker.BrokerMessagingService;
import com.genband.util.broker.BrokerType;
import com.genband.util.broker.MessagingService;
import com.genband.util.broker.model.Message;
import com.genband.util.broker.util.MessageUtils;
import com.genband.util.log.slf4j.GbLogger;
import com.genband.util.log.slf4j.GbLoggerFactory;

@Service
public class DummyRabbitmqService {
    private static GbLogger log = GbLoggerFactory.getGbLogger(DummyRabbitmqService.class.getName());
    private MessagingService svc;
    private MessageUtils util;

    @PostConstruct
    private void construct() {
        log.info("Dummy Service Loaded...");
        svc = BrokerMessagingService.getService(BrokerType.RABBITMQ);
        svc.startConsumeMessaging();
        util = MessageUtils.getInstance();

    }

    public void handleUnroutedMessage(Message message) {
        log.info("Now will serving new subscriber: " + message.getMessageParams().getSubscriber());
        log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        log.info("Unrouted Message: \n " + message.getMessageBody());
        log.info("Type: \n " + message.getMessageParams().getType());
        log.info("Transcation-ID: \n " + message.getMessageParams().getTransactionId());
        log.info("Message-ID: \n " + message.getMessageParams().getMessageId());
        log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        svc.bindRoutingKey(message.getMessageParams().getSubscriber());
    }

    public void handleRoutedMessage(Message message) {
        log.info("Routed subscriber: " + message.getMessageParams().getSubscriber());
        log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        log.info("Message: \n " + message.getMessageBody());
        log.info("Type: \n " + message.getMessageParams().getType());
        log.info("Transcation-ID: \n " + message.getMessageParams().getTransactionId());
        log.info("Message-ID: \n " + message.getMessageParams().getMessageId());
        log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    }

    public Message synchronziedSendMsg(String message, String username, String serviceName) {
        // TODO Auto-generated method stub
        Message msg = util.buildMessage(message, username, serviceName);
        Message returnMsg = null;

        try {
            returnMsg = svc.sendSynchronizedRequest(msg);
        } catch (Exception e) {
            log.error("The error stuff");
            e.printStackTrace();
        }

        return returnMsg;
    }
}
