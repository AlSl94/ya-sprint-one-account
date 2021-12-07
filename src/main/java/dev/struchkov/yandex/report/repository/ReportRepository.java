package dev.struchkov.yandex.report.repository;

import java.util.Collection;
import java.util.List;

public interface ReportRepository<T> {

    T save(T year);

    List<T> saveAll(Collection<T> collection);

    List<T> findAll();

}
