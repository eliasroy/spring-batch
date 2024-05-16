package com.batch.springBatch.steps;


import com.batch.springBatch.entities.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ItemProcessorStep implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("inicio de step processor");
        log.info("obtiene la lista de personas");
        List<Person> personList=(List<Person>) chunkContext.getStepContext()
                                                            .getStepExecution()
                                                            .getJobExecution()
                                                            .getExecutionContext()
                                                            .get("personsList");
        log.info("crea la fecha de inserccion");
        personList.stream().map(person -> {
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
            person.setInsertionDate(formatter.format(LocalDateTime.now()));
            return person;
        }).toList();

        log.info("guarda la lista en el contexto del job");
        chunkContext.getStepContext()
                    .getStepExecution()
                    .getJobExecution()
                    .getExecutionContext()
                    .put("personList",personList);

        log.info("fin ProcessorStep");
        return RepeatStatus.FINISHED;
    }
}
