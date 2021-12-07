package dev.struchkov.yandex.report.service.impl;

import dev.struchkov.yandex.report.service.FileReader;
import dev.struchkov.yandex.report.service.ReportReadService;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractReportCsvReadService<T> implements ReportReadService<T> {

    private final FileReader fileReader;
    private String lineBreak = "\n";
    private String comma = ",";

    protected AbstractReportCsvReadService(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    protected AbstractReportCsvReadService(
            FileReader fileReader,
            String lineBreak,
            String comma
    ) {
        this.fileReader = fileReader;
        this.lineBreak = lineBreak;
        this.comma = comma;
    }

    protected List<T> getReport(Path filePath, Function<String[], T> f) {
        final Optional<String> optFileText = fileReader.read(filePath);
        if (optFileText.isPresent()) {
            final String fileText = optFileText.get();
            final String[] split = fileText.split(lineBreak);
            final List<T> results = new ArrayList<>();
            for (int i = 1; i < split.length; i++) {
                final String[] item = split[i].split(comma);
                final T obj = f.apply(item);
                results.add(obj);
            }
            return results;
        }
        return Collections.emptyList();
    }

}
