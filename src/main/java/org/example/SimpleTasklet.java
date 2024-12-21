package org.example;


import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class SimpleTasklet implements Tasklet {

    private final TaskProxy taskProxy;

    public SimpleTasklet(TaskProxy taskProxy) {
        this.taskProxy = taskProxy;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        taskProxy.run();
        System.out.println("执行完毕");
        return RepeatStatus.FINISHED;
    }
}
