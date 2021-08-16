package com.mdrsolutions.SpringJmsExample.service.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class Receiver {

    @JmsListener(destination = "order-queue")
    public void receiveMessage(String order){

        log.info("Order Recieved = " + order);
    }


}
