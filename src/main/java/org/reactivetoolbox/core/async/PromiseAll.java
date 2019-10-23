package org.reactivetoolbox.core.async;

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

import org.reactivetoolbox.core.lang.Result;
import org.reactivetoolbox.core.lang.Tuple;
import org.reactivetoolbox.core.lang.Tuple.Tuple1;
import org.reactivetoolbox.core.lang.Tuple.Tuple2;
import org.reactivetoolbox.core.lang.Tuple.Tuple3;
import org.reactivetoolbox.core.lang.Tuple.Tuple4;
import org.reactivetoolbox.core.lang.Tuple.Tuple5;
import org.reactivetoolbox.core.lang.Tuple.Tuple6;
import org.reactivetoolbox.core.lang.Tuple.Tuple7;
import org.reactivetoolbox.core.lang.Tuple.Tuple8;
import org.reactivetoolbox.core.lang.Tuple.Tuple9;

import static org.reactivetoolbox.core.async.ActionableThreshold.threshold;
import static org.reactivetoolbox.core.async.Promise.give;
import static org.reactivetoolbox.core.lang.Result.success;
import static org.reactivetoolbox.core.lang.Tuple.with;

public interface PromiseAll<T> {
    static <T1> PromiseResult<Tuple1<T1>> all(final Promise<Result<T1>> promise1) {
        return PromiseResult.from(give(promise -> threshold(Tuple1.size(),
                                                            (at) -> promise1.then($ -> at.registerEvent()),
                                                            () -> promise1.then(v1 -> promise.resolve(Tuple.with(v1).map(PromiseAll::zip))))));
    }

    static <T1> Result<Tuple1<T1>> zip(final Result<T1> value) {
        return value.flatMap(vv1 -> success(Tuple.with(vv1)));
    }

    static <T1, T2> PromiseResult<Tuple2<T1, T2>> all(final Promise<Result<T1>> promise1,
                                                      final Promise<Result<T2>> promise2) {
        return PromiseResult.from(give(promise -> threshold(Tuple2.size(),
                                                            (at) -> { promise1.then($ -> at.registerEvent());
                                                                      promise2.then($ -> at.registerEvent()); },
                                                            () -> promise1.then(v1 ->
                                                                    promise2.then(v2 -> promise.resolve(Tuple.with(v1, v2).map(PromiseAll::zip)))))));
    }

    static <T1, T2> Result<Tuple2<T1, T2>> zip(final Result<T1> value1, final Result<T2> value2) {
        return value1.flatMap(vv1 -> value2.flatMap(vv2 -> success(with(vv1, vv2))));
    }

    static <T1, T2, T3> PromiseResult<Tuple3<T1, T2, T3>> all(final Promise<Result<T1>> promise1,
                                                              final Promise<Result<T2>> promise2,
                                                              final Promise<Result<T3>> promise3) {
        return PromiseResult.from(give(promise -> threshold(Tuple3.size(),
                                         (at) -> { promise1.then($ -> at.registerEvent());
                                                   promise2.then($ -> at.registerEvent());
                                                   promise3.then($ -> at.registerEvent()); },
                                         () -> promise1.then(v1 ->
                                                 promise2.then(v2 ->
                                                   promise3.then(v3 -> promise.resolve(Tuple.with(v1, v2, v3).map(PromiseAll::zip))))))));
    }

    static <T1, T2, T3> Result<Tuple3<T1, T2, T3>> zip(final Result<T1> value1, final Result<T2> value2, final Result<T3> value3) {
        return value1.flatMap(vv1 ->
                 value2.flatMap(vv2 ->
                   value3.flatMap(vv3 -> success(with(vv1, vv2, vv3)))));
    }

