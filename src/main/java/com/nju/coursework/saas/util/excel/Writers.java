package com.nju.coursework.saas.util.excel;

import java.util.Optional;
import java.util.function.*;

/**
 * Created by ZhangShiqi
 */
public class Writers {

    public static <T> Writer<T> getInt(ToIntFunction<T> getter) {
        return (c, t) -> c.setCellValue(getter.applyAsInt(t));
    }

    public static <T> Writer<T> getInt(Function<T, Integer> getter) {
        return (c, t) -> Optional.ofNullable(t).map(getter).ifPresent(c::setCellValue);
    }

    public static <T> Writer<T> getDouble(ToDoubleFunction<T> getter) {
        return (c, t) -> c.setCellValue(getter.applyAsDouble(t));
    }

    public static <T> Writer<T> getDouble(Function<T, Double> getter) {
        return (c, t) -> Optional.ofNullable(t).map(getter).ifPresent(c::setCellValue);
    }

    public static <T> Writer<T> getLong(ToLongFunction<T> getter) {
        return (c, t) -> c.setCellValue(getter.applyAsLong(t));
    }

    public static <T> Writer<T> getLong(Function<T, Long> getter) {
        return (c, t) -> Optional.ofNullable(t).map(getter).ifPresent(c::setCellValue);
    }

    public static <T> Writer<T> getBool(Predicate<T> getter) {
        return (c, t) -> c.setCellValue(getter.test(t));
    }

    public static <T> Writer<T> getBool(Function<T, Boolean> getter) {
        return (c, t) -> Optional.ofNullable(t).map(getter).ifPresent(c::setCellValue);
    }

    public static <T> Writer<T> getString(Function<T, String> getter) {
        return (c, t) -> Optional.ofNullable(t).map(getter).ifPresent(c::setCellValue);
    }

    public static <T, E> Writer<T> map(Function<T, E> map, Writer<E> next) {
        return (c, t) -> next.accept(c, map.apply(t));
    }
}
