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
import java.util.Comparator;
import java.util.Random;
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
public interface List<E> extends Collection<E> {
    /**
     * Return first element from list.
     *
     * @return First element wrapped into {@link Option} if element is present or {@link Option#empty()} otherwise
     */
    Option<E> first();

    /**
     * Return first {@code N} elements from the list. If there are less elements than requested, then avalable
     * elements returned.
     * @param n
     *        Number of elements to return.
     *
     * @return New list with at most requested number of elements
     */
    Collection<E> first(final int n);

    /**
     * Return last element from list.
     *
     * @return Last element wrapped into {@link Option} if element is present or {@link Option#empty()} otherwise
     */
    Option<E> last();

    /**
     * Return list which contains elements from current list followed by elements from list provided as parameter.
     *
     * @param other
     *        List to append
     *
     * @return List with elements from current list followed by elements from {@code other} list
     */
    Collection<E> append(final List<E> other);

    /**
     * Return list which contains elements from list provided as parameter followed by elements of current list.
     *
     * @param other
     *        List to append to
     *
     * @return List with elements from {@code other} list followed by elements from current list
     */
    default Collection<E> prepend(final List<E> other) {
        return other.append(this);
    }

    @Override
    default <R> Collection<R> map(final FN1<R, E> mapper) {
        return mapN((n, e) -> mapper.apply(e));
    }

    /**
     * Return list consisting of elements obtained from elements of current list with applied
     * transformation function. Unlike {@link #map(FN1)} this method passes index of element
     * along with element to transformation function.
     *
     * @param mapper
     *        Transformation function
     *
     * @return New list with transformed elements
     */
    <R> Collection<R> mapN(final FN2<R, Integer, E> mapper);

    @Override
    default Collection<E> apply(final Consumer<E> consumer) {
        return applyN((n, e) -> consumer.accept(e));
    }

    /**
     * Applies specified consumer to elements of current list.
     * Unlike {@link #apply(Consumer)} element index is passed along with element to consumer.
     *
     * @param consumer
     *        Consumer for elements
     *
     * @return Current list
     */
    Collection<E> applyN(final BiConsumer<Integer, E> consumer);

    /**
     * Create new list which will hold the same elements but sorted according to
     * provided comparator.
     *
     * @param comparator
     *        Element comparator
     *
     * @return Sorted list
     */
    Collection<E> sort(final Comparator<E> comparator);

    /**
     * Create new list which contains same elements reordered using given source of random numbers.
     *
     * @param random
     *        Random number source
     *
     * @return Shuffled list
     */
    Collection<E> shuffle(final Random random);

    @Override
    default Collection<E> filter(final Predicate<E> predicate) {
        return ListBuilder.<E>builder(size())
                .then(builder -> mapN((n, e) -> predicate.test(e) ? builder.append(e) : null))
                .toList();
    }

    @Override
    default Pair<Collection<E>, Collection<E>> splitBy(final Predicate<E> predicate) {
        final var listFalse = ListBuilder.<E>builder(size());
        final var listTrue = ListBuilder.<E>builder(size());

        mapN((n, e) -> (predicate.test(e) ? listTrue.append(e) : listFalse.append(e)));

        return Pair.pair(listFalse.toList(), listTrue.toList());
    }

    boolean elementEquals(final E ... elements);

    @SuppressWarnings("unchecked")
    static <T> List<T> from(final java.util.Collection<T> source) {
        return (List<T>) list(source.toArray());
    }

    @SuppressWarnings("unchecked")
    static <T> List<T> list() {
        return (List<T>) EMPTY_LIST;
    }

    //TODO: more efficient implementation
    static <T> List<T> list(final T... elements) {
        return new List<T>() {
            @Override
            public <R> Collection<R> mapN(final FN2<R, Integer, T> mapper) {
                return ListBuilder.<R>builder(size()).then(builder -> {
                    for (int i = 0; i < elements.length; i++) {
                        builder.append(mapper.apply(i, elements[i]));
                    }
                }).toList();
            }

            @Override
            public Collection<T> applyN(final BiConsumer<Integer, T> consumer) {
                for (int i = 0; i < elements.length; i++) {
                    consumer.accept(i, elements[i]);
                }
                return this;
            }

            @Override
            public Option<T> first() {
                return elements.length > 0 ? Option.option(elements[0]) : Option.empty();
            }

            @Override
            public Option<T> last() {
                return elements.length > 0 ? Option.option(elements[elements.length - 1]) : Option.empty();
            }

            @Override
            public Collection<T> first(final int n) {
                return list(Arrays.copyOf(elements, max(0, min(elements.length, n))));
            }

            @Override
            public Collection<T> append(final List<T> other) {
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
            public boolean elementEquals(final T... other) {
                return Arrays.equals(elements, other);
            }

            @Override
            public Collection<T> sort(final Comparator<T> comparator) {
                final var nelements = Arrays.copyOf(elements, elements.length);
                Arrays.sort(nelements, comparator);
                return list(nelements);
            }

            @Override
            public Collection<T> shuffle(final Random random) {
                final var nelements = Arrays.copyOf(elements, elements.length);

                for(int i = 0; i < nelements.length; i++) {
                    final var pos = random.nextInt(nelements.length);
                    final var element = nelements[pos];
                    nelements[pos] = nelements[i];
                    nelements[i] = element;
                }
                return list(nelements);
            }

            @Override
            public int hashCode() {
                return Arrays.hashCode(elements);
            }

            @Override
            public boolean equals(final Object obj) {
                if (obj == this) {
                    return true;
                }

                if(obj instanceof List) {
                    final var list = (List) obj;
                    return list.size() == size() && list.elementEquals(elements);
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

    Collection EMPTY_LIST = new List() {
        @Override
        public Collection mapN(final FN2 mapper) {
            return this;
        }

        @Override
        public Collection applyN(final BiConsumer consumer) {
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
        public Collection first(final int n) {
            return this;
        }

        @Override
        public Collection append(final List other) {
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
        public boolean elementEquals(final Object[] elements) {
            return elements.length == 0;
        }

        @Override
        public Collection sort(final Comparator comparator) {
            return this;
        }

        @Override
        public Collection shuffle(final Random random) {
            return this;
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

            if (obj instanceof List && ((Collection) obj).size() == 0) {
                return true;
            }

            return false;
        }

        @Override
        public String toString() {
            return "List()";
        }
    };

    static <T> Collector<T, ListBuilder<T>, Collection<T>> toList() {
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
            public Function<ListBuilder<T>, Collection<T>> finisher() {
                return ListBuilder::toList;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of();
            }
        };
    }
}