    static <T1, T2, T3, T4> PromiseResult<Tuple4<T1, T2, T3, T4>> all(final Promise<Result<T1>> promise1,
                                                                      final Promise<Result<T2>> promise2,
                                                                      final Promise<Result<T3>> promise3,
                                                                      final Promise<Result<T4>> promise4) {
        return PromiseResult.from(give(promise -> threshold(Tuple4.size(),
                                         (at) -> { promise1.then($ -> at.registerEvent());
                                                   promise2.then($ -> at.registerEvent());
                                                   promise3.then($ -> at.registerEvent());
                                                   promise4.then($ -> at.registerEvent()); },
                                         () -> promise1.then(v1 ->
                                                 promise2.then(v2 ->
                                                   promise3.then(v3 ->
                                                     promise4.then(v4 -> promise.resolve(Tuple.with(v1, v2, v3, v4).map(PromiseAll::zip)))))))));
    }

    static <T1, T2, T3, T4> Result<Tuple4<T1, T2, T3, T4>> zip(final Result<T1> value1,
                                                               final Result<T2> value2,
                                                               final Result<T3> value3,
                                                               final Result<T4> value4) {
        return value1.flatMap(vv1 ->
                 value2.flatMap(vv2 ->
                   value3.flatMap(vv3 ->
                     value4.flatMap(vv4 -> success(with(vv1, vv2, vv3, vv4))))));
    }

    static <T1, T2, T3, T4, T5> PromiseResult<Tuple5<T1, T2, T3, T4, T5>> all(final Promise<Result<T1>> promise1,
                                                                              final Promise<Result<T2>> promise2,
                                                                              final Promise<Result<T3>> promise3,
                                                                              final Promise<Result<T4>> promise4,
                                                                              final Promise<Result<T5>> promise5) {
        return PromiseResult.from(give(promise -> threshold(Tuple5.size(),
                                         (at) -> { promise1.then($ -> at.registerEvent());
                                                   promise2.then($ -> at.registerEvent());
                                                   promise3.then($ -> at.registerEvent());
                                                   promise4.then($ -> at.registerEvent());
                                                   promise5.then($ -> at.registerEvent()); },
                                         () -> promise1.then(v1 ->
                                                 promise2.then(v2 ->
                                                   promise3.then(v3 ->
                                                     promise4.then(v4 ->
                                                       promise5.then(v5 -> promise.resolve(Tuple.with(v1, v2, v3, v4, v5).map(PromiseAll::zip))))))))));
    }

    static <T1, T2, T3, T4, T5> Result<Tuple5<T1, T2, T3, T4, T5>> zip(final Result<T1> value1,
                                                                       final Result<T2> value2,
                                                                       final Result<T3> value3,
                                                                       final Result<T4> value4,
                                                                       final Result<T5> value5) {
        return value1.flatMap(vv1 ->
                 value2.flatMap(vv2 ->
                   value3.flatMap(vv3 ->
                     value4.flatMap(vv4 ->
                       value5.flatMap(vv5 -> success(with(vv1, vv2, vv3, vv4, vv5)))))));
    }

    static <T1, T2, T3, T4, T5, T6> PromiseResult<Tuple6<T1, T2, T3, T4, T5, T6>> all(final Promise<Result<T1>> promise1,
                                                                                      final Promise<Result<T2>> promise2,
                                                                                      final Promise<Result<T3>> promise3,
                                                                                      final Promise<Result<T4>> promise4,
                                                                                      final Promise<Result<T5>> promise5,
                                                                                      final Promise<Result<T6>> promise6) {
        return PromiseResult.from(give(promise -> threshold(Tuple6.size(),
                                         (at) -> { promise1.then($ -> at.registerEvent());
                                                   promise2.then($ -> at.registerEvent());
                                                   promise3.then($ -> at.registerEvent());
                                                   promise4.then($ -> at.registerEvent());
                                                   promise5.then($ -> at.registerEvent());
                                                   promise6.then($ -> at.registerEvent()); },
                                         () -> promise1.then(v1 ->
                                                 promise2.then(v2 ->
                                                   promise3.then(v3 ->
                                                     promise4.then(v4 ->
                                                       promise5.then(v5 ->
                                                         promise6.then(v6 -> promise.resolve(Tuple.with(v1, v2, v3, v4, v5, v6).map(PromiseAll::zip)))))))))));
    }

