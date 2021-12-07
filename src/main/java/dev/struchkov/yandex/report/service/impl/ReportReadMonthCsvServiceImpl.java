package dev.struchkov.yandex.report.service.impl;

import dev.struchkov.yandex.report.domain.MonthData;
import dev.struchkov.yandex.report.service.FileReader;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public class ReportReadMonthCsvServiceImpl extends AbstractReportCsvReadService<MonthData> {

    public ReportReadMonthCsvServiceImpl(FileReader fileReader) {
        super(fileReader);
    }

    public ReportReadMonthCsvServiceImpl(FileReader fileReader, String lineBreak, String comma) {
        super(fileReader, lineBreak, comma);
    }

    @Override
    public List<MonthData> readReport(Path filePath) {
        final Function<String[], MonthData> mounthFunction = item -> {
            final MonthData monthData = new MonthData();
            monthData.setItemName(item[0]);
            monthData.setExpense(Boolean.parseBoolean(item[1]));
            monthData.setQuantity(Long.parseLong(item[2]));
            monthData.setSum(new BigDecimal(item[3]));
            return monthData;
        };
        return getReport(filePath, mounthFunction);
    }
}
