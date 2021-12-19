package dev.struchkov.yandex.report.repository;

import dev.struchkov.yandex.report.domain.YearData;

import java.time.Year;
import java.util.List;
import java.util.Set;

public interface YearRepository extends ReportRepository<YearData> {

    List<YearData> findByYear(Year year);

    Set<Year> findAllYear();

    void clear(Year year);

}
