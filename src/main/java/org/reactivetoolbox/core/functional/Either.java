package org.reactivetoolbox.core.functional;

/*
 * Copyright (c) 2019 Sergiy Yevtushenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Convenience type for use in cases when function may return either result of successful execution or failure.
 *
 * @param <F>
 *        failure type
 * @param <S>
 *        success type
 */
public interface Either<F, S> {
    /**
     * Return <code>true</code> if instance holds failure value.
     */
    boolean isFailure();

    /**
     * Return <code>true</code> if instance holds success value.
     */
    boolean isSuccess();

    /**
     * Get access to success value wrapped into {@link Option}.
     */
    Option<S> success();

    /**
     * Get access to failure wrapped into {@link Option}.
     */
    Option<F> failure();

    /**
     * Transform given instance into another, using provided mapper.
     * Transformation takes place only if current instance contains success.
     *
     * @param mapper
     *        Transformation to apply
     * @param <NF>
     *        New type for failure
     * @param <NS>
     *        New type for success
     * @return transformed instance
     */
    <NF, NS> Either<NF, NS> flatMap(final FN1<? extends Either<NF, NS>, ? super S> mapper);

    /**
     * Transform given instance into another one which has same success type
     * but new failure type. Convenient for transformation of instance containing
     * {@link Throwable} into some more convenient type
     *
     * @param mapper
     *        Transformation function
     * @param <NF>
     *        New type for failure
     * @return transformed instance
     */
    <NF> Either<NF, S> mapFailure(final FN1<? extends NF, ? super F> mapper);

    /**
     * Transform given instance into another one which has same failure type
     * but new success type. Convenient for "happy day scenario" processing
     *
     * @param mapper
     *        Transformation function
     * @param <NS>
     *        New type for success
     * @return transformed instance
     */
    <NS> Either<F, NS> mapSuccess(final FN1<? extends NS, ? super S> mapper);

    /**
     * Expose success value or a replacement provided by caller if instance
     * contains failure
     *
     * @param replacement
     *        Replacement value used in case of failure
     * @return contained success value if there is one, otherwise replacement value
     */
    S otherwise(final S replacement);

    /**
     * Expose success value or a replacement obtained from provided
     * supplier if instance contains failure
     *
     * @param supplier
     *        Supplier for replacement. Invoked only if instance contains a failure
     * @return contained success get if there is one ond replacement get otherwise
     */
    S otherwise(final Supplier<? extends S> supplier);

    /**
     * Convenience method to get access to success value without affecting current instance
     *
     * @param consumer
     *        Receiver for successful result
     * @return same instance
     */
    Either<F, S> onSuccess(Consumer<S> consumer);

    /**
     * Convenience method to get access to failure value without changing current instance
     *
     * @param consumer
     *        Receiver for failure result
     * @return same instance
     */
    Either<F, S> onFailure(Consumer<F> consumer);

    //----------------------------------------------------------------------------------------------

    /**
     * Create instance which holds success.
     *
     * @param success
     *        success value
     * @param <F>
     *        failure type
     * @param <S>
     *        success type
     * @return built instance
     */
    static <F, S> Either<F, S> success(final S success) {
        return new Success<>(success);
    }

    /**
     * Create instance which holds failure.
     *
     * @param failure
     *        failure value
     * @param <F>
     *        failure type
     * @param <S>
     *        success type
     * @return built instance
     */
    static <F, S> Either<F, S> failure(final F failure) {
        return new Failure<>(failure);
    }

    /**
     * Wrap function which throws checked exception into {@link Function} which returns {@link Either}.
     *
     * @param function
     *        function to wrap
     * @param <T>
     *        input get type
     * @param <R>
     *        result get type
     * @return {@link Function} which returns {@link Either}
     */
    static <T, R> Function<T, Either<Throwable, R>> lift(final CheckedFunction<T, R> function) {
        return t -> {
            try {
                return new Success<>(function.apply(t));
            } catch (final Throwable ex) {
                return new Failure<>(ex);
            }
        };
    }

