package com.robingong;

import io.moquette.interception.AbstractInterceptHandler;
import io.moquette.interception.messages.InterceptConnectMessage;
import io.moquette.interception.messages.InterceptDisconnectMessage;
import io.moquette.interception.messages.InterceptPublishMessage;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Robin
 * @date 2019/6/11
 */
@Slf4j
@Component
public class MqttMessageHandler extends AbstractInterceptHandler {
    @Override
    public String getID() {
        return MqttMessageHandler.class.getName();
    }

    @Override
    public void onConnect(InterceptConnectMessage msg) {
        log.info("Client connected: {}", msg.getClientID());
    }

    @Override
    public void onDisconnect(InterceptDisconnectMessage msg) {
        log.info("Client disconnected: {}", msg.getClientID());
    }

    @Override
    public void onPublish(InterceptPublishMessage msg) {
        log.info("Get message from: {}", msg.getClientID());
        ByteBuf buf = msg.getPayload();
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String payload = new String(bytes);
        log.info("Payload: {}", payload);
    }
}
