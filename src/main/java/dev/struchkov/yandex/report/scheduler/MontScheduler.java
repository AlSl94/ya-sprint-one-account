package dev.struchkov.yandex.report.scheduler;

import dev.struchkov.yandex.report.domain.MonthData;
import dev.struchkov.yandex.report.service.MonthService;
import dev.struchkov.yandex.report.service.ReportReadService;

import java.time.Month;
import java.time.Year;
import java.util.List;

public class MontScheduler extends AbstractScheduler<MonthData> {

    private final MonthService monthService;

    public MontScheduler(
            String rootPath,
            String fileNamePattern,
            MonthService monthService,
            ReportReadService<MonthData> reportReadService
    ) {
        super(rootPath, fileNamePattern, monthService, reportReadService);
        this.monthService = monthService;
    }

    @Override
    protected void clearOld(String fileName) {
        final Year year = getYear(fileName);
        final Month month = getMonth(fileName);
        monthService.clear(year, month);
    }

    @Override
    protected void preProcessing(List<MonthData> report, String fileName) {
        final Year year = getYear(fileName);
        final Month month = getMonth(fileName);
        for (MonthData monthData : report) {
            monthData.setMonth(month);
            monthData.setYear(year);
        }
    }

    private Month getMonth(String fileName) {
        return Month.of(Integer.parseInt(fileName.substring(6, 8)));
    }

    private Year getYear(String fileName) {
        return Year.parse(fileName.substring(2, 6));
    }

}
