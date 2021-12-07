package dev.struchkov.yandex.report.service.impl;

import dev.struchkov.yandex.report.service.FileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class FileReaderImpl implements FileReader {

    public Optional<String> read(Path path) {
        try {
            return Optional.of(Files.readString(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return Optional.empty();
        }
    }

}
