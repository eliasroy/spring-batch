package com.batch.springBatch.steps;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Slf4j
public class ItemDescompressStep implements Tasklet {
    @Autowired
    private ResourceLoader resourceLoader;
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
       log.info("inicio de step descompress");

        log.info("obtiene el archivo de resources del proyecto");
        Resource  resource=resourceLoader.getResource("classpath:files/person.zip");
        log.info("obtiene el path del archivo");
        String filePath=resource.getFile().getAbsolutePath();

        log.info("obtiene el archivo zip");
        ZipFile zipFile=new ZipFile(filePath);
        log.info("oteien la direccion a descomprimir direcion padre del zip");
        File destDir=new File(resource.getFile().getParent(),"destination");

        log.info("si no existe la direccion la crea");
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        log.info("obtiene los archivos del zip");
        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        log.info("descomprime los archivos");
        while (entries.hasMoreElements()) {
            log.info("obtiene el entry de entries");
            ZipEntry entry = entries.nextElement();
            log.info("obtiene el nombre del entry y guarda dentro de destDir ");
            File file = new File(destDir, entry.getName());
            log.info("si el entry es directorio lo crea");
            if (file.isDirectory()) {
                file.mkdirs();
            } else {
                log.info("si el entry es archivo lo descomprime");
                InputStream inputStream = zipFile.getInputStream(entry);
                log.info("guarda el archivo en un archivo");
                FileOutputStream  outputStream = new FileOutputStream(file);


                byte[] buffer = new byte[1024];
                int len;
                log.info("descomprime el archivo y lo guarda en un archivo");
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
                outputStream.close();
                inputStream.close();
            }
        }

        zipFile.close();
       log.info("fin step descompress");
        return RepeatStatus.FINISHED;
    }
}
