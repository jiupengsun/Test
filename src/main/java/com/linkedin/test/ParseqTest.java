package com.linkedin.test;

public class ParseqTest {

  public static void main(String[] args) {
    final int numCores = Runtime.getRuntime().availableProcessors();
    final ExecutorService taskScheduler = Executors.newFixedThreadPool(numCores + 1);
    final ScheduledExecutorService timerScheduler = Executors.newSingleThreadScheduledExecutor();

    final Engine engine = new EngineBuilder()
        .setTaskExecutor(taskScheduler)
        .setTimerScheduler(timerScheduler)
        .build();
  }
}
