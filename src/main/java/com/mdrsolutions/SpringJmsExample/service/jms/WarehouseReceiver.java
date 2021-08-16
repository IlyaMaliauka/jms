package com.mdrsolutions.SpringJmsExample.service.jms;

import com.mdrsolutions.SpringJmsExample.pojos.GoodsOrder;
import com.mdrsolutions.SpringJmsExample.pojos.ProcessedGoodsOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.adapter.JmsResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class WarehouseReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseReceiver.class);

    @Autowired
    private WarehouseProcessingService warehouseProcessingService;

    @JmsListener(destination = "goods.order.queue")
    public JmsResponse<Message<ProcessedGoodsOrder>> receive(@Payload GoodsOrder goodsOrder,
                                                             @Header(name="orderState")String orderState,
                                                             @Header(name="goodsOrderId")String goodsOrderId,
                                                             @Header(name="storeId")String storeId
                                                           ){
        LOGGER.info("Message received!");
        LOGGER.info("Message is == " + goodsOrder);
        LOGGER.info("Message property orderState = {}, goodsOrderId = {}, storeId = {}", orderState, goodsOrder, storeId);


        if(goodsOrder.getGoods().getTitle().startsWith("L")){
            throw new IllegalArgumentException("Error in order=" + goodsOrder.getGoodsOrderId()
                    + " too much!");
        }

        return warehouseProcessingService.processOrder(goodsOrder, orderState, storeId);
    }
}
