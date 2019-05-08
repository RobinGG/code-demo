package com.robingong;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author Robin
 * @date 2019/5/8
 */
@Slf4j
@Configuration
public class MinaTimeServer {

    private static final int PORT = 3360;

    @Autowired
    private IoHandlerAdapter ioHandlerAdapter;

    @PostConstruct
    public void init() throws Exception {
        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        acceptor.setHandler(ioHandlerAdapter);
        acceptor.bind(new InetSocketAddress(PORT));
        log.info("Mina time server is ready");
    }

}
