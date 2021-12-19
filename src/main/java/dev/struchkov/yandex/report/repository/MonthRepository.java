package dev.struchkov.yandex.report.repository;

import dev.struchkov.yandex.report.domain.MonthData;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MonthRepository extends ReportRepository<MonthData> {

    List<MonthData> findAllByYearAndMonth(Year year, Month month);

    Map<Year, Set<Month>> findAllYear();

    List<MonthData> findByYear(Year year);

    void clear(Year year, Month month);

}
