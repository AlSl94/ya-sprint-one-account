package dev.struchkov.yandex.report.service.impl;

import dev.struchkov.yandex.report.domain.YearData;
import dev.struchkov.yandex.report.service.FileReader;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.Month;
import java.util.List;
import java.util.function.Function;

public class ReportReadYearCsvServiceImpl extends AbstractReportCsvReadService<YearData> {

    public ReportReadYearCsvServiceImpl(FileReader fileReader) {
        super(fileReader);
    }

    public ReportReadYearCsvServiceImpl(FileReader fileReader, String lineBreak, String comma) {
        super(fileReader, lineBreak, comma);
    }

    @Override
    public List<YearData> readReport(Path filePath) {
        final Function<String[], YearData> yearFunction = item -> {
            final YearData yearData = new YearData();
            yearData.setMonth(Month.of(Integer.parseInt(item[0])));
            yearData.setAmount(new BigDecimal(item[1]));
            yearData.setExpense(Boolean.parseBoolean(item[2]));
            return yearData;
        };
        return getReport(filePath, yearFunction);
    }
}
