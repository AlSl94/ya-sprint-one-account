package dev.struchkov.yandex.report.scheduler;

import dev.struchkov.yandex.report.domain.MonthData;
import dev.struchkov.yandex.report.service.ReportReadService;
import dev.struchkov.yandex.report.service.ReportService;

import java.time.Month;
import java.time.Year;
import java.util.List;

public class MontScheduler extends AbstractScheduler<MonthData> {

    public MontScheduler(
            String rootPath,
            String fileNamePattern,
            ReportService<MonthData> reportService,
            ReportReadService<MonthData> reportReadService
    ) {
        super(rootPath, fileNamePattern, reportService, reportReadService);
    }

    @Override
    protected void preProcessing(List<MonthData> report, String fileName) {
        final Year year = Year.parse(fileName.substring(2, 6));
        final Month month = Month.of(Integer.parseInt(fileName.substring(6, 8)));
        for (MonthData monthData : report) {
            monthData.setMonth(month);
            monthData.setYear(year);
        }
    }

}
