package com.linkedin.test;

import com.linkedin.parseq.Engine;
import com.linkedin.parseq.EngineBuilder;
import com.linkedin.parseq.Task;
import com.linkedin.parseq.httpclient.HttpClient;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ParseqTest {

  public static void main(String[] args) throws InterruptedException {
    final int numCores = Runtime.getRuntime().availableProcessors();
    final ExecutorService taskScheduler = Executors.newFixedThreadPool(numCores + 1);
    final ScheduledExecutorService timerScheduler = Executors.newSingleThreadScheduledExecutor();

    final Engine engine = new EngineBuilder()
        .setTaskExecutor(taskScheduler)
        .setTimerScheduler(timerScheduler)
        .build();

    Thread.sleep(30000);

    final Task<String> googleContentType = getContentType("http://www.google.com", 60000);
    final Task<String> bingContentType = getContentType("http://www.bing.com", 60000);

    final Task<String> contentTypes = Task.par(googleContentType, bingContentType)
        .map("concatenate", (google, bing) -> "Google:" + google + "\n" + "Bing: " + bing + "\n")
        .andThen("print", System.out::print);

    engine.run(contentTypes);

    engine.shutdown();
    engine.awaitTermination(10, TimeUnit.MINUTES);
    taskScheduler.shutdown();
    timerScheduler.shutdown();
    System.exit(0);
  }

  private static Task<String> getContentType(String url, final long time) {
    return HttpClient.get(url).task()
        .map("getContentType", response -> {
          Thread.sleep(time);
          return response.getContentType();
        });
  }
}
