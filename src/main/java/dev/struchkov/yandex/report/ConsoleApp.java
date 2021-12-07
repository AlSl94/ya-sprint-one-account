package dev.struchkov.yandex.report;

import dev.struchkov.yandex.report.domain.MonthReport;
import dev.struchkov.yandex.report.domain.YearReport;
import dev.struchkov.yandex.report.scheduler.MontScheduler;
import dev.struchkov.yandex.report.scheduler.YearScheduler;
import dev.struchkov.yandex.report.service.Accountant;
import dev.struchkov.yandex.report.service.MonthService;
import dev.struchkov.yandex.report.service.Presentation;
import dev.struchkov.yandex.report.service.YearService;

import java.nio.file.Path;
import java.util.List;

public class ConsoleApp {

    private final YearScheduler yearScheduler;
    private final MontScheduler montScheduler;
    private final Accountant accountant;
    private final YearService yearService;
    private final MonthService monthService;
    private final Presentation presentation;

    public ConsoleApp(YearScheduler yearScheduler, MontScheduler montScheduler, Accountant accountant, YearService yearService, MonthService monthService, Presentation presentation) {
        this.yearScheduler = yearScheduler;
        this.montScheduler = montScheduler;
        this.accountant = accountant;
        this.yearService = yearService;
        this.monthService = monthService;
        this.presentation = presentation;
    }

    public void run() {
        int command;
        do {
            presentation.displayMenu();
            command = Integer.parseInt(presentation.userInput("Введите номер команды: "));
            switch (command) {
                case 1 -> {
                    yearScheduler.run();
                    montScheduler.run();
                }
                case 2 -> {
                    final List<MonthReport> monthReports = monthService.generateAllMonthReport();
                    presentation.showMonthReport(monthReports);
                }
                case 3 -> {
                    final List<YearReport> yearReports = yearService.generateAllYearReport();
                    presentation.showYearReport(yearReports);
                }
                case 4 -> {
                    final String year = presentation.userInput("Введите год: ");
                    accountant.dataReconciliation(Integer.parseInt(year));
                }
            }
        } while (command != 0);

    }
}
