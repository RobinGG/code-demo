package com.robingong;

import io.moquette.BrokerConstants;
import io.moquette.broker.Server;
import io.moquette.broker.config.IConfig;
import io.moquette.broker.config.MemoryConfig;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * @author Robin
 * @date 2019/6/11
 */
@Component
public class MqttServer {

    private Server mqttBroker;

    private IConfig config;

    @PostConstruct
    public void init() throws Exception {
        mqttBroker = new Server();
        Properties properties = new Properties();
        properties.setProperty(BrokerConstants.PORT_PROPERTY_NAME, String.valueOf(BrokerConstants.PORT));
        properties.setProperty(BrokerConstants.HOST_PROPERTY_NAME, BrokerConstants.HOST);
        config = new MemoryConfig(properties);
        start();
    }

    public void start() throws Exception {
        mqttBroker.startServer(config);
    }

    public void stop() {
        mqttBroker.stopServer();
    }

}
