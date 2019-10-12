package org.reactivetoolbox.core.lang;
/*
 * Copyright (c) 2017-2019 Sergiy Yevtushenko
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

import org.reactivetoolbox.core.lang.Functions.FN1;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Implementation of basic immutable container for value which may or may not be present.
 * Note that unlike {@link java.util.Optional} this implementation does not treat {@code null}
 * as {@code empty} value. The {@code null} value is a completely valid content of non-empty
 * instance. The {@code empty} instance actually does not contain anything.
 * For convenience there is a static factory method {@link #of(Object)} which creates
 * empty instance for {@code null} values and non-empty instance for other values.
 *
 * @param <T>
 *        Type of contained value
 */
public abstract class Option<T> implements Either<Void, T>{
    /**
     * Convert nullable value into instance of {@link Option}. This method converts
     * {@code null} to empty instance and any other value into non-empty instance.
     *
     * @param value
     *        Value to convert.
     * @return created instance.
     */
    public static <T> Option<T> of(final T value) {
         return value == null ? Option.empty() : Option.with(value);
    }

    /**
     * Create empty instance.
     *
     * @return Created instance
     */
    public static <R> Option<R> empty() {
        return new Option<>() {
            @Override
            public <T> T map(final FN1<? extends T, ? super Void> leftMapper, final FN1<? extends T, ? super R> rightMapper) {
                return leftMapper.apply(null);
            }
        };
    }

    /**
     * Create a present or empty instance depending on the passed value.
     *
     * @param value
     *        Value to be stored in the created instance
     * @return Created instance
     */
    public static <R> Option<R> with(final R value) {
        return new Option<>() {
            @Override
            public <T> T map(final FN1<? extends T, ? super Void> leftMapper, final FN1<? extends T, ? super R> rightMapper) {
                return rightMapper.apply(value);
            }
        };
    }

    /**
     * Transform instance according to results of testing of contained value with provided predicate.
     * If instance is empty, it remains empty. If instance contains value, this value is passed to predicate.
     * If predicate returns <code>true</code> then instance remains untouched. If predicate returns <code>false</code>
     * then empty instance is returned instead.
     *
     * @param predicate
     *        Predicate to test instance value.
     *
     * @return current instance if it is not empty and predicate returns <code>true</code> and empty instance otherwise
     */
    public Option<T> filter(final Predicate<? super T> predicate) {
        return flatMap(v -> predicate.test(v) ? this : empty());
    }

    /**
     * Shorthand for {@code filer(v -> v != null)}.
     *
     * @return current instance if it contains not null value and empty instance otherwise
     */
    public Option<T> notNull() {
        return flatMap(v -> v != null ? this : empty());
    }

    /**
     * Convert instance into other instance of different type using provided mapping function. Empty instance is mapped
     * into empty instance of different type. Non-empty instance is converted to empty or non-empty instance depending
     * on results of execution of mapping function. Mapping function receives value contained in the current instance
     * only if current instance is not empty. Result of invocation of mapping function is wrapped into new instance.
     * If result of application of mapping function is <code>null</code>, then resulting instance will be empty.
     * <b>WARNING!</b> While it is highly discouraged to use this method with mapping functions which may return
     * <code>null</code>. Such a usage may result to subtle, hard to debug issues.
     * If such a behavior is actually necessary then use of {@link #flatMap(FN1)} provides clear and much less
     * error-prone way to achieve it.
     *
     * @param mapper
     *        Mapping function
     * @param <U>
     *        Type of new value
     * @return transformed instance
     */
    public  <U> Option<U> map(final FN1<U, ? super T> mapper) {
        return flatMap(t -> with(mapper.apply(t)));
    }

    /**
     * Pass internal value to provided consumer in-line. Consumer is invoked only is current instance is not empty.
     * This is a convenience method which can be inserted at any point of fluent call chain. Note that provided
     * consumer should not change value in any way (for example, if contained value is mutable collection/map/array/etc.)
     * and should not throw any kind of exceptions.
     *
     * @param consumer
     *        Consumer to pass contained value to
     * @return this instance for fluent call chaining
     */
    public Option<T> ifPresent(final Consumer<? super T> consumer) {
        map(t -> {consumer.accept(t); return null;});
        return this;
    }

