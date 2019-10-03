package org.reactivetoolbox.core.functional;

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

/**
 * Tuples with various size and convenient static factories for tuple creation.<br/>
 */
public interface Tuple {
    interface Tuple0 {
        <T> T map(FN0<T> mapper);
    }

    interface Tuple1<T1> {
        <T> T map(FN1<T, T1> mapper);
    }

    interface Tuple2<T1, T2> {
        <T> T map(FN2<T, T1, T2> mapper);
    }

    interface Tuple3<T1, T2, T3> {
        <T> T map(FN3<T, T1, T2, T3> mapper);
    }

    interface Tuple4<T1, T2, T3, T4> {
        <T> T map(FN4<T, T1, T2, T3, T4> mapper);
    }

    interface Tuple5<T1, T2, T3, T4, T5> {
        <T> T map(FN5<T, T1, T2, T3, T4, T5> mapper);
    }

    interface Tuple6<T1, T2, T3, T4, T5, T6> {
        <T> T map(FN6<T, T1, T2, T3, T4, T5, T6> mapper);
    }

    interface Tuple7<T1, T2, T3, T4, T5, T6, T7> {
        <T> T map(FN7<T, T1, T2, T3, T4, T5, T6, T7> mapper);
    }

    interface Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> {
        <T> T map(FN8<T, T1, T2, T3, T4, T5, T6, T7, T8> mapper);
    }

    interface Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
        <T> T map(FN9<T, T1, T2, T3, T4, T5, T6, T7, T8, T9> mapper);
    }

    static Tuple0 with() {
        return new Tuple0() {
            @Override
            public <T> T map(final FN0<T> mapper) {
                return mapper.apply();
            }
        };
    }

    static <T1> Tuple1<T1> with(final T1 param1) {
        return new Tuple1<>() {
            @Override
            public <T> T map(final FN1<T, T1> mapper) {
                return mapper.apply(param1);
            }
        };
    }

    static <T1, T2> Tuple2<T1, T2> with(final T1 param1, final T2 param2) {
        return new Tuple2<>() {
            @Override
            public <T> T map(final FN2<T, T1, T2> mapper) {
                return mapper.apply(param1, param2);
            }
        };
    }

    static <T1, T2, T3> Tuple3<T1, T2, T3> with(final T1 param1, final T2 param2, final T3 param3) {
        return new Tuple3<>() {
            @Override
            public <T> T map(final FN3<T, T1, T2, T3> mapper) {
                return mapper.apply(param1, param2, param3);
            }
        };
    }

    static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> with(final T1 param1, final T2 param2, final T3 param3,
                                                        final T4 param4) {
        return new Tuple4<>() {
            @Override
            public <T> T map(final FN4<T, T1, T2, T3, T4> mapper) {
                return mapper.apply(param1, param2, param3, param4);
            }
        };
    }

    static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> with(final T1 param1, final T2 param2, final T3 param3,
                                                                final T4 param4, final T5 param5) {
        return new Tuple5<>() {
            @Override
            public <T> T map(final FN5<T, T1, T2, T3, T4, T5> mapper) {
                return mapper.apply(param1, param2, param3, param4, param5);
            }
        };
    }

    static <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> with(final T1 param1, final T2 param2, final T3 param3,
                                                                        final T4 param4, final T5 param5, final T6 param6) {
        return new Tuple6<>() {
            @Override
            public <T> T map(final FN6<T, T1, T2, T3, T4, T5, T6> mapper) {
                return mapper.apply(param1, param2, param3, param4, param5, param6);
            }
        };
    }

    static <T1, T2, T3, T4, T5, T6, T7> Tuple7<T1, T2, T3, T4, T5, T6, T7> with(final T1 param1, final T2 param2, final T3 param3,
                                                                                final T4 param4, final T5 param5, final T6 param6,
                                                                                final T7 param7) {
        return new Tuple7<>() {
            @Override
            public <T> T map(final FN7<T, T1, T2, T3, T4, T5, T6, T7> mapper) {
                return mapper.apply(param1, param2, param3, param4, param5, param6, param7);
            }
        };
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8> Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> with(final T1 param1, final T2 param2, final T3 param3,
                                                                                        final T4 param4, final T5 param5, final T6 param6,
                                                                                        final T7 param7, final T8 param8) {
        return new Tuple8<>() {
            @Override
            public <T> T map(final FN8<T, T1, T2, T3, T4, T5, T6, T7, T8> mapper) {
                return mapper.apply(param1, param2, param3, param4, param5, param6, param7, param8);
            }
        };
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> with(final T1 param1, final T2 param2, final T3 param3,
                                                                                                final T4 param4, final T5 param5, final T6 param6,
                                                                                                final T7 param7, final T8 param8, final T9 param9) {
        return new Tuple9<>() {
            @Override
            public <T> T map(final FN9<T, T1, T2, T3, T4, T5, T6, T7, T8, T9> mapper) {
                return mapper.apply(param1, param2, param3, param4, param5, param6, param7, param8, param9);
            }
        };
    }
}
