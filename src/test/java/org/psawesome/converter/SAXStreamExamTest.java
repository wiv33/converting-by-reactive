package org.psawesome.converter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLEventFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

class SAXStreamExamTest {
    XMLEventFactory factory;
    final String FILE_PATH = "data/xml/testing.xml";
    Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        factory = XMLEventFactory.newInstance();
        final String substring = FILE_PATH.substring(0, FILE_PATH.lastIndexOf("/"));
        System.out.println("substring = " + substring);
        Files.createDirectories(Path.of(substring));

        final String fileName = FILE_PATH.substring(FILE_PATH.lastIndexOf("/") + 1,
                FILE_PATH.lastIndexOf("."));
        final String fileExtension = FILE_PATH.substring(FILE_PATH.lastIndexOf("."));

        tempFile = Files.createTempFile(Path.of(substring),
                fileName, fileExtension);
    }

    @AfterEach
    void tearDown() throws IOException {
        System.out.println("SAXStreamExamTest.tearDown");
        Files.delete(tempFile);
        Files.deleteIfExists(Path.of(FILE_PATH));
    }

    @Test
    void testCreateFiles() {
        final boolean exists = Files.exists(tempFile);
        Assertions.assertTrue(exists);
        System.out.println(tempFile);
    }
}

