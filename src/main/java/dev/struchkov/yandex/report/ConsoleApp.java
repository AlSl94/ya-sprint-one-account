package dev.struchkov.yandex.report;

import dev.struchkov.yandex.report.domain.MonthReport;
import dev.struchkov.yandex.report.domain.YearReport;
import dev.struchkov.yandex.report.scheduler.MontScheduler;
import dev.struchkov.yandex.report.scheduler.YearScheduler;
import dev.struchkov.yandex.report.service.Accountant;
import dev.struchkov.yandex.report.service.MonthService;
import dev.struchkov.yandex.report.service.Presentation;
import dev.struchkov.yandex.report.service.YearService;

import java.time.Month;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class ConsoleApp {

    private final YearScheduler yearScheduler;
    private final MontScheduler montScheduler;
    private final Accountant accountant;
    private final YearService yearService;
    private final MonthService monthService;
    private final Presentation presentation;

    public ConsoleApp(
            YearScheduler yearScheduler,
            MontScheduler montScheduler,
            Accountant accountant,
            YearService yearService,
            MonthService monthService,
            Presentation presentation
    ) {
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
                case 1:
                    yearScheduler.run();
                    montScheduler.run();
                    break;
                case 2:
                    final Timer timer = new Timer();
                    timer.schedule(
                            new TimerTask() {
                                @Override
                                public void run() {
                                    yearScheduler.run();
                                }
                            },
                            0, 60_000
                    );

                    final Timer monthTimer = new Timer();
                    monthTimer.schedule(
                            new TimerTask() {
                                @Override
                                public void run() {
                                    montScheduler.run();
                                }
                            },
                            0, 60_000
                    );
                    break;

                case 3:
                    final List<MonthReport> monthReports = monthService.generateAllMonthReport();
                    presentation.showMonthReport(monthReports);
                    break;
                case 4:
                    final List<YearReport> yearReports = yearService.generateAllYearReport();
                    presentation.showYearReport(yearReports);
                    break;
                case 5:
                    final String year = presentation.userInput("Введите год: ");
                    final Set<Month> months = accountant.dataReconciliation(Integer.parseInt(year));
                    presentation.showResultDataReconciliation(months);
                    break;
            }
        } while (command != 0);

    }
}
