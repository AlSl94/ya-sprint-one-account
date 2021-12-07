package dev.struchkov.yandex.report.service.impl;

import dev.struchkov.yandex.report.domain.YearData;
import dev.struchkov.yandex.report.domain.YearReport;
import dev.struchkov.yandex.report.repository.YearRepository;
import dev.struchkov.yandex.report.service.YearService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.time.Year;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ReportYearServiceImpl implements YearService {

    public final YearRepository repository;

    public ReportYearServiceImpl(YearRepository repository) {
        this.repository = repository;
    }

    @Override
    public YearData create(YearData report) {
        return repository.save(report);
    }

    @Override
    public List<YearData> createAll(Collection<YearData> reports) {
        return reports.stream()
                .map(this::create)
                .toList();
    }

    @Override
    public List<YearData> updateAll(Collection<YearData> reports) {
        return createAll(reports);
    }

    @Override
    public List<YearData> getByYear(Year year) {
        return repository.findByYear(year);
    }

    @Override
    public YearReport generateYearReport(Year year) {
        final List<YearData> years = repository.findByYear(year);
        final BigDecimal spending = calculateAvg(years, true);
        final BigDecimal income = calculateAvg(years, false);
        final Map<Month, BigDecimal> profit = years.stream()
                .collect(Collectors.groupingBy(YearData::getMonth))
                .entrySet()
                .stream()
                .map(
                        entry -> {
                            final Month month = entry.getKey();
                            final List<YearData> values = entry.getValue();
                            final BigDecimal spen = calculateSum(values, true);
                            final BigDecimal inc = calculateSum(values, false);
                            final BigDecimal result = inc.subtract(spen);
                            return new Pair(month, result);
                        }
                ).collect(Collectors.toMap(
                        Pair::getMonth, Pair::getProfit
                ));
        return new YearReport(year, profit, income, spending);
    }

    @Override
    public List<YearReport> generateAllYearReport() {
        final Set<Year> years = repository.findAllYear();
        return years.stream()
                .map(this::generateYearReport)
                .toList();
    }

    public BigDecimal calculateAvg(List<YearData> yearDataList, boolean expense) {
        final List<BigDecimal> spending = yearDataList.stream()
                .filter(yearData -> yearData.isExpense() == expense)
                .map(YearData::getAmount)
                .map(Objects::requireNonNull)
                .toList();
        return spending.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(new BigDecimal(spending.size()), RoundingMode.valueOf(2));
    }

    public BigDecimal calculateSum(List<YearData> years, boolean expense) {
        return years.stream()
                .filter(yearData -> yearData.isExpense() == expense)
                .map(YearData::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static class Pair {
        private final Month month;
        private final BigDecimal profit;

        private Pair(Month month, BigDecimal profit) {
            this.month = month;
            this.profit = profit;
        }

        public Month getMonth() {
            return month;
        }

        public BigDecimal getProfit() {
            return profit;
        }

    }
}
