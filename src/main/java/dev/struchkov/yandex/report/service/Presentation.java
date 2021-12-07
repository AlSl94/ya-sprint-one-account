package dev.struchkov.yandex.report.service;

import dev.struchkov.yandex.report.domain.MonthReport;
import dev.struchkov.yandex.report.domain.YearReport;

import java.util.List;

public interface Presentation {

    void displayMenu();

    String userInput(String text);

    void showMonthReport(List<MonthReport> monthReports);

    void showYearReport(List<YearReport> yearReports);

}
