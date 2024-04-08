package com.qt.demo.controller.pool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.*;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/06/22 15:52
 * @version: V1.0
 */
@Slf4j
@Controller
@RequestMapping("/pool")
public class PoolController {

    int corePoolSize = 8;
    int maximumPoolSize = 10;
    long keepAliveTime = 30;
    TimeUnit unit = TimeUnit.SECONDS;
    /**
     * ArrayBlockingQueue
     * LinkedBlockingQueue
     * DelayQueue
     * PriorityBlockingQueue
     * SynchronousQueue
     */
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
    ThreadFactory threadFactory ;
    /**
     *  CallerRunsPolicy 当前主线程执行任务 <br>
     *  DiscardOldestPolicy 移除最开始入队列的数据 再尝试入队
     *
     */

    RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();

    @GetMapping()
    @ResponseBody
    public String testMongoDB() {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue,threadFactory,handler);


        return null;
    }


}
