package dev.struchkov.yandex.report.service;

import dev.struchkov.yandex.report.domain.YearData;
import dev.struchkov.yandex.report.domain.YearReport;

import java.time.Year;
import java.util.List;

public interface YearService extends ReportService<YearData> {

    List<YearData> getByYear(Year year);

    YearReport generateYearReport(Year year);

    List<YearReport> generateAllYearReport();

}
