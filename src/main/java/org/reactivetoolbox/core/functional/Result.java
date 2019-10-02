package org.reactivetoolbox.core.functional;

import org.reactivetoolbox.core.async.BaseError;
import org.reactivetoolbox.core.functional.Functions.FN0;
import org.reactivetoolbox.core.functional.Functions.FN1;
import org.reactivetoolbox.core.functional.Functions.FN2;
import org.reactivetoolbox.core.functional.Functions.FN3;
import org.reactivetoolbox.core.functional.Functions.FN4;
import org.reactivetoolbox.core.functional.Functions.FN5;
import org.reactivetoolbox.core.functional.Functions.FN6;
import org.reactivetoolbox.core.functional.Functions.FN7;
import org.reactivetoolbox.core.functional.Functions.FN8;
import org.reactivetoolbox.core.functional.Functions.FN9;
import org.reactivetoolbox.core.functional.Tuples.Tuple1;
import org.reactivetoolbox.core.functional.Tuples.Tuple2;
import org.reactivetoolbox.core.functional.Tuples.Tuple3;
import org.reactivetoolbox.core.functional.Tuples.Tuple4;
import org.reactivetoolbox.core.functional.Tuples.Tuple5;
import org.reactivetoolbox.core.functional.Tuples.Tuple6;
import org.reactivetoolbox.core.functional.Tuples.Tuple7;
import org.reactivetoolbox.core.functional.Tuples.Tuple8;
import org.reactivetoolbox.core.functional.Tuples.Tuple9;

import java.util.function.Consumer;

/**
 * Representation of the operation result. The result can be either success or failure.
 * In case of success it holds value returned by the operation. In case of error it
 * holds an error which describes the failure.
 *
 * @param <T>
 *     Type of value in case of successful result.
 */
public interface Result<T> extends Either<BaseError, T> {

    /**
     * Create an instance of successful operation result.
     *
     * @param value
 *            Operation result
     * @return created instance
     */
    static <R> Result<R> success(final R value) {
        return new Result<>() {
            @Override
            public <T> T map(final FN1<? extends T, ? super BaseError> leftMapper,
                             final FN1<? extends T, ? super R> rightMapper) {
                return rightMapper.apply(value);
            }
        };
    }

    /**
     * Create an instance of failure operation result.
     * @param value
     *        Operation error value
     * @return created instance
     */
    static <R> Result<R> failure(final BaseError value) {
        return new Result<>() {
            @Override
            public <T> T map(final FN1<? extends T, ? super BaseError> leftMapper,
                             final FN1<? extends T, ? super R> rightMapper) {
                return leftMapper.apply(value);
            }
        };
    }

    /**
     * Transform operation result into another operation result. In case if current
     * instance (this) is an error, transformation function is not invoked
     * and value remains the same.
     *
     * @param mapper
     *        Function to apply to result
     * @return transformed value (in case of success) or current instance (in case of failure)
     */
    @SuppressWarnings("unchecked")
    default <R> Result<R> flatMap(final FN1<Result<R>, T> mapper) {
        return map(t -> (Result<R>) this, mapper);
    }

    /**
     * Transform operation result value into value of other type and wrap new
     * value into {@link Result}. Transformation takes place if current instance
     * (this) contains successful result, otherwise current instance remains
     * unchanged and transformation function is not invoked.
     *
     * @param mapper
     *        Function to transform successful value
     * @return transformed value (in case of success) or current instance (in case of failure)
     */
    @SuppressWarnings("unchecked")
    default <R> Result<R> map(final FN1<R, T> mapper) {
        return map(l -> (Result<R>) this, r -> success(mapper.apply(r)));
    }

    /**
     * Combine current instance with another result. If current instance holds
     * success then result is equivalent to current instance, otherwise other
     * instance (passed as {@code replacement} parameter) is returned.
     *
     * @param replacement
     *        Value to return if current instance contains failure operation result
     * @return current instance in case of success or replacement instance in case of failure.
     */
    default Result<T> or(final Result<T> replacement) {
        return map(t -> replacement, t -> this);
    }

    /**
     * Combine current instance with another result. If current instance holds
     * success then result is equivalent to current instance, otherwise other
     * instance (retrieved by calling to {@code replacementFunction}) is returned.
     *
     * @param replacementFunction
     *        Function which returns value if current instance contains failure operation result
     * @return current instance in case of success or result of replacementFunction call in case of failure.
     */
    default Result<T> or(final FN0<Result<T>> replacementFunction) {
        return map(t -> replacementFunction.apply(), t -> this);
    }

    /**
     * Pass successful operation result value into provided consumer.
     *
     * @param consumer
     *        Consumer to pass value to
     * @return current instance for fluent call chaining
     */
    default Result<T> ifSuccess(final Consumer<T> consumer) {
        map(t1 -> t1, t1 -> { consumer.accept(t1); return null; });
        return this;
    }

