package org.example;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Log4j2
public class TaskProxy {
    @Autowired
    TestService testService;
    public void run (){
        List<String> tasks = testService.getTasks();

        List<CompletableFuture<String>> futures = tasks.stream().map(task -> CompletableFuture.supplyAsync(
                        () -> testService.runRec(task).join())
                .thenApplyAsync(res -> testService.run(res).join())
        ).toList();

//        List<String> results = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
//                .thenApply(v -> futures.stream()
//                        .map(x-> {
//                            log.info(Thread.currentThread().getName());
//                           return x.join();
//                        })
//                        .collect(Collectors.toList()))
//                .join();
        List<String> results = futures.stream().map(CompletableFuture::join).toList();
        results.forEach(log::info);
    }
}
