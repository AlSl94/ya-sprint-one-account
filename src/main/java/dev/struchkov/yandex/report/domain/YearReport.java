package dev.struchkov.yandex.report.domain;

import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;
import java.util.Map;
import java.util.stream.Collectors;

public class YearReport implements Comparable<YearReport> {

    /**
     * Год
     */
    private final Year year;

    /**
     * Прибыль по каждому месяцу.
     */
    private final Map<Month, BigDecimal> profit;

    /**
     * Средний доход
     */
    private final BigDecimal avgIncome;

    /**
     * Средний расход
     */
    private final BigDecimal avgConsumption;

    public YearReport(Year year, Map<Month, BigDecimal> profit, BigDecimal avgIncome, BigDecimal avgConsumption) {
        this.year = year;
        this.profit = profit;
        this.avgIncome = avgIncome;
        this.avgConsumption = avgConsumption;
    }

    @Override
    public String toString() {
        return "Годовой отчет за " + year + ":\n" +
                "Средний доход/расход -- " + avgIncome + "/" + avgConsumption + "\n" +
                profit.entrySet().stream()
                        .map(m -> m.getKey() + ": " + m.getValue())
                        .collect(Collectors.joining("\n"));

    }

    @Override
    public int compareTo(YearReport o) {
        return this.year.compareTo(o.year);
    }

}