    static <T1, T2, T3, T4, T5, T6> Result<Tuple6<T1, T2, T3, T4, T5, T6>> zip(final Result<T1> value1,
                                                                               final Result<T2> value2,
                                                                               final Result<T3> value3,
                                                                               final Result<T4> value4,
                                                                               final Result<T5> value5,
                                                                               final Result<T6> value6) {
        return value1.flatMap(vv1 ->
                 value2.flatMap(vv2 ->
                   value3.flatMap(vv3 ->
                     value4.flatMap(vv4 ->
                       value5.flatMap(vv5 ->
                         value6.flatMap(vv6 -> success(with(vv1, vv2, vv3, vv4, vv5, vv6))))))));
    }


    static <T1, T2, T3, T4, T5, T6, T7> PromiseResult<Tuple7<T1, T2, T3, T4, T5, T6, T7>> all(final Promise<Result<T1>> promise1,
                                                                                              final Promise<Result<T2>> promise2,
                                                                                              final Promise<Result<T3>> promise3,
                                                                                              final Promise<Result<T4>> promise4,
                                                                                              final Promise<Result<T5>> promise5,
                                                                                              final Promise<Result<T6>> promise6,
                                                                                              final Promise<Result<T7>> promise7) {
        return PromiseResult.from(give(promise -> threshold(Tuple7.size(),
                                         (at) -> { promise1.then($ -> at.registerEvent());
                                                   promise2.then($ -> at.registerEvent());
                                                   promise3.then($ -> at.registerEvent());
                                                   promise4.then($ -> at.registerEvent());
                                                   promise5.then($ -> at.registerEvent());
                                                   promise6.then($ -> at.registerEvent());
                                                   promise7.then($ -> at.registerEvent()); },
                                         () -> promise1.then(v1 ->
                                                 promise2.then(v2 ->
                                                   promise3.then(v3 ->
                                                     promise4.then(v4 ->
                                                       promise5.then(v5 ->
                                                         promise6.then(v6 ->
                                                           promise7.then(v7 -> promise.resolve(Tuple.with(v1, v2, v3, v4, v5, v6, v7).map(PromiseAll::zip))))))))))));
    }

    static <T1, T2, T3, T4, T5, T6, T7> Result<Tuple7<T1, T2, T3, T4, T5, T6, T7>> zip(final Result<T1> value1,
                                                                                       final Result<T2> value2,
                                                                                       final Result<T3> value3,
                                                                                       final Result<T4> value4,
                                                                                       final Result<T5> value5,
                                                                                       final Result<T6> value6,
                                                                                       final Result<T7> value7) {
        return value1.flatMap(vv1 ->
                 value2.flatMap(vv2 ->
                   value3.flatMap(vv3 ->
                     value4.flatMap(vv4 ->
                       value5.flatMap(vv5 ->
                         value6.flatMap(vv6 ->
                           value7.flatMap(vv7 -> success(with(vv1, vv2, vv3, vv4, vv5, vv6, vv7)))))))));
    }


