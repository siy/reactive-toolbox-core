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

import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * General purpose container suitable for holding pair of linked values, like key and value in map.
 *
 * @param <L>
 *        Type of left value
 * @param <R>
 *        Type of right value
 */
public interface Pair<L, R> {
    /**
     * Transform pair into value of other type by applying specified function to both elements together.
     *
     * @param mapper
     *        Function used transform pair
     * @param <V>
     *        Type of new value
     *
     * @return Result of transformation
     */
    <V> V map(final FN2<V, L, R> mapper);

    /**
     * Transform pair into another pair by applying functions to each element of pair.
     *
     * @param leftMapper
     *        Function to apply to left value
     * @param rightMapper
     *        Function to apply to right value
     *
     * @return Transformed instance
     */
    default <NL, NR> Pair<NL, NR> map(final FN1<? extends NL, ? super L> leftMapper, final FN1<? extends NR, ? super R> rightMapper) {
        return map((l, r) -> with(leftMapper.apply(l), rightMapper.apply(r)));
    }

    /**
     * Factory method for creation of instances of {@link Pair} with given values.
     *
     * @param left
     *        Left value
     * @param right
     *        Right value
     *
     * @return created instance
     */
    static <L, R> Pair<L, R> with(final L left, final R right) {
        return new Pair<L, R>() {
            @Override
            public <V> V map(final FN2<V, L, R> mapper) {
                return mapper.apply(left, right);
            }

            @Override
            public boolean equals(final Object o) {
                if (this == o) {
                    return true;
                }

                if (!(o instanceof Pair)) {
                    return false;
                }

                return map((l, r) -> ((Pair<?, ?>) o).map((ol, or) -> Objects.equals(l, ol)
                                                                      && Objects.equals(r, or)));
            }

            @Override
            public int hashCode() {
                return map((l, r) -> Objects.hash(left, right));
            }

            @Override
            public String toString() {
                return map((l, r) -> new StringJoiner(", ", "Pair(", ")")
                            .add(Objects.toString(l))
                            .add(Objects.toString(r))
                            .toString());
            }
        };
    }

    /**
     * Factory method for creation of instances of {@link Pair} with given values.
     *
     * @param left
     *        Left value
     * @param right
     *        Right value
     * @param <L>
     *        type of left value
     * @param <R>
     *        type of right value
     *
     * @return created instance
     */
    static <L, R> Pair<L, R> pair(final L left, final R right) {
        return with(left, right);
    }

    /**
     * Swap left and right values.
     *
     * @return Transformed pair
     */
    default Pair<R, L> swap() {
        return map((l, r) -> with(r, l));
    }

    /**
     * Apply provided consumer to both elements of pair.
     *
     * @param biconsumer
     *        Consumer to apply
     *
     * @return current instance
     */
    default Pair<L, R> apply(final BiConsumer<L, R> biconsumer) {
        map((l, r) -> { biconsumer.accept(l, r); return null; });
        return this;
    }

    /**
     * Apply consumer to left element of pair.
     *
     * @param consumer
     *        Consumer to apply
     *
     * @return current instance
     */
    default Pair<L, R> applyLeft(final Consumer<L> consumer) {
        map((l, r) -> { consumer.accept(l); return null; });
        return this;
    }

    /**
     * Apply consumer to right element of pair
     *
     * @param consumer
     *        Consumer to apply
     *
     * @return current instance
     */
    default Pair<L, R> applyRight(final Consumer<R> consumer) {
        map((l, r) -> { consumer.accept(r); return null; });
        return this;
    }
}
