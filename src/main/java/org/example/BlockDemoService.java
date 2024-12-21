package org.example;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class BlockDemoService {

    @Async("poolFileReceive")
    public CompletableFuture<String> runReceiveFile(String filePrefix) {
        // 模拟耗时
        try {
            System.out.println(Thread.currentThread().getName() + " 按前缀取文件 " + filePrefix);
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + " 按前缀取文件 " + filePrefix + " 完成 ");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture("取得结果->" + Thread.currentThread().getName() + filePrefix + ".tsv");
    }

    @Async("poolFileHandler")
    public CompletableFuture<Integer> run(String fileName){
        // 模拟耗时
        try {
            System.out.println(Thread.currentThread().getName() + " 处理文件 " + fileName);
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + " 处理文件 " + fileName + " 完成 ");
        } catch (InterruptedException e) {
            return CompletableFuture.completedFuture(100);
        }
        return CompletableFuture.completedFuture(0);
    }
}
