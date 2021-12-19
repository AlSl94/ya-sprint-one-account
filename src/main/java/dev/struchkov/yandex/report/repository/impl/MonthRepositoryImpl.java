package dev.struchkov.yandex.report.repository.impl;

import dev.struchkov.yandex.report.domain.MonthData;
import dev.struchkov.yandex.report.repository.MonthRepository;
import dev.struchkov.yandex.report.utils.YearAndMonth;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MonthRepositoryImpl implements MonthRepository {

    private final Map<YearAndMonth, List<MonthData>> map = new HashMap<>();

    @Override
    public MonthData save(MonthData monthData) {
        final YearAndMonth key = monthData.getYearAndMonth();
        map.computeIfAbsent(key, k-> new ArrayList<>());
        map.get(key).add(monthData);
        return monthData;
    }

    @Override
    public List<MonthData> saveAll(Collection<MonthData> collection) {
        return collection.stream()
                .map(this::save)
                .collect(Collectors.toList());
    }

    @Override
    public List<MonthData> findAll() {
        return map.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<MonthData> findAllByYearAndMonth(Year year, Month month) {
        final YearAndMonth key = new YearAndMonth(year, month);
        if (map.containsKey(key)) {
            return map.get(key);
        }
        return Collections.emptyList();
    }

    @Override
    public Map<Year, Set<Month>> findAllYear() {
        return map.keySet().stream()
                .collect(Collectors.groupingBy(YearAndMonth::getYear, Collectors.toSet()))
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue().stream()
                                        .map(YearAndMonth::getMonth)
                                        .collect(Collectors.toUnmodifiableSet())
                        )
                );
    }

    @Override
    public List<MonthData> findByYear(Year year) {
        return map.entrySet().stream()
                .filter(e -> year.equals(e.getKey().getYear()))
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public void clear(Year year, Month month) {
        map.remove(new YearAndMonth(year, month));
    }

}
