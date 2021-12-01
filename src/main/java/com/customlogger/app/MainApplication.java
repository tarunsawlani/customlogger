package com.customlogger.app;

import com.customlogger.dao.EventDetails;
import com.customlogger.dao.EventDetailsRepository;
import com.customlogger.service.CustomLoggerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.File;
import java.io.IOException;


@Slf4j

@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.customlogger.service", "com.customlogger.dao"})
@EnableJpaRepositories(basePackageClasses = EventDetailsRepository.class)
@EntityScan(basePackageClasses = EventDetails.class)
public class MainApplication implements CommandLineRunner {


    @Autowired
    private CustomLoggerService customerLoggerService;


    public static void main(String[] args) {
        log.info("STARTING THE APPLICATION");
        SpringApplication.run(MainApplication.class, args);
        log.info("APPLICATION FINISHED");
    }


    @Override
    public void run(String... args) throws IOException {
        LineIterator it = null;
        try {
            long startTime = System.currentTimeMillis();
            it = FileUtils.lineIterator(new File(args[0]), "UTF-8");
            while (it.hasNext()) {
                String line = it.nextLine();
                customerLoggerService.processLogs(line);
            }
            log.info("Time taken to process the file ={} ms", System.currentTimeMillis() - startTime);
        } catch (JsonProcessingException jsonProcessingException) {
            log.error("Parsing issue identified " + jsonProcessingException);

            System.err.println("Parsing issue identified with args" + args[0]);

        } catch (IOException ioException) {
            log.error("File provided in the input not found " + ioException);
            System.err.println("File not found" + args[0]);
        } finally {
            LineIterator.closeQuietly(it);
        }


    }

}
