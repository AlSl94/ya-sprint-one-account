package dev.struchkov.yandex.report.repository.impl;

import dev.struchkov.yandex.report.domain.YearData;
import dev.struchkov.yandex.report.repository.YearRepository;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ReportYearMapRepositoryImpl implements YearRepository {

    private final Map<Year, List<YearData>> map = new HashMap<>();

    @Override
    public YearData save(YearData yearData) {
        final Year year = yearData.getYear();
        map.computeIfAbsent(year, y -> new ArrayList<>());
        map.get(year).add(yearData);
        return yearData;
    }

    @Override
    public List<YearData> saveAll(Collection<YearData> collection) {
        return collection.stream()
                .map(this::save)
                .collect(Collectors.toList());
    }

    @Override
    public List<YearData> findAll() {
        return map.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<YearData> findByYear(Year year) {
        if (map.containsKey(year)) {
            return map.get(year);
        }
        return Collections.emptyList();
    }

    @Override
    public Set<Year> findAllYear() {
        return map.keySet();
    }

    @Override
    public void clear(Year year) {
        map.remove(year);
    }

}
