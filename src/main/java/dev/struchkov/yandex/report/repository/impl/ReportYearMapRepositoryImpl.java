package dev.struchkov.yandex.report.repository.impl;

import dev.struchkov.yandex.report.domain.YearData;
import dev.struchkov.yandex.report.repository.YearRepository;
import dev.struchkov.yandex.report.utils.MonthKey;

import java.time.Year;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ReportYearMapRepositoryImpl implements YearRepository {

    private final Map<MonthKey, YearData> map = new HashMap<>();

    @Override
    public YearData save(YearData yearData) {
        map.put(yearData.getMontKey(), yearData);
        return yearData;
    }

    @Override
    public List<YearData> saveAll(Collection<YearData> collection) {
        return collection.stream()
                .map(this::save)
                .toList();
    }

    @Override
    public List<YearData> findAll() {
        return map.values().stream().toList();
    }

    @Override
    public List<YearData> findByYear(Year year) {
        return map.keySet().stream()
                .filter(monthKey -> monthKey.getYear().equals(year))
                .map(map::get)
                .toList();
    }

    @Override
    public Set<Year> findAllYear() {
        return map.keySet()
                .stream().map(MonthKey::getYear)
                .collect(Collectors.toUnmodifiableSet());
    }

}
