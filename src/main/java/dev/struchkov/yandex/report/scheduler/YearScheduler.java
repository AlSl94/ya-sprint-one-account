package dev.struchkov.yandex.report.scheduler;

import dev.struchkov.yandex.report.domain.YearData;
import dev.struchkov.yandex.report.service.ReportReadService;
import dev.struchkov.yandex.report.service.YearService;

import java.time.Year;
import java.util.List;

public class YearScheduler extends AbstractScheduler<YearData> {

    private final YearService yearService;

    public YearScheduler(
            String rootPath,
            String fileNamePattern,
            YearService yearService,
            ReportReadService<YearData> reportReadService
    ) {
        super(rootPath, fileNamePattern, yearService, reportReadService);
        this.yearService = yearService;
    }

    @Override
    protected void clearOld(String fileName) {
        final Year year = getYear(fileName);
        yearService.clear(year);
    }

    @Override
    protected void preProcessing(List<YearData> yearDataList, String fileName) {
        final Year year = getYear(fileName);
        for (YearData yearData : yearDataList) {
            yearData.setYear(year);
        }
    }

    private Year getYear(String fileName) {
        return Year.parse(fileName.substring(2, 6));
    }

}
