package com.batch.springBatch.config;

import com.batch.springBatch.steps.ItemDescompressStep;
import com.batch.springBatch.steps.ItemProcessorStep;
import com.batch.springBatch.steps.ItemReaderStep;
import com.batch.springBatch.steps.ItemWriterStep;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Bean
    @JobScope
    public ItemDescompressStep itemDescompressStep(){
        return new ItemDescompressStep();
    }

    @Bean@JobScope
    public ItemReaderStep itemReaderStep(){
        return new ItemReaderStep();
    }
    @Bean
    @JobScope
    public ItemProcessorStep itemProcessorStep(){
        return new ItemProcessorStep();
    }
    @Bean
    @JobScope
    public ItemWriterStep itemWriterStep(){
        return new ItemWriterStep();
    }

    @Bean
    public Step descompressFileStep(JobRepository jobRepository,ItemDescompressStep tasklet,
                                 PlatformTransactionManager transactionManager){
        return new StepBuilder("itemDescompressStep", jobRepository)
                .tasklet(tasklet,transactionManager)
                .build();
    }
    @Bean
    public Step readerFileStep(JobRepository jobRepository,ItemReaderStep tasklet,
                               PlatformTransactionManager transactionManager){
        return new StepBuilder("itemReaderStep", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }
    @Bean
    public Step processorFileStep(JobRepository jobRepository,ItemProcessorStep tasklet,
                                  PlatformTransactionManager transactionManager){
        return new StepBuilder("itemProcessorStep", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }
    @Bean
    public Step writerFileStep(JobRepository jobRepository,ItemWriterStep tasklet,
                               PlatformTransactionManager transactionManager){
        return new StepBuilder("itemWriterStep", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }
    @Bean
    public Job readCSVJob(JobRepository jobRepository,Step descompressFileStep,Step readerFileStep,
                          Step processorFileStep,Step writerFileStep){
        System.out.println("readCSVJob");
        return  new JobBuilder("readCSVJob",jobRepository)
                .start(descompressFileStep)
                .next(readerFileStep)
                .next(processorFileStep)
                .next(writerFileStep)
                .build();
    }
}
