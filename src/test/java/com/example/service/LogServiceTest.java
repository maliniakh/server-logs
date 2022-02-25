package com.example.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

import static org.mockito.Mockito.*;

class LogServiceTest {

    LogService logService;

    @Test
    void testParse() throws IOException {
        AlertService alertService = mock(AlertService.class);
        logService = new LogService(alertService);

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("logfile.txt").getFile());

        logService.parse(file);

        verify(alertService, times(6)).process(any());
    }

    @Test
    void testFilesDoesntExist() {
        AlertService alertService = mock(AlertService.class);
        logService = new LogService(alertService);

        File file = new File("non-existent.txt");
        Assertions.assertThrows(NoSuchFileException.class, () -> {
            logService.parse(file);
        });
    }
}