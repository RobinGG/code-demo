package com.robingong;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.mqtt.*;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

import static io.netty.channel.ChannelFutureListener.CLOSE_ON_FAILURE;
import static io.netty.handler.codec.mqtt.MqttQoS.AT_MOST_ONCE;

/**
 * @author Robin
 * @date 2019/6/11
 */
@Slf4j
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MqttMessage mqttMessage = (MqttMessage) msg;
        MqttMessageType messageType = mqttMessage.fixedHeader().messageType();
        switch (messageType) {
            case CONNECT:
                log.info("Client connected.");
                MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.CONNACK, false, MqttQoS.AT_MOST_ONCE,
                        false, 0);
                MqttConnAckVariableHeader mqttConnAckVariableHeader = new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_ACCEPTED, false);
                MqttConnAckMessage response = new MqttConnAckMessage(mqttFixedHeader, mqttConnAckVariableHeader);
                ctx.writeAndFlush(response).addListener(CLOSE_ON_FAILURE);
            case PINGREQ:
                log.info("HeartBeat received at {}", Instant.now());
                MqttFixedHeader pingHeader = new MqttFixedHeader(MqttMessageType.PINGRESP, false, AT_MOST_ONCE,
                        false, 0);
                MqttMessage pingResp = new MqttMessage(pingHeader);
                ctx.writeAndFlush(pingResp).addListener(CLOSE_ON_FAILURE);
                break;
            default:
                log.warn("Unexpected messageType: {}", messageType);
        }
    }
}
