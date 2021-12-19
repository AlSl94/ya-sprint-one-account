package dev.struchkov.yandex.report.utils;

import java.time.Month;
import java.time.Year;
import java.util.Objects;

public class YearAndMonth {

    private final Year year;
    private final Month month;

    public YearAndMonth(Year year, Month month) {
        this.month = month;
        this.year = year;
    }

    public Month getMonth() {
        return month;
    }

    public Year getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YearAndMonth yearAndMonth = (YearAndMonth) o;
        return month == yearAndMonth.month && Objects.equals(year, yearAndMonth.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, year);
    }

}
