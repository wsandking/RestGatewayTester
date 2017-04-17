package com.genband.util.dummy.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.genband.util.broker.BrokerMessagingService;
import com.genband.util.broker.BrokerType;
import com.genband.util.broker.MessagingService;
import com.genband.util.log.slf4j.GbLogger;
import com.genband.util.log.slf4j.GbLoggerFactory;

@SpringBootApplication
@ComponentScan({ "com.genband.util.dummy.gateway", "com.genband.controlInterface" })
public class DummyApplication {

    private static GbLogger log = GbLoggerFactory.getGbLogger(DummyApplication.class.getName());

    public static void main(String args[]) {
        log.info("The beginning of Dummy Gate.");

        ApplicationContext ctx = SpringApplication.run(DummyApplication.class, args);
        
        /**
         * In spring boot environment, this is necessary
         */
        MessagingService svc = BrokerMessagingService.getService(BrokerType.RABBITMQ);
        svc.setPackageToScan("com.genband.util.dummy.gateway.service.listener");
        svc.setApplicationContext(ctx);
        svc.startConsumeMessaging();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                if (ctx instanceof ConfigurableApplicationContext) {
                    log.info("Application closed");
                    ((ConfigurableApplicationContext) ctx).close();
                }
            }
        });
    }

}
