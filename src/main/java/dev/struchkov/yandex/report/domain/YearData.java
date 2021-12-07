package dev.struchkov.yandex.report.domain;

import dev.struchkov.yandex.report.utils.MonthKey;

import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;

public class YearData {

    private Year year;
    private Month month;
    private BigDecimal amount;
    private boolean expense;

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isExpense() {
        return expense;
    }

    public void setExpense(boolean expense) {
        this.expense = expense;
    }

    public MonthKey getMontKey() {
        return new MonthKey(month, year, expense);
    }

    @Override
    public String toString() {
        return "YearData{" +
                "year=" + year +
                ", month=" + month +
                ", amount=" + amount +
                ", expense=" + expense +
                '}';
    }
}
