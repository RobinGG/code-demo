package com.robingong;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Robin
 * @date 2019/5/8
 */
@Slf4j
@Component
public class TimeServerHandler extends IoHandlerAdapter {

    private static final String QUIT_CMD = "quit";

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String messageStr = message.toString();
        log.info("Message get: {}", messageStr);
        if (QUIT_CMD.equalsIgnoreCase(messageStr.trim())) {
            session.closeNow();
            return;
        }
        Date date = new Date();
        session.write(date.toString());
        log.info("Message wrote: {}", date.toString());
    }
}
