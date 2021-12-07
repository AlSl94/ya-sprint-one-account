package dev.struchkov.yandex.report.scheduler;

import dev.struchkov.yandex.report.domain.YearData;
import dev.struchkov.yandex.report.service.ReportReadService;
import dev.struchkov.yandex.report.service.ReportService;

import java.time.Year;
import java.util.List;

public class YearScheduler extends AbstractScheduler<YearData> {

    public YearScheduler(
            String rootPath,
            String fileNamePattern,
            ReportService<YearData> reportService,
            ReportReadService<YearData> reportReadService
    ) {
        super(rootPath, fileNamePattern, reportService, reportReadService);
    }

    @Override
    protected void preProcessing(List<YearData> yearDataList, String fileName) {
        final Year year = Year.parse(fileName.substring(2, 6));
        for (YearData yearData : yearDataList) {
            yearData.setYear(year);
        }
    }

}
