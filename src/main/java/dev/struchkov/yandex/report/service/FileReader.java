package dev.struchkov.yandex.report.service;

import java.nio.file.Path;
import java.util.Optional;

public interface FileReader {

    Optional<String> read(Path path);

}