    static <T1, T2, T3, T4, T5, T6, T7, T8> PromiseResult<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> all(final Promise<Result<T1>> promise1,
                                                                                                      final Promise<Result<T2>> promise2,
                                                                                                      final Promise<Result<T3>> promise3,
                                                                                                      final Promise<Result<T4>> promise4,
                                                                                                      final Promise<Result<T5>> promise5,
                                                                                                      final Promise<Result<T6>> promise6,
                                                                                                      final Promise<Result<T7>> promise7,
                                                                                                      final Promise<Result<T8>> promise8) {
        return PromiseResult.from(give(promise -> threshold(Tuple8.size(),
                                         (at) -> { promise1.then($ -> at.registerEvent());
                                                   promise2.then($ -> at.registerEvent());
                                                   promise3.then($ -> at.registerEvent());
                                                   promise4.then($ -> at.registerEvent());
                                                   promise5.then($ -> at.registerEvent());
                                                   promise6.then($ -> at.registerEvent());
                                                   promise7.then($ -> at.registerEvent());
                                                   promise8.then($ -> at.registerEvent()); },
                                         () -> promise1.then(v1 ->
                                                 promise2.then(v2 ->
                                                   promise3.then(v3 ->
                                                     promise4.then(v4 ->
                                                       promise5.then(v5 ->
                                                         promise6.then(v6 ->
                                                           promise7.then(v7 ->
                                                             promise8.then(v8 -> promise.resolve(Tuple.with(v1, v2, v3, v4, v5, v6, v7, v8).map(PromiseAll::zip)))))))))))));
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8> Result<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> zip(final Result<T1> value1,
                                                                                               final Result<T2> value2,
                                                                                               final Result<T3> value3,
                                                                                               final Result<T4> value4,
                                                                                               final Result<T5> value5,
                                                                                               final Result<T6> value6,
                                                                                               final Result<T7> value7,
                                                                                               final Result<T8> value8) {
        return value1.flatMap(vv1 ->
                 value2.flatMap(vv2 ->
                   value3.flatMap(vv3 ->
                     value4.flatMap(vv4 ->
                       value5.flatMap(vv5 ->
                         value6.flatMap(vv6 ->
                           value7.flatMap(vv7 ->
                             value8.flatMap(vv8 -> success(with(vv1, vv2, vv3, vv4, vv5, vv6, vv7, vv8))))))))));
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8, T9> PromiseResult<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> all(final Promise<Result<T1>> promise1,
                                                                                                              final Promise<Result<T2>> promise2,
                                                                                                              final Promise<Result<T3>> promise3,
                                                                                                              final Promise<Result<T4>> promise4,
                                                                                                              final Promise<Result<T5>> promise5,
                                                                                                              final Promise<Result<T6>> promise6,
                                                                                                              final Promise<Result<T7>> promise7,
                                                                                                              final Promise<Result<T8>> promise8,
                                                                                                              final Promise<Result<T9>> promise9) {
        return PromiseResult.from(give(promise -> threshold(Tuple9.size(),
                                         (at) -> { promise1.then($ -> at.registerEvent());
                                                   promise2.then($ -> at.registerEvent());
                                                   promise3.then($ -> at.registerEvent());
                                                   promise4.then($ -> at.registerEvent());
                                                   promise5.then($ -> at.registerEvent());
                                                   promise6.then($ -> at.registerEvent());
                                                   promise7.then($ -> at.registerEvent());
                                                   promise8.then($ -> at.registerEvent());
                                                   promise9.then($ -> at.registerEvent()); },
                                         () -> promise1.then(v1 ->
                                                 promise2.then(v2 ->
                                                   promise3.then(v3 ->
                                                     promise4.then(v4 ->
                                                       promise5.then(v5 ->
                                                         promise6.then(v6 ->
                                                           promise7.then(v7 ->
                                                             promise8.then(v8 ->
                                                               promise9.then(v9 -> promise.resolve(Tuple.with(v1, v2, v3, v4, v5, v6, v7, v8, v9).map(PromiseAll::zip))))))))))))));
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Result<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> zip(final Result<T1> value1,
                                                                                                       final Result<T2> value2,
                                                                                                       final Result<T3> value3,
                                                                                                       final Result<T4> value4,
                                                                                                       final Result<T5> value5,
                                                                                                       final Result<T6> value6,
                                                                                                       final Result<T7> value7,
                                                                                                       final Result<T8> value8,
                                                                                                       final Result<T9> value9) {
        return value1.flatMap(vv1 ->
                 value2.flatMap(vv2 ->
                   value3.flatMap(vv3 ->
                     value4.flatMap(vv4 ->
                       value5.flatMap(vv5 ->
                         value6.flatMap(vv6 ->
                           value7.flatMap(vv7 ->
                             value8.flatMap(vv8 ->
                               value9.flatMap(vv9 -> success(with(vv1, vv2, vv3, vv4, vv5, vv6, vv7, vv8, vv9)))))))))));
    }
}
