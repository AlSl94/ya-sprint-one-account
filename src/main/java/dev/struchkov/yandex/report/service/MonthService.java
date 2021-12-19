package dev.struchkov.yandex.report.service;

import dev.struchkov.yandex.report.domain.MonthData;
import dev.struchkov.yandex.report.domain.MonthReport;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface MonthService extends ReportService<MonthData> {

    Optional<MonthReport> generateMonthReport(Year year, Month month);

    List<MonthReport> generateAllMonthReport();

    List<MonthData> getByYear(Year year);

    void clear(Year year, Month month);

}
