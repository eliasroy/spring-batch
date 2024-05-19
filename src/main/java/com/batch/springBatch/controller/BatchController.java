package com.batch.springBatch.controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/batch")
@RequiredArgsConstructor
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job job;

    @PostMapping("/uploadFile")
    public ResponseEntity<?>  receiveFile(@RequestParam(name = "file")MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();

        try {
            Path path =
                    Path.of("src" + File.separator+"main"+File.separator+"resources"+File.separator+"files"+File.separator+fileName);

            Files.createDirectories(path.getParent());
            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            log.info("archivo guardado");
            log.info("inicio del proceso batch");
            JobParameters jobParameters=new JobParametersBuilder()
                    .addDate("fecha",new Date())
                    .addString("fileName",fileName)
                    .toJobParameters();

            jobLauncher.run(job,jobParameters);

            Map<String,String>response=new HashMap<>();
            response.put("fileName",fileName);
            response.put("message", "Archivo guardado");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            log.error("error al procesar el archivo", e.getMessage());
            throw new RuntimeException();
        }

    }
}
