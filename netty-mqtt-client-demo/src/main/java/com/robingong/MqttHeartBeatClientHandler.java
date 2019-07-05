package com.robingong;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.mqtt.*;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author Robin
 * @date 2019/7/5
 */
public class MqttHeartBeatClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        MqttFixedHeader connectFixedHeader = new MqttFixedHeader(MqttMessageType.CONNECT, false, MqttQoS.AT_MOST_ONCE, false, 0);
        MqttConnectVariableHeader connectVariableHeader = new MqttConnectVariableHeader("MQTT", 4, true, true, false, 0, false, false, 20);
        MqttConnectPayload connectPayload = new MqttConnectPayload("guestClient", null, null, "guest", "guest".getBytes("utf-8"));
        MqttConnectMessage connectMessage = new MqttConnectMessage(connectFixedHeader, connectVariableHeader, connectPayload);
        ctx.channel().writeAndFlush(connectMessage);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            MqttFixedHeader pingreqFixedHeader = new MqttFixedHeader(MqttMessageType.PINGREQ, false, MqttQoS.AT_MOST_ONCE, false, 0);
            MqttMessage pingreqMessage = new MqttMessage(pingreqFixedHeader);
            ctx.channel().writeAndFlush(pingreqMessage);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
