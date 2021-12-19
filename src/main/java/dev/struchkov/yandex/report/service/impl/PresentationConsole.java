package dev.struchkov.yandex.report.service.impl;

import dev.struchkov.yandex.report.domain.MonthReport;
import dev.struchkov.yandex.report.domain.YearReport;
import dev.struchkov.yandex.report.service.Presentation;

import java.time.Month;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class PresentationConsole implements Presentation {

    private final Scanner scanner = new Scanner(System.in);
    private static final String DISPLAY_MENU = "\nМеню:\n" +
            "1. Считать файлы\n" +
            "2. Считывать файлы автоматически\n" +
            "3. Вывести месячные отчеты\n" +
            "4. Вывести годовые отчеты\n" +
            "5. Сверка отчета\n" +
            "-- -- -- -- --";

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

    @Override
    public void showResultDataReconciliation(Set<Month> months) {
        if (months.isEmpty()) {
            System.out.println("-- -- -- -- --");
            System.out.println("Оператция успешно завершена!");
            System.out.println("-- -- -- -- --");
        } else {
            System.out.println("-- -- -- -- --");
            System.out.println("Обнаружены ошибки в следующих месяцах:");
            final String monthText = months.stream()
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
            System.out.println(monthText);
            System.out.println("-- -- -- -- --");
        }
    }

}