    /**
     * Execute action if instance is empty and do nothing otherwise.
     *
     * @param action
     *        Action to perform on empty instance
     * @return this instance for fluent call chaining
     */
    public Option<T> ifEmpty(final Runnable action) {
        map(t1 -> { action.run(); return null; }, t -> null);
        return this;
    }

    /**
     * Convenience method which allows to perform specific actions for empty and non-empty instances at once.
     *
     * @param emptyValConsumer
     *        Action to perform in case of empty instance
     * @param nonEmptyValConsumer
     *        Action to perform on non-empty instance value
     * @return this instance for fluent call chaining
     */
    public Option<T> consume(final Runnable emptyValConsumer, final Consumer<? super T> nonEmptyValConsumer) {
        map(t1 -> { emptyValConsumer.run(); return null;}, t2 -> { nonEmptyValConsumer.accept(t2); return null;});
        return this;
    }

    /**
     * Replace current non-empty instance with another one generated by applying provided mapper to value stored
     * in this instance. Empty instance is replaced with empty instance of new type matching type of provided mapping
     * function.
     *
     * @param mapper
     *        Mapping function
     * @param <U>
     *        New type
     * @return Instance of new type
     */
    public <U> Option<U> flatMap(final FN1<Option<U>, ? super T> mapper) {
        return map(v -> empty(), mapper);
    }

    /**
     * Logical <code>OR</code> between current instance and instance of same type provided by specified supplier.
     * First non-empty instance is returned. Note that if current instance is not empty then supplier is not invoked.
     *
     * @param supplier
     *        Supplier which provides new instance in case if current instance is empty
     * @return first non-empty instance, either current one or one returned by provided supplier
     */
    public Option<T> or(final Supplier<Option<T>> supplier) {
        return map(v -> supplier.get(), v -> this);
    }

    /**
     * Logical <code>OR</code> between current instance and provided instance of same type.
     * First non-empty instance is returned.
     *
     * @param replacement
     *        Replacement instance which is returned in case if current instance is empty
     * @return first non-empty instance, either current one or one returned by provided supplier
     */
    public Option<T> or(final Option<T> replacement) {
        return map(v -> replacement, v -> this);
    }

    /**
     * Return current value stored in current instance if current instance is non-empty. If current
     * instance is empty then return provided replacement value.
     *
     * @param replacement
     *        Replacement value returned in case if current instance is empty
     *
     * @return either value stored in current instance or provided replacement value if current instance is empty
     */
    public T otherwise(final T replacement) {
        return map(v -> replacement, v -> v);
    }

    /**
     * Return current value stored in current instance if current instance is non-empty. If current
     * instance is empty then return value returned by provided supplier. If current instance is not empty then
     * supplier is not invoked.
     *
     * @param supplier
     *        Supplier for replacement value returned in case if current instance is empty
     *
     * @return either value stored in current instance or value returned by provided supplier if current instance
     * is empty
     */
    public T otherwise(final Supplier<T> supplier) {
        return map(v -> supplier.get(), v -> v);
    }

    /**
     * Stream current instance. For empty instance empty stream is created. For non-empty instance the stream
     * with single element is returned. The element is the value stored in current instance.
     *
     * @return created stream
     */
    public Stream<T> stream() {
        return map(v -> Stream.empty(), Stream::of);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!(o  instanceof Option)) {
            return false;
        }

        final Option<?> option = (Option<?>) o;
        return map(v -> option.map(vo -> true, vo -> false),
                   v -> option.map(vo -> false, vo -> Objects.equals(v, vo)));
    }

    @Override
    public int hashCode() {
        return map(v -> 0, Objects::hash);
    }

    @Override
    public String toString() {
        return map(v -> "Option()", v -> "Option(" + v.toString() + ")");
    }
}
