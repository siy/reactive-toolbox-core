package org.reactivetoolbox.core.lang;

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

import org.reactivetoolbox.core.lang.Functions.FN1;
import org.reactivetoolbox.core.lang.Functions.FN2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Immutable List
 */
//TODO: experimental, requires more considerations and, most likely, more efficient implementation
public interface List<E> {
    <R> List<R> mapN(final FN2<R, Integer, E> mapper);

    List<E> applyN(final BiConsumer<Integer, E> consumer);

    Option<E> first();

    Option<E> last();

    List<E> first(final int n);

    List<E> append(final List<E> other);

    Stream<E> stream();

    int size();

    boolean equals(final E ... elements);

    default <R> List<R> map(final FN1<R, E> mapper) {
        return mapN((n, e) -> mapper.apply(e));
    }

    default List<E> filter(final Predicate<E> predicate) {
        return ListBuilder.<E>builder(size())
                .then(builder -> apply(e -> {
                    if (predicate.test(e)) {
                        builder.append(e);
                    }
                }))
                .toList();
    }

    default List<E> apply(final Consumer<E> consumer) {
        return applyN((n, e) -> consumer.accept(e));
    }

    default List<E> prepend(final List<E> other) {
        return other.append(this);
    }

    @SuppressWarnings("unchecked")
    static <T> List<T> from(final java.util.List<T> source) {
        return (List<T>) list(source.toArray());
    }

    @SuppressWarnings("unchecked")
    static <T> List<T> list() {
        return (List<T>) EMPTY_LIST;
    }

    static <T> List<T> list(final T... elements) {
        return new List<T>() {
            @Override
            public <R> List<R> mapN(final FN2<R, Integer, T> mapper) {
                return ListBuilder.<R>builder(size()).then(builder -> {
                    for (int i = 0; i < elements.length; i++) {
                        builder.append(mapper.apply(i, elements[i]));
                    }
                }).toList();
            }

            @Override
            public List<T> applyN(final BiConsumer<Integer, T> consumer) {
                for (int i = 0; i < elements.length; i++) {
                    consumer.accept(i, elements[i]);
                }
                return this;
            }

            @Override
            public Option<T> first() {
                return elements.length > 0 ? Option.of(elements[0]) : Option.empty();
            }

            @Override
            public Option<T> last() {
                return elements.length > 0 ? Option.of(elements[elements.length - 1]) : Option.empty();
            }

            @Override
            public List<T> first(final int n) {
                return list(Arrays.copyOf(elements, max(0, min(elements.length, n))));
            }

            @Override
            public List<T> append(final List<T> other) {
                return ListBuilder.<T>builder(other.size(), elements)
                        .append(other)
                        .toList();
            }

            @Override
            public Stream<T> stream() {
                return Arrays.stream(elements);
            }

            @Override
            public int size() {
                return elements.length;
            }

            @Override
            public boolean equals(final T... other) {
                return Arrays.equals(elements, other);
            }

            @Override
            public int hashCode() {
                return Arrays.hashCode(elements);
            }

            @SuppressWarnings("unchecked")
            @Override
            public boolean equals(final Object obj) {
                if (obj == this) {
                    return true;
                }

                if(obj instanceof List) {
                    final var list = (List) obj;
                    return list.size() == size() && list.equals(elements);
                }

                return false;
            }

            @Override
            public String toString() {
                final StringJoiner joiner = new StringJoiner(",", "List(", ")");
                apply(e -> joiner.add(e.toString()));
                return joiner.toString();
            }
        };
    }

    final class ListBuilder<T> {
        private final ArrayList<T> values;

        private ListBuilder(final int capacity) {
            values = new ArrayList<>(capacity);
        }

        private ListBuilder(final int extraSize, final T...elements) {
            values = new ArrayList<T>(elements.length + extraSize);
            values.addAll(Arrays.asList(elements));
        }

        static <E> ListBuilder<E> builder(final int extraSize, final E...elements) {
            return new ListBuilder<>(extraSize, elements);
        }

        static <E> ListBuilder<E> builder(final int capacity) {
            return new ListBuilder<>(capacity);
        }

        ListBuilder<T> append(final T e) {
            values.add(e);
            return this;
        }

        ListBuilder<T> then(final Consumer<ListBuilder<T>> consumer) {
            consumer.accept(this);
            return this;
        }

        List<T> toList() {
            return from(values);
        }

        ListBuilder<T> append(final List<T> other) {
            other.apply(values::add);
            return this;
        }
    }

    List EMPTY_LIST = new List() {
        @Override
        public List mapN(final FN2 mapper) {
            return this;
        }

        @Override
        public List applyN(final BiConsumer consumer) {
            return this;
        }

        @Override
        public Option first() {
            return Option.empty();
        }

        @Override
        public Option last() {
            return Option.empty();
        }

        @Override
        public List first(final int n) {
            return this;
        }

        @Override
        public List append(final List other) {
            return other;
        }

        @Override
        public Stream stream() {
            return Stream.empty();
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean equals(final Object[] elements) {
            return elements.length == 0;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }

            if (obj instanceof List && ((List) obj).size() == 0) {
                return true;
            }

            return false;
        }

        @Override
        public String toString() {
            return "List()";
        }
    };

    static <T> Collector<T, ListBuilder<T>, List<T>> toList() {
        return new Collector<>() {
            @Override
            public Supplier<ListBuilder<T>> supplier() {
                return () -> new ListBuilder<T>(16);
            }

            @Override
            public BiConsumer<ListBuilder<T>, T> accumulator() {
                return ListBuilder::append;
            }

            @Override
            public BinaryOperator<ListBuilder<T>> combiner() {
                return (left, right) -> {
                    left.append(right.toList());
                    return left;
                };
            }

            @Override
            public Function<ListBuilder<T>, List<T>> finisher() {
                return ListBuilder::toList;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of();
            }
        };
    }
}
