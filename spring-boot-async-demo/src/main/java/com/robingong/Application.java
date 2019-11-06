package com.robingong;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Robin
 * @date 2019/11/6
 */
@Slf4j
@SpringBootApplication
@EnableAsync
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private AsyncComponent asyncComponent;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 1 << 10; i++) {
            log.info("Run async {} times", i);
            asyncComponent.asyncRun();
        }
    }

}
