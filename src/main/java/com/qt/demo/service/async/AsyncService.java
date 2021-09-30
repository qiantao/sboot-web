package com.qt.demo.service.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/09/16 09:53
 * @version: V1.0
 */
@Service
@Slf4j
public class AsyncService {

    @Async
    public Future<String> doActive(){
      log.info("当前线程========执行");
      return new Future<String>() {
          @Override
          public boolean cancel(boolean mayInterruptIfRunning) {
              return false;
          }

          @Override
          public boolean isCancelled() {
              return false;
          }

          @Override
          public boolean isDone() {
              return false;
          }

          @Override
          public String get() throws InterruptedException, ExecutionException {
              return null;
          }

          @Override
          public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
              return null;
          }
      };

    }
}
