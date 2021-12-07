package dev.struchkov.yandex.report.service;

import java.util.Collection;
import java.util.List;

public interface ReportService<T> {

    T create(T report);

    List<T> createAll(Collection<T> reports);

    List<T> updateAll(Collection<T> reports);

}