    /**
     * Pass failure operation result value into provided consumer.
     *
     * @param consumer
     *        Consumer to pass value to
     * @return current instance for fluent call chaining
     */
    default Result<T> ifFailure(final Consumer<? super BaseError> consumer) {
        map(t1 -> { consumer.accept(t1); return null; }, t1 -> t1);
        return this;
    }

    /**
     * Convenience method to transform instance of {@link Result} which holds {@link Tuple1}
     *
     * @param result
     *         Input instance
     * @param mapper
     *         Mapping function
     *
     * @return transformed instance of {@link Result}
     */
    static <R, T1> Result<R> map(final Result<Tuple1<T1>> result,
                                 final FN1<R, T1> mapper) {
        return result.map(tuple -> tuple.map(mapper));
    }

    /**
     * Convenience method to transform instance of {@link Result} which holds {@link Tuple2}
     *
     * @param result
     *         Input instance
     * @param mapper
     *         Mapping function
     *
     * @return transformed instance of {@link Result}
     */
    static <R, T1, T2> Result<R> map(final Result<Tuple2<T1, T2>> result,
                                     final FN2<R, T1, T2> mapper) {
        return result.map(tuple -> tuple.map(mapper));
    }

    /**
     * Convenience method to transform instance of {@link Result} which holds {@link Tuple3}
     *
     * @param result
     *         Input instance
     * @param mapper
     *         Mapping function
     *
     * @return transformed instance of {@link Result}
     */
    static <R, T1, T2, T3> Result<R> map(final Result<Tuple3<T1, T2, T3>> result,
                                         final FN3<R, T1, T2, T3> mapper) {
        return result.map(tuple -> tuple.map(mapper));
    }

    /**
     * Convenience method to transform instance of {@link Result} which holds {@link Tuple4}
     *
     * @param result
     *         Input instance
     * @param mapper
     *         Mapping function
     *
     * @return transformed instance of {@link Result}
     */
    static <R, T1, T2, T3, T4> Result<R> map(final Result<Tuple4<T1, T2, T3, T4>> result,
                                             final FN4<R, T1, T2, T3, T4> mapper) {
        return result.map(tuple -> tuple.map(mapper));
    }

    /**
     * Convenience method to transform instance of {@link Result} which holds {@link Tuple5}
     *
     * @param result
     *         Input instance
     * @param mapper
     *         Mapping function
     *
     * @return transformed instance of {@link Result}
     */
    static <R, T1, T2, T3, T4, T5> Result<R> map(final Result<Tuple5<T1, T2, T3, T4, T5>> result,
                                                 final FN5<R, T1, T2, T3, T4, T5> mapper) {
        return result.map(tuple -> tuple.map(mapper));
    }

    /**
     * Convenience method to transform instance of {@link Result} which holds {@link Tuple6}
     *
     * @param result
     *         Input instance
     * @param mapper
     *         Mapping function
     *
     * @return transformed instance of {@link Result}
     */
    static <R, T1, T2, T3, T4, T5, T6> Result<R> map(final Result<Tuple6<T1, T2, T3, T4, T5, T6>> result,
                                                     final FN6<R, T1, T2, T3, T4, T5, T6> mapper) {
        return result.map(tuple -> tuple.map(mapper));
    }

    /**
     * Convenience method to transform instance of {@link Result} which holds {@link Tuple7}
     *
     * @param result
     *         Input instance
     * @param mapper
     *         Mapping function
     *
     * @return transformed instance of {@link Result}
     */
    static <R, T1, T2, T3, T4, T5, T6, T7> Result<R> map(final Result<Tuple7<T1, T2, T3, T4, T5, T6, T7>> result,
                                                         final FN7<R, T1, T2, T3, T4, T5, T6, T7> mapper) {
        return result.map(tuple -> tuple.map(mapper));
    }

    /**
     * Convenience method to transform instance of {@link Result} which holds {@link Tuple8}
     *
     * @param result
     *         Input instance
     * @param mapper
     *         Mapping function
     *
     * @return transformed instance of {@link Result}
     */
    static <R, T1, T2, T3, T4, T5, T6, T7, T8> Result<R> map(final Result<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> result,
                                                             final FN8<R, T1, T2, T3, T4, T5, T6, T7, T8> mapper) {
        return result.map(tuple -> tuple.map(mapper));
    }

    /**
     * Convenience method to transform instance of {@link Result} which holds {@link Tuple9}
     *
     * @param result
     *         Input instance
     * @param mapper
     *         Mapping function
     *
     * @return transformed instance of {@link Result}
     */
    static <R, T1, T2, T3, T4, T5, T6, T7, T8, T9> Result<R> map(final Result<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> result,
                                                                 final FN9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> mapper) {
        return result.map(tuple -> tuple.map(mapper));
    }
}