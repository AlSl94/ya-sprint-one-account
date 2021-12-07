package dev.struchkov.yandex.report.service;

import java.nio.file.Path;
import java.util.List;

public interface ReportReadService<T> {

    List<T> readReport(Path filePath);

}
