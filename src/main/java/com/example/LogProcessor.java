package com.example;

import com.example.persistence.HsqldbRepository;
import com.example.service.AlertService;
import com.example.service.LogService;

import java.io.File;
import java.io.IOException;

public class LogProcessor {

    public static Long DURATION_THRESHOLD = 4L;

    public static void main(String[] args) throws IOException {
        if(args.length != 1) {
            System.out.println("Please specify exactly one file with the logs");
            System.exit(1);
        }

        File inputFile = new File(args[0]);
        if(!inputFile.exists()) {
            System.out.println("Specified file doesn't exist");
            System.exit(1);
        }

        LogService logService = new LogService(new AlertService(new HsqldbRepository(), DURATION_THRESHOLD));
        logService.parse(inputFile);
    }
}
