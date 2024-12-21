package org.example;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
public class TestService {

    @Getter
    private List<String> tasks = new ArrayList<>();

    @PostConstruct
    void setTasks() {
        tasks.addAll(List.of("任务1", "任务2", "任务3", "任务4", "任务6", "任务5", "任务7", "任务8", "任务9"));
    }

    @Async("poolA")
    CompletableFuture<String> runRec(String taskName) {
        log.info("taskName = " + taskName + ",thread: " + Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(taskName + "的结果（步骤一）");
    }


    @Async
    CompletableFuture<String> run(String taskRes) {
        log.info("taskRes = " + taskRes + ",thread: " + Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
            log.info(taskRes + " 完成,thread: " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(taskRes + "|（步骤二）");
    }
}
