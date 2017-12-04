package com.nju.coursework.saas.util.excel;

import lombok.Getter;

/**
 * Created by ZhangShiqi
 */
@Getter
public class Column<T> {

    private final String header;
    private final Writer<T> writer;
    private final Reader<T> reader;

    public Column(String header, Writer<T> writer, Reader<T> reader) {
        this.header = header;
        this.writer = writer;
        this.reader = reader;
    }

    public static <T> Column<T> c(String header, Writer<T> writer, Reader<T> reader) {
        return new Column<>(header, writer, reader);
    }

    public static <T> Column<T> c(String header, Writer<T> writer) {
        return Column.c(header, writer, Readers.doNothing());
    }

    @Override
    public String toString() {
        return "Column<" + header + '>';
    }

}