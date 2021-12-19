package dev.struchkov.yandex.report;

import dev.struchkov.yandex.report.domain.MonthData;
import dev.struchkov.yandex.report.domain.YearData;
import dev.struchkov.yandex.report.repository.MonthRepository;
import dev.struchkov.yandex.report.repository.YearRepository;
import dev.struchkov.yandex.report.repository.impl.MonthRepositoryImpl;
import dev.struchkov.yandex.report.repository.impl.ReportYearMapRepositoryImpl;
import dev.struchkov.yandex.report.scheduler.MontScheduler;
import dev.struchkov.yandex.report.scheduler.YearScheduler;
import dev.struchkov.yandex.report.service.Accountant;
import dev.struchkov.yandex.report.service.FileReader;
import dev.struchkov.yandex.report.service.MonthService;
import dev.struchkov.yandex.report.service.Presentation;
import dev.struchkov.yandex.report.service.ReportReadService;
import dev.struchkov.yandex.report.service.YearService;
import dev.struchkov.yandex.report.service.impl.FileReaderImpl;
import dev.struchkov.yandex.report.service.impl.MonthServiceImpl;
import dev.struchkov.yandex.report.service.impl.PresentationConsole;
import dev.struchkov.yandex.report.service.impl.ReportReadMonthCsvServiceImpl;
import dev.struchkov.yandex.report.service.impl.ReportReadYearCsvServiceImpl;
import dev.struchkov.yandex.report.service.impl.SmartAccountantImpl;
import dev.struchkov.yandex.report.service.impl.YearServiceImpl;

public class Main {

    public static void main(String[] args) {
        final YearRepository yearReportRepository = new ReportYearMapRepositoryImpl();
        final MonthRepository monthReportRepository = new MonthRepositoryImpl();

        final YearService yearReportService = new YearServiceImpl(yearReportRepository);
        final MonthService monthReportService = new MonthServiceImpl(monthReportRepository);

        final FileReader fileReader = new FileReaderImpl();
        final ReportReadService<YearData> yearReportReadService = new ReportReadYearCsvServiceImpl(fileReader);
        final ReportReadService<MonthData> monthReportReadService = new ReportReadMonthCsvServiceImpl(fileReader);

        final String path = "";
        final MontScheduler montScheduler = new MontScheduler(
                path,
                "regex:.+m.\\d{6}.csv",
                monthReportService,
                monthReportReadService
        );
        final YearScheduler yearScheduler = new YearScheduler(
                path,
                "regex:.+y.\\d{4}.csv",
                yearReportService,
                yearReportReadService
        );

        final Accountant accountant = new SmartAccountantImpl(yearReportService, monthReportService);
        final Presentation presentation = new PresentationConsole();

        final ConsoleApp consoleApp = new ConsoleApp(
                yearScheduler,
                montScheduler,
                accountant,
                yearReportService,
                monthReportService,
                presentation
        );

        consoleApp.run();
    }

}
