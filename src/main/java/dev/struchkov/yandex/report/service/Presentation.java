package dev.struchkov.yandex.report.service;

import dev.struchkov.yandex.report.domain.MonthReport;
import dev.struchkov.yandex.report.domain.YearReport;

import java.time.Month;
import java.util.List;
import java.util.Set;

public interface Presentation {

    void displayMenu();

    String userInput(String text);

    void showMonthReport(List<MonthReport> monthReports);

    void showYearReport(List<YearReport> yearReports);

    void showResultDataReconciliation(Set<Month> months);

}
