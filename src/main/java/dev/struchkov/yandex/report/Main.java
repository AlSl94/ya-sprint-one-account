package dev.struchkov.yandex.report;

import dev.struchkov.yandex.report.domain.MonthData;
import dev.struchkov.yandex.report.domain.YearData;
import dev.struchkov.yandex.report.domain.YearReport;
import dev.struchkov.yandex.report.repository.MonthRepository;
import dev.struchkov.yandex.report.repository.YearRepository;
import dev.struchkov.yandex.report.repository.impl.MonthRepositoryImpl;
import dev.struchkov.yandex.report.repository.impl.ReportYearMapRepositoryImpl;
import dev.struchkov.yandex.report.scheduler.MontScheduler;
import dev.struchkov.yandex.report.scheduler.YearScheduler;
import dev.struchkov.yandex.report.service.Accountant;
import dev.struchkov.yandex.report.service.FileReader;
import dev.struchkov.yandex.report.service.MonthService;
import dev.struchkov.yandex.report.service.ReportReadService;
import dev.struchkov.yandex.report.service.YearService;
import dev.struchkov.yandex.report.service.impl.FileReaderImpl;
import dev.struchkov.yandex.report.service.impl.MonthServiceImpl;
import dev.struchkov.yandex.report.service.impl.ReportReadMonthCsvServiceImpl;
import dev.struchkov.yandex.report.service.impl.ReportReadYearCsvServiceImpl;
import dev.struchkov.yandex.report.service.impl.ReportYearServiceImpl;
import dev.struchkov.yandex.report.service.impl.SmartAccountantImpl;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {

        final YearRepository yearReportRepository = new ReportYearMapRepositoryImpl();
        final MonthRepository monthReportRepository = new MonthRepositoryImpl();

        final YearService yearReportService = new ReportYearServiceImpl(yearReportRepository);
        final MonthService monthReportService = new MonthServiceImpl(monthReportRepository);

        final FileReader fileReader = new FileReaderImpl();
        final ReportReadService<YearData> yearReportReadService = new ReportReadYearCsvServiceImpl(fileReader);
        final ReportReadService<MonthData> monthReportReadService = new ReportReadMonthCsvServiceImpl(fileReader);

        final MontScheduler montScheduler = new MontScheduler(
                "/Users/upagge/IdeaProjects/yandex/sprint-one/src/main/resources/reports/",
                "regex:.+m.\\d{6}.csv",
                monthReportService,
                monthReportReadService
        );
        final YearScheduler yearScheduler = new YearScheduler(
                "/Users/upagge/IdeaProjects/yandex/sprint-one/src/main/resources/reports/",
                "regex:.+y.\\d{4}.csv",
                yearReportService,
                yearReportReadService
        );

        final Accountant accountant = new SmartAccountantImpl(yearReportService, monthReportService);

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

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
