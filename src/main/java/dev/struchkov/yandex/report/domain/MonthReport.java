package dev.struchkov.yandex.report.domain;

import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;
import java.util.List;

public class MonthReport implements Comparable<MonthReport> {

    private final Year year;
    private final Month month;
    private final Pair productProfit;
    private final Pair bigWaste;

    public MonthReport(Year year, Month month, Pair productProfit, Pair bigWaste) {
        this.year = year;
        this.month = month;
        this.productProfit = productProfit;
        this.bigWaste = bigWaste;
    }

    public Year getYear() {
        return year;
    }

    public Month getMonth() {
        return month;
    }

    @Override
    public int compareTo(MonthReport o) {
        final int yearCompare = this.getYear().compareTo(o.getYear());
        final int monthCompare = this.getMonth().compareTo(o.getMonth());
        if (yearCompare < 0) {
            return -1;
        } else if (yearCompare > 0) {
            return 1;
        } else {
            return monthCompare;
        }
    }

    public static class Pair implements Comparable<Pair> {
        private final String name;
        private final BigDecimal sum;

        public Pair(String name, BigDecimal sum) {
            this.name = name;
            this.sum = sum;
        }

        public String getName() {
            return name;
        }

        public BigDecimal getSum() {
            return sum;
        }

        @Override
        public int compareTo(Pair o) {
            return sum.compareTo(o.sum);
        }
    }

    @Override
    public String toString() {
        return "Отчет за " + this.month + " " + this.year + ":\n" +
                "Самый прибыльный товар: " + productProfit.name + " -- " + productProfit.sum + "\n" +
                "Самая большая трата: " + bigWaste.name + " -- " + bigWaste.sum + "\n";
    }
}
