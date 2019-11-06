package com.robingong;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author Robin
 * @date 2019/11/6
 */
@Slf4j
@Component
public class AsyncComponent {

    @Async("myExecutor")
    public void asyncRun() {
        log.info("async run");
        int random = new Random().nextInt(100);
        if (random == 1) throw new RuntimeException("async exception");
    }

}
