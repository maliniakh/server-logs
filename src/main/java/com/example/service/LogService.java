package com.example.service;

import com.example.model.EventData;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LogService {

    private static final Logger log = LoggerFactory.getLogger(LogService.class);

    private final AlertService alertService;

    private final Gson gson = new Gson();

    public LogService(AlertService alertService) {
        this.alertService = alertService;
    }

    public void parse(File file) throws IOException {
        log.info("Processing {}...", file.getPath());

        // passing a file handle seems less natural than for example a list of strings,
        // but using streams enables reading files bigger that could fit into memory
        // alternatively a BufferedReader could be used

        Files.lines(Path.of(file.toURI()))
                .filter(l -> !l.trim().isEmpty())
                .map(l -> gson.fromJson(l, EventData.class))
                .forEachOrdered(alertService::process);

        log.info("Done");
    }
}
