package com.batch.springBatch.steps;

import java.util.List;

import com.batch.springBatch.entities.Person;
import com.batch.springBatch.service.IPersonService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j

public class ItemWriterStep implements Tasklet {
    @Autowired
    private  IPersonService personService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("inicio de step writer");
        log.info("------------------");
        log.info("obtiene la lista de personas");
        List<Person> personList=(List<Person>) chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext()
                .get("personsList");
        log.info("imprime si no es null");
        personList.forEach(person -> {
            if (person!=null){
                log.info(person.toString());
            }
        });
        log.info("guarda en la base de datos");
        personService.saveAll(personList);
        log.info("fin de step writer");
        return RepeatStatus.FINISHED;
    }
}
