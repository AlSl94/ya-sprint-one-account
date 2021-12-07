package dev.struchkov.yandex.report.repository.impl;

import dev.struchkov.yandex.report.domain.MonthData;
import dev.struchkov.yandex.report.repository.MonthRepository;
import dev.struchkov.yandex.report.utils.MonthKey;

import java.time.Month;
import java.time.Year;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MonthRepositoryImpl implements MonthRepository {

    private final Map<MonthKey, MonthData> map = new HashMap<>();

    @Override
    public MonthData save(MonthData monthData) {
        map.put(monthData.getMontKey(), monthData);
        return monthData;
    }

    @Override
    public List<MonthData> saveAll(Collection<MonthData> collection) {
        return collection.stream()
                .map(this::save)
                .toList();
    }

    @Override
    public List<MonthData> findAll() {
        return map.values().stream().toList();
    }

    @Override
    public List<MonthData> findAllByYearAndMonth(Year year, Month month) {
        return map.keySet().stream()
                .filter(monthKey -> monthKey.getYear().equals(year) && monthKey.getMonth().equals(month))
                .map(map::get)
                .toList();
    }

    @Override
    public Map<Year, Set<Month>> findAllYear() {
        return map.keySet().stream()
                .collect(Collectors.groupingBy(MonthKey::getYear, Collectors.toSet()))
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue().stream()
                                        .map(MonthKey::getMonth)
                                        .collect(Collectors.toUnmodifiableSet())
                        )
                );
    }

    @Override
    public List<MonthData> findByYear(Year year) {
        return map.entrySet().stream()
                .filter(e -> year.equals(e.getKey().getYear()))
                .map(Map.Entry::getValue)
                .toList();
    }

}
