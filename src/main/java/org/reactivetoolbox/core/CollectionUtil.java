package org.reactivetoolbox.core;

import org.reactivetoolbox.core.functional.Functions.FN1;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface CollectionUtil {
    static <R, T> List<R> map(final List<T> input, final FN1<R, T> mapper) {
        return input.stream().map(mapper::apply).collect(Collectors.toList());
    }

    static <R, T> Set<R> map(final Set<T> input, final FN1<R, T> mapper) {
        return input.stream().map(mapper::apply).collect(Collectors.toSet());
    }
}
