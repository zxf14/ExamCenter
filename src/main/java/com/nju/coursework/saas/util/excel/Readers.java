package com.nju.coursework.saas.util.excel;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Created by ZhangShiqi
 */
public class Readers {

    public static <T> Reader<T> setInt(IntSetter<T> setter) {
        return (c, t) -> setter.setInt(t, (int) c.getNumericCellValue());
    }

    public static <T> Reader<T> setDouble(DoubleSetter<T> setter) {
        return (c, t) -> setter.setDouble(t, c.getNumericCellValue());
    }

    public static <T> Reader<T> setDoubleNullable(BiConsumer<T, Double> setter) {
        return (c, t) -> setter.accept(t, c.getCellTypeEnum() == CellType.BLANK ? null : c.getNumericCellValue());
    }

    public static <T> Reader<T> setBool(BoolSetter<T> setter) {
        return (c, t) -> setter.setBool(t, c.getBooleanCellValue());
    }

    public static <T> Reader<T> setString(BiConsumer<T, String> setter) {
        return (c, t) -> setter.accept(t, asStringNotNull(c));
    }

    public static <T> Reader<T> setStringNullable(BiConsumer<T, String> setter) {
        return (c, t) -> setter.accept(t, asStringNullable(c));
    }

    public static <T, E> Reader<T> map(Function<T, E> map, Reader<E> next) {
        return (c, t) -> next.accept(c, map.apply(t));
    }

    public static <T> Reader<T> doNothing() {
        return (c, t) -> {
        };
    }

    public static String asStringNotNull(XSSFCell xssfCell) {
        xssfCell.setCellType(CellType.STRING);
        return xssfCell.getStringCellValue();
    }

    public static String asStringNullable(XSSFCell xssfCell) {
        if (xssfCell.getCellTypeEnum() == CellType.BLANK) {
            return null;
        }
        xssfCell.setCellType(CellType.STRING);
        return xssfCell.getStringCellValue();
    }
}