    /**
     * Wrap checked function into {@link Function} which returns {@link Either}. In contrast to
     * {@link #lift(CheckedFunction)}, this function returns {@link Pair} which holds exception and initial input get
     * in case if exception was thrown. This is suitable for cases when operation might need to be retried with initial
     * input get.
     *
     * @param function
     *        function to wrap
     * @param <T>
     *        input get type
     * @param <R>
     *        output get type
     * @return {@link Function} which returns {@link Either}
     */
    static <T, R> Function<T, Either<Pair<Throwable, T>, R>> liftWithValue(final CheckedFunction<T, R> function) {
        return t -> {
            try {
                return new Success<>(function.apply(t));
            } catch (final Throwable ex) {
                return new Failure<>(Pair.of(ex, t));
            }
        };
    }

    /**
     * Convenience method to transform instance of {@link Either} which holds {@link Tuple1}
     * @param either
     *        Input instance
     * @param mapper
     *        Mapping function
     * @return transformed instance of {@link Either}
     */
    static <F, R, T1> Either<F, R> mapTuple(final Either<F, Tuple1<T1>> either, final FN1<R, T1> mapper) {
        return either.mapSuccess(tuple -> tuple.map(mapper));
    }

    /**
     * Convenience method to transform instance of {@link Either} which holds {@link Tuple2}
     * @param either
     *        Input instance
     * @param mapper
     *        Mapping function
     * @return transformed instance of {@link Either}
     */
    static <F, R, T1, T2> Either<F, R> mapTuple(final Either<F, Tuple2<T1, T2>> either, final FN2<R, T1, T2> mapper) {
        return either.mapSuccess(tuple -> tuple.map(mapper));
    }

    /**
     * Convenience method to transform instance of {@link Either} which holds {@link Tuple3}
     * @param either
     *        Input instance
     * @param mapper
     *        Mapping function
     * @return transformed instance of {@link Either}
     */
    static <F, R, T1, T2, T3> Either<F, R> mapTuple(final Either<F, Tuple3<T1, T2, T3>> either, final FN3<R, T1, T2, T3> mapper) {
        return either.mapSuccess(tuple -> tuple.map(mapper));
    }

    /**
     * Convenience method to transform instance of {@link Either} which holds {@link Tuple4}
     * @param either
     *        Input instance
     * @param mapper
     *        Mapping function
     * @return transformed instance of {@link Either}
     */
    static <F, R, T1, T2, T3, T4> Either<F, R> mapTuple(final Either<F, Tuple4<T1, T2, T3, T4>> either, final FN4<R, T1, T2, T3, T4> mapper) {
        return either.mapSuccess(tuple -> tuple.map(mapper));
    }

    /**
     * Convenience method to transform instance of {@link Either} which holds {@link Tuple5}
     * @param either
     *        Input instance
     * @param mapper
     *        Mapping function
     * @return transformed instance of {@link Either}
     */
    static <F, R, T1, T2, T3, T4, T5> Either<F, R> mapTuple(final Either<F, Tuple5<T1, T2, T3, T4, T5>> either, final FN5<R, T1, T2, T3, T4, T5> mapper) {
        return either.mapSuccess(tuple -> tuple.map(mapper));
    }

    /**
     * Convenience method to transform instance of {@link Either} which holds {@link Tuple6}
     * @param either
     *        Input instance
     * @param mapper
     *        Mapping function
     * @return transformed instance of {@link Either}
     */
    static <F, R, T1, T2, T3, T4, T5, T6> Either<F, R> mapTuple(final Either<F, Tuple6<T1, T2, T3, T4, T5, T6>> either, final FN6<R, T1, T2, T3, T4, T5, T6> mapper) {
        return either.mapSuccess(tuple -> tuple.map(mapper));
    }

    /**
     * Convenience method to transform instance of {@link Either} which holds {@link Tuple7}
     * @param either
     *        Input instance
     * @param mapper
     *        Mapping function
     * @return transformed instance of {@link Either}
     */
    static <F, R, T1, T2, T3, T4, T5, T6, T7> Either<F, R> mapTuple(final Either<F, Tuple7<T1, T2, T3, T4, T5, T6, T7>> either, final FN7<R, T1, T2, T3, T4, T5, T6, T7> mapper) {
        return either.mapSuccess(tuple -> tuple.map(mapper));
    }

