package org.reactivetoolbox.core.lang.support;

import org.reactivetoolbox.core.lang.Functions.FN0;
import org.reactivetoolbox.core.lang.Functions.FN1;
import org.reactivetoolbox.core.lang.List;
import org.reactivetoolbox.core.lang.Option;
import org.reactivetoolbox.core.lang.Result;

import java.util.Arrays;

import static org.reactivetoolbox.core.lang.Option.option;
import static org.reactivetoolbox.core.lang.Result.success;

public interface Sequence<T> extends FN0<Result<Option<T>>> {
    Result<Option<T>> next();

    default Result<Option<T>> apply() {
        return next();
    }

    default <R> Sequence<R> map(final FN1<R, T> valueMapper) {
        return () -> next().map(o -> o.map(valueMapper));
    }

    default <R> Sequence<R> flatMap(final FN1<Option<R>, T> valueMapper) {
        return () -> next().map(o -> o.flatMap(valueMapper));
    }

    default Result<List<T>> collect() {
        return collect(list -> list);
    }

    default <R> Result<R> collect(final FN1<R, List<T>> resultTransformer) {
        final var builder = List.<T>builder();

        Result<Option<T>> lastValue;

        while ((lastValue = next()).map(v -> false,
                                        element -> element.map(v -> false,
                                                               value -> {
                                                                   builder.append(value);
                                                                   return true;
                                                               }))) {
            //Loop body is intentionally empty
        }

        return lastValue.map(v -> resultTransformer.apply(builder.build()));
    }

    static <T> Sequence<T> sequence(final T... elements) {
        final var iterator = Arrays.asList(elements).iterator();
        return () -> success(option(iterator.hasNext() ? iterator.next() : null));
    }

    static <T> Sequence<T> empty() {
        return () -> success(Option.empty());
    }
}
