package org.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableAsync
public class AppConfig implements AsyncConfigurer {
    private final JobRepository jobRepository;

    public AppConfig(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public AsyncTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(1);
        taskExecutor.setMaxPoolSize(6);
        taskExecutor.setThreadNamePrefix("pool-B-");
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean
    public Job simpleJob(Step simpleStep) {
        return new JobBuilder("simpleJob7", jobRepository)
                .start(simpleStep)
                .build();
    }

    @Bean
    public Step simpleStep(SimpleTasklet simpleTasklet, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("simpleStep7", jobRepository).tasklet(simpleTasklet,platformTransactionManager)
                .build();
    }




    @Bean("poolA")
    public AsyncTaskExecutor threadPoolTaskExecutorA() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(1);
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.initialize();
        return taskExecutor;
    }

}