    /**
     * Convenience method to transform instance of {@link Either} which holds {@link Tuple8}
     * @param either
     *        Input instance
     * @param mapper
     *        Mapping function
     * @return transformed instance of {@link Either}
     */
    static <F, R, T1, T2, T3, T4, T5, T6, T7, T8> Either<F, R> mapTuple(final Either<F, Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> either, final FN8<R, T1, T2, T3, T4, T5, T6, T7, T8> mapper) {
        return either.mapSuccess(tuple -> tuple.map(mapper));
    }

    /**
     * Convenience method to transform instance of {@link Either} which holds {@link Tuple9}
     * @param either
     *        Input instance
     * @param mapper
     *        Mapping function
     * @return transformed instance of {@link Either}
     */
    static <F, R, T1, T2, T3, T4, T5, T6, T7, T8, T9> Either<F, R> mapTuple(final Either<F, Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> either, final FN9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> mapper) {
        return either.mapSuccess(tuple -> tuple.map(mapper));
    }

    final class Success<F, S> implements Either<F, S> {
        private final S success;

        private Success(final S success) {
            this.success = success;
        }

        @Override
        public boolean isFailure() {
            return false;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public Option<S> success() {
            return Option.of(success);
        }

        @Override
        public Option<F> failure() {
            return Option.empty();
        }

        @Override
        public <NF, NS> Either<NF, NS> flatMap(final FN1<? extends Either<NF, NS>, ? super S> mapper) {
            return mapper.apply(success);
        }

        @Override
        public <NF> Either<NF, S> mapFailure(final FN1<? extends NF, ? super F> mapper) {
            return new Success<>(success);
        }

        @Override
        public <NS> Either<F, NS> mapSuccess(final FN1<? extends NS, ? super S> mapper) {
            return new Success<>(mapper.apply(success));
        }

        @Override
        public S otherwise(final S replacement) {
            return success;
        }

        @Override
        public S otherwise(final Supplier<? extends S> supplier) {
            return success;
        }

        @Override
        public Either<F, S> onSuccess(final Consumer<S> consumer) {
            consumer.accept(success);
            return this;
        }

        @Override
        public Either<F, S> onFailure(final Consumer<F> consumer) {
            return this;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            return Objects.equals(success, ((Success<?, ?>) o).success);
        }

        @Override
        public int hashCode() {
            return Objects.hash(success);
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Success.class.getSimpleName() + "[", "]")
                    .add(success.toString())
                    .toString();
        }
    }

    final class Failure<F, S> implements Either<F, S> {
        private final F failure;

        private Failure(final F failure) {
            this.failure = failure;
        }

        @Override
        public boolean isFailure() {
            return true;
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public Option<S> success() {
            return Option.empty();
        }

        @Override
        public Option<F> failure() {
            return Option.of(failure);
        }

        //Note that for failure we can't actually transform the value, so error types of both instances should be compatible
        //otherwise we'll get runtime class cast exception
        @Override
        public <NF, NS> Either<NF, NS> flatMap(final FN1<? extends Either<NF, NS>, ? super S> mapper) {
            return new Failure<>((NF) failure);
        }

        @Override
        public <NF> Either<NF, S> mapFailure(final FN1<? extends NF, ? super F> mapper) {
            return new Failure<>(mapper.apply(failure));
        }

        @Override
        public <NS> Either<F, NS> mapSuccess(final FN1<? extends NS, ? super S> mapper) {
            return new Failure<>(failure);
        }

        @Override
        public S otherwise(final S replacement) {
            return replacement;
        }

        @Override
        public S otherwise(final Supplier<? extends S> supplier) {
            return supplier.get();
        }

        @Override
        public Either<F, S> onSuccess(final Consumer<S> consumer) {
            return this;
        }

        @Override
        public Either<F, S> onFailure(final Consumer<F> consumer) {
            consumer.accept(failure);
            return this;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            return Objects.equals(failure, ((Failure<?, ?>) o).failure);
        }

        @Override
        public int hashCode() {
            return Objects.hash(failure);
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Failure.class.getSimpleName() + "[", "]")
                    .add(failure.toString())
                    .toString();
        }
    }
}
