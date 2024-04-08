package com.qt.demo.exec;

import org.springframework.stereotype.Component;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author MuYang
 * @Date 2022/9/23 09:58
 * @version: V1.0
 */
@Component
public class ExecTest {

    public static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    public static final int MAX_POOL_SIZE = CORE_POOL_SIZE * 4 - 1;

    public static final ExecutorService EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            30L, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(10240),
            build("mcd-%d"),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static ThreadFactory build(final String name) {
        return new ThreadFactory() {
            final AtomicLong count = name != null ? new AtomicLong(0L) : null;

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                if (this.count != null) {
                    t.setName(name + "-" + this.count.getAndIncrement());
                }
                return t;
            }
        };
    }


}
