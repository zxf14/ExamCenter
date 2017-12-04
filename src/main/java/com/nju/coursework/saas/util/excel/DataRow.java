package com.nju.coursework.saas.util.excel;

import lombok.NonNull;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.RandomAccess;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * Created by ZhangShiqi
 */
public class DataRow<T> {

    private final List<Column<T>> columns;
    private final Supplier<T> supplier;
    private final int columnCount;

    public DataRow(@NonNull List<Column<T>> _columns, @NonNull Supplier<T> _supplier) {
        columns = _columns instanceof RandomAccess ? _columns : new ArrayList<>(_columns);
        supplier = _supplier;
        columnCount = columns.size();
    }

    @SafeVarargs
    public static <T> DataRow<T> r(Supplier<T> supplier, Column<T>... columns) {
        return new DataRow<>(Arrays.asList(columns), supplier);
    }

    public static <T> DataRow<T> r(Supplier<T> supplier, List<Column<T>> columns) {
        return new DataRow<>(columns, supplier);
    }

    public static <T> Supplier<T> neverUse() {
        return () -> {
            throw new AssertionError();
        };
    }

    public List<T> read(XSSFSheet sheet) {
        return read(sheet, any -> true);
    }

    public List<T> readNotEmpty(XSSFSheet sheet) {
        return read(sheet, row -> {
            for (int i = 0; i < columnCount; i++) {
                if (row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellTypeEnum() != CellType.BLANK) {
                    return true;
                }
            }
            return false;
        });
    }

    public List<T> read(XSSFSheet sheet, Predicate<XSSFRow> process) {
        return StreamSupport.stream(sheet.spliterator(), false)
                .skip(1)
                .map(r -> (XSSFRow) r)
                .filter(process)
                .map(row -> {
                    T t = supplier.get();
                    for (int i = 0; i < columnCount; i++) {
                        columns.get(i).getReader().accept(row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK), t);
                    }
                    return t;
                })
                .collect(Collectors.toList());
    }

    public void write(XSSFSheet sheet, List<T> list) {
        XSSFRow header = sheet.createRow(0);
        for (int i = 0; i < columnCount; i++) {
            header.createCell(i).setCellValue(columns.get(i).getHeader());
        }
        int index = 1;
        for (T t : list) {
            XSSFRow row = sheet.createRow(index);
            for (int i = 0; i < columnCount; i++) {
                columns.get(i).getWriter().accept(row.createCell(i), t);
            }
            index++;
        }
    }
}
