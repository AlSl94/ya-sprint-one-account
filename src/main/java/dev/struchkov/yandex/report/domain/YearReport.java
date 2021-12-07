package dev.struchkov.yandex.report.domain;

import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;
import java.util.Map;
import java.util.stream.Collectors;

public class YearReport {

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
    private final BigDecimal avgСonsumption;

    public YearReport(Year year, Map<Month, BigDecimal> profit, BigDecimal avgIncome, BigDecimal avgСonsumption) {
        this.year = year;
        this.profit = profit;
        this.avgIncome = avgIncome;
        this.avgСonsumption = avgСonsumption;
    }

    @Override
    public String toString() {
        return "Годовой отчет за " + year + ":\n" +
                "Средний доход/расход -- " + avgIncome + "/" + avgСonsumption + "\n" +
                profit.entrySet().stream()
                        .map(m -> m.getKey() + ": " + m.getValue())
                        .collect(Collectors.joining("\n"));

    }
}
