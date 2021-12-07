package dev.struchkov.yandex.report.service.impl;

import dev.struchkov.yandex.report.domain.MonthData;
import dev.struchkov.yandex.report.domain.YearData;
import dev.struchkov.yandex.report.service.Accountant;
import dev.struchkov.yandex.report.service.MonthService;
import dev.struchkov.yandex.report.service.YearService;

import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SmartAccountantImpl implements Accountant {

    private final YearService yearService;
    private final MonthService monthService;

    public SmartAccountantImpl(YearService yearService, MonthService monthService) {
        this.yearService = yearService;
        this.monthService = monthService;
    }

    @Override
    public void dataReconciliation(int year) {
        final Year javaYear = Year.of(year);
        final List<YearData> years = yearService.getByYear(javaYear);
        final Map<Month, List<MonthData>> expense = monthService.getByYear(javaYear).stream()
                .filter(MonthData::isExpense)
                .collect(Collectors.groupingBy(MonthData::getMonth));
        final Map<Month, List<MonthData>> notExpense = monthService.getByYear(javaYear).stream()
                .filter(monthData -> !monthData.isExpense())
                .collect(Collectors.groupingBy(MonthData::getMonth));
        for (YearData yearData : years) {
            final List<MonthData> expenseMonthList = expense.get(yearData.getMonth());
            final List<MonthData> notExpenseMonthList = notExpense.get(yearData.getMonth());
            final BigDecimal expenseMonth = expenseMonthList.stream()
                    .map(MonthData::getSum)
                    .reduce(BigDecimal::add)
                    .get();
            final BigDecimal notExpenseMonth = notExpenseMonthList.stream()
                    .map(MonthData::getSum)
                    .reduce(BigDecimal::add)
                    .get();
            if (yearData.isExpense()) {
                System.out.println(yearData.getAmount().equals(expenseMonth));
            } else {
                System.out.println(yearData.getAmount().equals(notExpenseMonth));
            }
        }
    }

}
