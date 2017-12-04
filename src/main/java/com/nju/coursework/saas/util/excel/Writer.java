package com.nju.coursework.saas.util.excel;

import org.apache.poi.xssf.usermodel.XSSFCell;

import java.util.function.BiConsumer;

/**
 * Created by ZhangShiqi
 */
public interface Writer<T> extends BiConsumer<XSSFCell, T> {
}
