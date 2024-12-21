package org.example;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AppConfig {

    @Bean("poolFileReceive")
    public ThreadPoolTaskExecutor threadPoolTaskExecutorA() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(20);
        taskExecutor.setMaxPoolSize(22);
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean("poolFileHandler")
    public ThreadPoolTaskExecutor threadPoolTaskExecutorB() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.initialize();
        return taskExecutor;
    }

    @PreDestroy
    public void shutdownThreadPool() {
        System.out.println(" 容器关闭前。。。" );
    }
}
