package dev.struchkov.yandex.report.scheduler;

import dev.struchkov.yandex.report.service.ReportReadService;
import dev.struchkov.yandex.report.service.ReportService;
import dev.struchkov.yandex.report.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public abstract class AbstractScheduler<T> implements Runnable {

    private static final Integer WORK_FOLDER_MAX_DEPTH = 5;

    private final String rootPath;
    private final String fileNamePattern;
    private final ReportService<T> reportService;
    private final ReportReadService<T> reportReadService;
    private final Map<String, String> md5Map = new HashMap<>();

    public AbstractScheduler(
            String rootPath,
            String fileNamePattern,
            ReportService<T> reportService,
            ReportReadService<T> reportReadService
    ) {
        this.rootPath = rootPath;
        this.fileNamePattern = fileNamePattern;
        this.reportService = reportService;
        this.reportReadService = reportReadService;
    }

    @Override
    public void run() {
        final Path attachmentPath = Paths.get(rootPath);
        final PathMatcher filter = attachmentPath.getFileSystem().getPathMatcher(fileNamePattern);
        try (final Stream<Path> stream = Files.walk(attachmentPath, WORK_FOLDER_MAX_DEPTH)) {
            final List<Path> pathFiles = stream
                    .filter(filter::matches)
                    .filter(Files::isRegularFile)
                    .toList();
            for (Path path : pathFiles) {
                final String fileName = FileUtils.getFileName(path);
                final String md5 = FileUtils.getFileChecksum(path);
                if (md5Map.containsKey(fileName)) {
                    final String saveMd5 = md5Map.get(fileName);
                    if (!saveMd5.equals(md5)) {
                        final List<T> reports = reportReadService.readReport(path);
                        preProcessing(reports,fileName);
                        reportService.updateAll(reports);
                    }
                } else {
                    md5Map.put(fileName, md5);
                    final List<T> reports = reportReadService.readReport(path);
                    preProcessing(reports, fileName);
                    reportService.createAll(reports);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    protected abstract void preProcessing(List<T> report, String fileName);

}
