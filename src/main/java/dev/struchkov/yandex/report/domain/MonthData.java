package dev.struchkov.yandex.report.domain;

import dev.struchkov.yandex.report.utils.MonthKey;

import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;

public class MonthData {

    private Year year;
    private Month month;
    private String itemName;
    private boolean expense;
    private Long quantity;
    private BigDecimal sum;

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isExpense() {
        return expense;
    }

    public void setExpense(boolean expense) {
        this.expense = expense;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public MonthKey getMontKey() {
        return new MonthKey(month, year, expense);
    }

    @Override
    public String toString() {
        return "MonthData{" +
                "year=" + year +
                ", month=" + month +
                ", itemName='" + itemName + '\'' +
                ", expense=" + expense +
                ", quantity=" + quantity +
                ", sum=" + sum +
                '}';
    }
}
