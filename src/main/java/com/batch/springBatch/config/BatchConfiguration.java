package com.batch.springBatch.config;

import com.batch.springBatch.steps.ItemProcessorStep;
import com.batch.springBatch.steps.ItemReaderStep;
import com.batch.springBatch.steps.ItemWriterStep;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfiguration {

    @Bean
    public ItemReaderStep itemReaderStep(){
        return new ItemReaderStep();
    }
    @Bean
    public ItemProcessorStep itemProcessorStep(){
        return new ItemProcessorStep();
    }
    @Bean
    public ItemWriterStep itemWriterStep(){
        return new ItemWriterStep();
    }
}
