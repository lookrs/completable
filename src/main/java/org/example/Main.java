package org.example;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@SpringBootApplication
@Log4j2
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        BlockDemoService Test0043AsynService = context.getBean(BlockDemoService.class);
        List<String> filePrefix = List.of("target_file_001", "target_file_002", "target_file_003", "target_file_004",
                "target_file_005",
                "target_file_006", "target_file_007", "target_file_008");
        ArrayBlockingQueue<String> filesQueue = new ArrayBlockingQueue<>(filePrefix.size());
        AtomicBoolean finishedFlag = new AtomicBoolean(false);
        List<CompletableFuture<String>> receiveFilesFuture =
                filePrefix.stream().map(file -> Test0043AsynService.runReceiveFile(file)).toList();

        CompletableFuture.runAsync(() -> {
            receiveFilesFuture.forEach(thread -> filesQueue.add(thread.join()));
            finishedFlag.set(true);
        });
        List<CompletableFuture<Integer>> executeResult = new ArrayList<>(filePrefix.size());
        while (!filesQueue.isEmpty() || !finishedFlag.get()) {
            try {
                String take = filesQueue.take();
                CompletableFuture<Integer> resultStatusFuture = Test0043AsynService.run(take);
                executeResult.add(resultStatusFuture);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
//        List<CompletableFuture<Integer>> executeResult =
//                filePrefix.stream().map(fileInfo -> CompletableFuture.supplyAsync(
//                                () -> Test0043AsynService.runReceiveFile(fileInfo)
//                                        .join())
//                        .thenApplyAsync(receiveFile -> Test0043AsynService.run(receiveFile)
//                                .join())
//                ).toList();

        List<Integer> finalResult = executeResult.stream().map(CompletableFuture::join).toList();
        System.out.println("finalResult = " + finalResult);
        Thread.getAllStackTraces().keySet().forEach(thread ->
                System.out.println(thread.getName() + " - Daemon: " + thread.isDaemon())
        );
//        context.close();
    }
}