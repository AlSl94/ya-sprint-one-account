package dev.struchkov.yandex.report.utils;

import java.time.Month;
import java.time.Year;
import java.util.Objects;

public class MonthKey {

    private final Month month;
    private final Year year;
    private final boolean expense;

    public MonthKey(Month month, Year year, boolean expense) {
        this.month = month;
        this.year = year;
        this.expense = expense;
    }

    public Month getMonth() {
        return month;
    }

    public Year getYear() {
        return year;
    }

    public boolean isExpense() {
        return expense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthKey monthKey = (MonthKey) o;
        return expense == monthKey.expense && month == monthKey.month && year.equals(monthKey.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, year, expense);
    }

}
