package org.example;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@SpringBootApplication
@Log4j2
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        TestService testService = context.getBean(TestService.class);
        List<String> tasks = testService.getTasks();

        List<CompletableFuture<String>> futures = tasks.stream().map(task -> CompletableFuture.supplyAsync(
                        () -> testService.runRec(task).join())
                .thenApplyAsync(res -> testService.run(res).join())
        ).toList();

        List<String> results = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()))
                .join();
        results.forEach(log::info);
    }
}