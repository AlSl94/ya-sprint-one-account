package dev.struchkov.yandex.report.service;

import java.time.Month;
import java.util.Set;

public interface Accountant {

    /**
     * <p>Сверка данных.</p>
     * <p>Сверка данных — это проверка, что данные в двух и более разных источниках не противоречат друг другу. В данном случае при сверке данных вам нужно проверить, что информация по месяцу в годовом отчёте не противоречит информации в месячном отчёте.</p>
     */
    Set<Month> dataReconciliation(int year);

}
