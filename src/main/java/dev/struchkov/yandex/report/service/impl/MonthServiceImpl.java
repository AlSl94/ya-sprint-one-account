package dev.struchkov.yandex.report.service.impl;

import dev.struchkov.yandex.report.domain.MonthData;
import dev.struchkov.yandex.report.domain.MonthReport;
import dev.struchkov.yandex.report.repository.MonthRepository;
import dev.struchkov.yandex.report.service.MonthService;

import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MonthServiceImpl implements MonthService {

    private final MonthRepository repository;

    public MonthServiceImpl(MonthRepository repository) {
        this.repository = repository;
    }

    @Override
    public MonthData create(MonthData report) {
        return repository.save(report);
    }

    @Override
    public List<MonthData> createAll(Collection<MonthData> collections) {
        return collections.stream()
                .map(this::create)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MonthReport> generateMonthReport(Year year, Month month) {
        final List<MonthData> monthDataList = repository.findAllByYearAndMonth(year, month);
        if (!monthDataList.isEmpty()) {
            final MonthReport.Pair productProfit = getProductProfit(monthDataList, false);
            final MonthReport.Pair maximumSpend = getProductProfit(monthDataList, true);
            return Optional.of(new MonthReport(year, month, productProfit, maximumSpend));
        }
        return Optional.empty();
    }

    @Override
    public List<MonthReport> generateAllMonthReport() {
        final Map<Year, Set<Month>> allYear = repository.findAllYear();
        final List<MonthReport> monthReports = new ArrayList<>();
        for (Map.Entry<Year, Set<Month>> entry : allYear.entrySet()) {
            final Year year = entry.getKey();
            final Set<Month> months = entry.getValue();
            final List<MonthReport> reports = months.stream()
                    .map(month -> generateMonthReport(year, month))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            monthReports.addAll(reports);
        }
        return monthReports.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public List<MonthData> getByYear(Year year) {
        return repository.findByYear(year);
    }

    @Override
    public void clear(Year year, Month month) {
        repository.clear(year, month);
    }

    private MonthReport.Pair getProductProfit(List<MonthData> monthDataList, boolean expense) {
        return monthDataList.stream()
                .filter(monthData -> monthData.isExpense() == expense)
                .map(monthData -> new MonthReport.Pair(
                        monthData.getItemName(),
                        monthData.getSum().multiply(new BigDecimal(monthData.getQuantity()))
                ))
                .max(MonthReport.Pair::compareTo)
                .get();
    }


}
