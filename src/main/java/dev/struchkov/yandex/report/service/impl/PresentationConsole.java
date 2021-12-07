package dev.struchkov.yandex.report.service.impl;

import dev.struchkov.yandex.report.domain.MonthReport;
import dev.struchkov.yandex.report.domain.YearReport;
import dev.struchkov.yandex.report.service.Presentation;

import java.util.List;
import java.util.Scanner;

public class PresentationConsole implements Presentation {

    private final Scanner scanner = new Scanner(System.in);
    private static final String DISPLAY_MENU = "\nМеню:\n1. Считать файлы\n2. Вывести месячные отчеты\n3. Вывести годовые отчеты\n-- -- -- -- --";

    @Override
    public void displayMenu() {
        System.out.println(DISPLAY_MENU);
    }

    @Override
    public String userInput(String text) {
        System.out.print(text);
        return scanner.nextLine();
    }

    @Override
    public void showMonthReport(List<MonthReport> monthReports) {
        System.out.println("\nВсе месячные отчеты\n-- -- -- -- --");
        monthReports.forEach(System.out::println);
    }

    @Override
    public void showYearReport(List<YearReport> yearReports) {
        System.out.println("\nВсе годовые отчеты\n-- -- -- -- --");
        yearReports.forEach(System.out::println);
    }

}
