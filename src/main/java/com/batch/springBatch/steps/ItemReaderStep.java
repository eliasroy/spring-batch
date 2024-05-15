package com.batch.springBatch.steps;

import com.batch.springBatch.entities.Person;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Slf4j

public class ItemReaderStep implements Tasklet {
    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.info("------------------");
        log.info("inicio ItemReaderStep");
        log.info("obtiene el archivo de resources del proyecto");
        Reader reader=new FileReader(
                resourceLoader.getResource("classpath:files/destination/persons.csv")
                        .getFile());
        log.info("crea el parser para el archivo con el separador");
        CSVParser  parser=new CSVParserBuilder()
                .withSeparator(',')
                .build();
        log.info("crea el reader para el archivo donde salta la primera linea y se pasa el seprador" +
                "se define el objeto que nos ayudara a leer el archivo");
        CSVReader csvReader=new CSVReaderBuilder(reader)
                .withCSVParser(parser)
                .withSkipLines(1)
                .build();

        log.info("------------------");
        List<Person>personList=new ArrayList<>();
        String[] line;
        log.info("lee el archivo y lo guarda en la lista");
        while ((line=csvReader.readNext())!=null){
            personList.add(Person.builder()
                    .name(line[0])
                    .lastName(line[1])
                    .age(Integer.parseInt(line[2]))
                    .build());
        }
        csvReader.close();
        reader.close();
        log.info("guarda la lista en el contexto del job");
        chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext()
                .put("personList",personList);
        log.info("FIN paso de lectura ItemReaderStep");
        return RepeatStatus.FINISHED;
    }
}
