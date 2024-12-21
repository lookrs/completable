package org.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner extends JobLauncherApplicationRunner {
    private final JobRegistry jobRepository;
    private final JobLauncher jobLauncher;
    /**
     * Create a new {@link JobLauncherApplicationRunner}.
     *
     * @param jobLauncher    to launch jobs
     * @param jobExplorer    to check the job repository for previous executions
     * @param jobRepository  to check if a job instance exists with the given parameters
     *                       when running a job
     */
    public MyRunner(JobLauncher jobLauncher, JobExplorer jobExplorer, JobRepository jobRepository,JobRegistry jobRegistry) {
        super(jobLauncher, jobExplorer, jobRepository);
        this.jobRepository = jobRegistry;
        this.jobLauncher = jobLauncher;
    }

    @Override
    public void run(String... args) throws JobExecutionException {
        System.out.println("使用runner?>>>>");
        Job job6 = jobRepository.getJob("simpleJob7");
        jobLauncher.run(job6, new JobParameters());
        System.out.println("使用runner");
    }
}
