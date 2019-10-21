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

import org.reactivetoolbox.core.async.impl.PromiseImpl;
import org.reactivetoolbox.core.lang.Result;
import org.reactivetoolbox.core.lang.ResultTuple;
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

public interface PromiseAll<T> {
    static <T1> Promise<Tuple1<T1>> allOf(final Promise<T1> param1) {
        return new PromiseAll1<>(param1);
    }

    static <T1, T2> Promise<Tuple2<T1, T2>> allOf(final Promise<T1> param1, final Promise<T2> param2) {
        return new PromiseAll2<>(param1, param2);
    }

    static <T1, T2, T3> Promise<Tuple3<T1, T2, T3>> allOf(final Promise<T1> promise1,
                                                          final Promise<T2> promise2,
                                                          final Promise<T3> promise3) {
        return new PromiseAll3<>(promise1, promise2, promise3);
    }

    static <T1, T2, T3, T4> Promise<Tuple4<T1, T2, T3, T4>> allOf(final Promise<T1> promise1,
                                                                  final Promise<T2> promise2,
                                                                  final Promise<T3> promise3,
                                                                  final Promise<T4> promise4) {
        return new PromiseAll4<>(promise1, promise2, promise3, promise4);
    }

    static <T1, T2, T3, T4, T5> Promise<Tuple5<T1, T2, T3, T4, T5>> allOf(final Promise<T1> promise1,
                                                                          final Promise<T2> promise2,
                                                                          final Promise<T3> promise3,
                                                                          final Promise<T4> promise4,
                                                                          final Promise<T5> promise5) {
        return new PromiseAll5<>(promise1, promise2, promise3, promise4, promise5);
    }

    static <T1, T2, T3, T4, T5, T6> Promise<Tuple6<T1, T2, T3, T4, T5, T6>> allOf(final Promise<T1> promise1,
                                                                                  final Promise<T2> promise2,
                                                                                  final Promise<T3> promise3,
                                                                                  final Promise<T4> promise4,
                                                                                  final Promise<T5> promise5,
                                                                                  final Promise<T6> promise6) {
        return new PromiseAll6<>(promise1, promise2, promise3, promise4, promise5, promise6);
    }

    static <T1, T2, T3, T4, T5, T6, T7> Promise<Tuple7<T1, T2, T3, T4, T5, T6, T7>> allOf(final Promise<T1> promise1,
                                                                                          final Promise<T2> promise2,
                                                                                          final Promise<T3> promise3,
                                                                                          final Promise<T4> promise4,
                                                                                          final Promise<T5> promise5,
                                                                                          final Promise<T6> promise6,
                                                                                          final Promise<T7> promise7) {
        return new PromiseAll7<>(promise1, promise2, promise3, promise4, promise5, promise6, promise7);
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8> Promise<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> allOf(final Promise<T1> promise1,
                                                                                                  final Promise<T2> promise2,
                                                                                                  final Promise<T3> promise3,
                                                                                                  final Promise<T4> promise4,
                                                                                                  final Promise<T5> promise5,
                                                                                                  final Promise<T6> promise6,
                                                                                                  final Promise<T7> promise7,
                                                                                                  final Promise<T8> promise8) {
        return new PromiseAll8<>(promise1, promise2, promise3, promise4, promise5, promise6, promise7, promise8);
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Promise<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> allOf(final Promise<T1> promise1,
                                                                                                          final Promise<T2> promise2,
                                                                                                          final Promise<T3> promise3,
                                                                                                          final Promise<T4> promise4,
                                                                                                          final Promise<T5> promise5,
                                                                                                          final Promise<T6> promise6,
                                                                                                          final Promise<T7> promise7,
                                                                                                          final Promise<T8> promise8,
                                                                                                          final Promise<T9> promise9) {
        return new PromiseAll9<>(promise1, promise2, promise3, promise4, promise5, promise6, promise7, promise8, promise9);
    }

    //--------------------------------------------------------------------------------------
    // Result-specific versions
    //--------------------------------------------------------------------------------------

    static <T1> PromiseResult<Tuple1<T1>> resultsOf(final Promise<Result<T1>> promise1) {
        return PromiseResult.from(allOf(promise1)
                                          .map(tuple -> ResultTuple.of(tuple).zip()));
    }

    static <T1, T2> PromiseResult<Tuple2<T1, T2>> resultsOf(final Promise<Result<T1>> promise1,
                                                            final Promise<Result<T2>> promise2) {
        return PromiseResult.from(allOf(promise1, promise2)
                                          .map(tuple -> ResultTuple.of(tuple).zip()));
    }

    static <T1, T2, T3> PromiseResult<Tuple3<T1, T2, T3>> resultsOf(final Promise<Result<T1>> promise1,
                                                                    final Promise<Result<T2>> promise2,
                                                                    final Promise<Result<T3>> promise3) {
        return PromiseResult.from(allOf(promise1, promise2, promise3)
                                          .map(tuple -> ResultTuple.of(tuple).zip()));
    }

    static <T1, T2, T3, T4> PromiseResult<Tuple4<T1, T2, T3, T4>> resultsOf(final Promise<Result<T1>> promise1,
                                                                            final Promise<Result<T2>> promise2,
                                                                            final Promise<Result<T3>> promise3,
                                                                            final Promise<Result<T4>> promise4) {
        return PromiseResult.from(allOf(promise1, promise2, promise3, promise4)
                                          .map(tuple -> ResultTuple.of(tuple).zip()));
    }

    static <T1, T2, T3, T4, T5> PromiseResult<Tuple5<T1, T2, T3, T4, T5>> resultsOf(final Promise<Result<T1>> promise1,
                                                                                    final Promise<Result<T2>> promise2,
                                                                                    final Promise<Result<T3>> promise3,
                                                                                    final Promise<Result<T4>> promise4,
                                                                                    final Promise<Result<T5>> promise5) {
        return PromiseResult.from(allOf(promise1, promise2, promise3, promise4, promise5)
                                          .map(tuple -> ResultTuple.of(tuple).zip()));
    }

    static <T1, T2, T3, T4, T5, T6> PromiseResult<Tuple6<T1, T2, T3, T4, T5, T6>> resultsOf(final Promise<Result<T1>> promise1,
                                                                                            final Promise<Result<T2>> promise2,
                                                                                            final Promise<Result<T3>> promise3,
                                                                                            final Promise<Result<T4>> promise4,
                                                                                            final Promise<Result<T5>> promise5,
                                                                                            final Promise<Result<T6>> promise6) {
        return PromiseResult.from(allOf(promise1, promise2, promise3, promise4, promise5, promise6)
                                          .map(tuple -> ResultTuple.of(tuple).zip()));
    }

    static <T1, T2, T3, T4, T5, T6, T7> PromiseResult<Tuple7<T1, T2, T3, T4, T5, T6, T7>> resultsOf(final Promise<Result<T1>> promise1,
                                                                                                    final Promise<Result<T2>> promise2,
                                                                                                    final Promise<Result<T3>> promise3,
                                                                                                    final Promise<Result<T4>> promise4,
                                                                                                    final Promise<Result<T5>> promise5,
                                                                                                    final Promise<Result<T6>> promise6,
                                                                                                    final Promise<Result<T7>> promise7) {
        return PromiseResult.from(allOf(promise1, promise2, promise3, promise4, promise5, promise6, promise7)
                                          .map(tuple -> ResultTuple.of(tuple).zip()));
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8> PromiseResult<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> resultsOf(final Promise<Result<T1>> promise1,
                                                                                                            final Promise<Result<T2>> promise2,
                                                                                                            final Promise<Result<T3>> promise3,
                                                                                                            final Promise<Result<T4>> promise4,
                                                                                                            final Promise<Result<T5>> promise5,
                                                                                                            final Promise<Result<T6>> promise6,
                                                                                                            final Promise<Result<T7>> promise7,
                                                                                                            final Promise<Result<T8>> promise8) {
        return PromiseResult.from(allOf(promise1, promise2, promise3, promise4,
                                        promise5, promise6, promise7, promise8)
                                          .map(tuple -> ResultTuple.of(tuple).zip()));
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8, T9> PromiseResult<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> resultsOf(final Promise<Result<T1>> promise1,
                                                                                                                    final Promise<Result<T2>> promise2,
                                                                                                                    final Promise<Result<T3>> promise3,
                                                                                                                    final Promise<Result<T4>> promise4,
                                                                                                                    final Promise<Result<T5>> promise5,
                                                                                                                    final Promise<Result<T6>> promise6,
                                                                                                                    final Promise<Result<T7>> promise7,
                                                                                                                    final Promise<Result<T8>> promise8,
                                                                                                                    final Promise<Result<T9>> promise9) {
        return PromiseResult.from(allOf(promise1, promise2, promise3,
                                        promise4, promise5, promise6,
                                        promise7, promise8, promise9)
                                          .map(tuple -> ResultTuple.of(tuple).zip()));
    }

    class PromiseAll1<T1> extends PromiseImpl<Tuple1<T1>> {
        private PromiseAll1(final Promise<T1> promise1) {
            final ActionableThreshold threshold = ActionableThreshold.of(Tuple1.size(),
                                                                         () -> promise1.then(v1 -> resolve(Tuple.with(v1))));
            promise1.then($ -> threshold.registerEvent());
        }         
    }

    class PromiseAll2<T1, T2> extends PromiseImpl<Tuple2<T1, T2>> {
        private PromiseAll2(final Promise<T1> promise1,
                            final Promise<T2> promise2) {
            final var threshold = ActionableThreshold.of(Tuple2.size(),
                                                         () ->
                                                          promise1.then(v1 ->
                                                           promise2.then(v2 -> resolve(Tuple.with(v1, v2)))));
            promise1.then($ -> threshold.registerEvent());
            promise2.then($ -> threshold.registerEvent());
        }
    }

    class PromiseAll3<T1, T2, T3> extends PromiseImpl<Tuple3<T1, T2, T3>> {
        private PromiseAll3(final Promise<T1> promise1,
                            final Promise<T2> promise2,
                            final Promise<T3> promise3) {
            final var threshold = ActionableThreshold.of(Tuple3.size(),
                                                         () ->
                                                          promise1.then(v1 ->
                                                           promise2.then(v2 ->
                                                            promise3.then(v3 -> resolve(Tuple.with(v1, v2, v3))))));
            promise1.then($ -> threshold.registerEvent());
            promise2.then($ -> threshold.registerEvent());
            promise3.then($ -> threshold.registerEvent());
        }
    }

    class PromiseAll4<T1, T2, T3, T4> extends PromiseImpl<Tuple4<T1, T2, T3, T4>> {
        private PromiseAll4(final Promise<T1> promise1,
                            final Promise<T2> promise2,
                            final Promise<T3> promise3,
                            final Promise<T4> promise4) {
            final var threshold = ActionableThreshold.of(Tuple4.size(),
                                                         () ->
                                                          promise1.then(v1 ->
                                                           promise2.then(v2 ->
                                                            promise3.then(v3 ->
                                                             promise4.then(v4 -> resolve(Tuple.with(v1, v2, v3, v4)))))));
            promise1.then($ -> threshold.registerEvent());
            promise2.then($ -> threshold.registerEvent());
            promise3.then($ -> threshold.registerEvent());
            promise4.then($ -> threshold.registerEvent());
        }
    }

    class PromiseAll5<T1, T2, T3, T4, T5> extends PromiseImpl<Tuple5<T1, T2, T3, T4, T5>> {
        private PromiseAll5(final Promise<T1> promise1,
                            final Promise<T2> promise2,
                            final Promise<T3> promise3,
                            final Promise<T4> promise4,
                            final Promise<T5> promise5) {
            final var threshold = ActionableThreshold.of(Tuple5.size(),
                                                         () ->
                                                          promise1.then(v1 ->
                                                           promise2.then(v2 ->
                                                            promise3.then(v3 ->
                                                             promise4.then(v4 ->
                                                              promise5.then(v5 -> resolve(Tuple.with(v1, v2, v3, v4, v5))))))));
            promise1.then($ -> threshold.registerEvent());
            promise2.then($ -> threshold.registerEvent());
            promise3.then($ -> threshold.registerEvent());
            promise4.then($ -> threshold.registerEvent());
            promise5.then($ -> threshold.registerEvent());
        }
    }

    class PromiseAll6<T1, T2, T3, T4, T5, T6> extends PromiseImpl<Tuple6<T1, T2, T3, T4, T5, T6>> {
        private PromiseAll6(final Promise<T1> promise1,
                            final Promise<T2> promise2,
                            final Promise<T3> promise3,
                            final Promise<T4> promise4,
                            final Promise<T5> promise5,
                            final Promise<T6> promise6) {
            final var threshold = ActionableThreshold.of(Tuple6.size(),
                                                         () ->
                                                          promise1.then(v1 ->
                                                           promise2.then(v2 ->
                                                            promise3.then(v3 ->
                                                             promise4.then(v4 ->
                                                              promise5.then(v5 ->
                                                               promise6.then(v6 ->resolve(Tuple.with(v1, v2, v3, v4, v5, v6)))))))));
            promise1.then($ -> threshold.registerEvent());
            promise2.then($ -> threshold.registerEvent());
            promise3.then($ -> threshold.registerEvent());
            promise4.then($ -> threshold.registerEvent());
            promise5.then($ -> threshold.registerEvent());
            promise6.then($ -> threshold.registerEvent());
        }
    }

    class PromiseAll7<T1, T2, T3, T4, T5, T6, T7> extends PromiseImpl<Tuple7<T1, T2, T3, T4, T5, T6, T7>> {
        private PromiseAll7(final Promise<T1> promise1,
                            final Promise<T2> promise2,
                            final Promise<T3> promise3,
                            final Promise<T4> promise4,
                            final Promise<T5> promise5,
                            final Promise<T6> promise6,
                            final Promise<T7> promise7) {
            final var threshold = ActionableThreshold.of(Tuple7.size(),
                                                         () ->
                                                          promise1.then(v1 ->
                                                           promise2.then(v2 ->
                                                            promise3.then(v3 ->
                                                             promise4.then(v4 ->
                                                              promise5.then(v5 ->
                                                               promise6.then(v6 ->
                                                                promise7.then(v7 ->resolve(Tuple.with(v1, v2, v3, v4, v5, v6, v7))))))))));
            promise1.then($ -> threshold.registerEvent());
            promise2.then($ -> threshold.registerEvent());
            promise3.then($ -> threshold.registerEvent());
            promise4.then($ -> threshold.registerEvent());
            promise5.then($ -> threshold.registerEvent());
            promise6.then($ -> threshold.registerEvent());
            promise7.then($ -> threshold.registerEvent());
        }
    }

    class PromiseAll8<T1, T2, T3, T4, T5, T6, T7, T8> extends PromiseImpl<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> {
        private PromiseAll8(final Promise<T1> promise1,
                            final Promise<T2> promise2,
                            final Promise<T3> promise3,
                            final Promise<T4> promise4,
                            final Promise<T5> promise5,
                            final Promise<T6> promise6,
                            final Promise<T7> promise7,
                            final Promise<T8> promise8) {
            final var threshold = ActionableThreshold.of(Tuple8.size(),
                                                         () ->
                                                          promise1.then(v1 ->
                                                           promise2.then(v2 ->
                                                            promise3.then(v3 ->
                                                             promise4.then(v4 ->
                                                              promise5.then(v5 ->
                                                               promise6.then(v6 ->
                                                                promise7.then(v7 ->
                                                                 promise8.then(v8 ->resolve(Tuple.with(v1, v2, v3, v4, v5, v6, v7, v8)))))))))));
            promise1.then($ -> threshold.registerEvent());
            promise2.then($ -> threshold.registerEvent());
            promise3.then($ -> threshold.registerEvent());
            promise4.then($ -> threshold.registerEvent());
            promise5.then($ -> threshold.registerEvent());
            promise6.then($ -> threshold.registerEvent());
            promise7.then($ -> threshold.registerEvent());
            promise8.then($ -> threshold.registerEvent());
        }
    }

    class PromiseAll9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends PromiseImpl<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> {
        private PromiseAll9(final Promise<T1> promise1,
                            final Promise<T2> promise2,
                            final Promise<T3> promise3,
                            final Promise<T4> promise4,
                            final Promise<T5> promise5,
                            final Promise<T6> promise6,
                            final Promise<T7> promise7,
                            final Promise<T8> promise8,
                            final Promise<T9> promise9) {
            final var threshold = ActionableThreshold.of(Tuple9.size(),
                                                               () ->
                                                                promise1.then(v1 ->
                                                                 promise2.then(v2 ->
                                                                  promise3.then(v3 ->
                                                                   promise4.then(v4 ->
                                                                    promise5.then(v5 ->
                                                                     promise6.then(v6 ->
                                                                      promise7.then(v7 ->
                                                                       promise8.then(v8 ->
                                                                        promise9.then(v9 -> resolve(Tuple.with(v1, v2, v3, v4, v5, v6, v7, v8, v9))))))))))));
            promise1.then($ -> threshold.registerEvent());
            promise2.then($ -> threshold.registerEvent());
            promise3.then($ -> threshold.registerEvent());
            promise4.then($ -> threshold.registerEvent());
            promise5.then($ -> threshold.registerEvent());
            promise6.then($ -> threshold.registerEvent());
            promise7.then($ -> threshold.registerEvent());
            promise8.then($ -> threshold.registerEvent());
            promise9.then($ -> threshold.registerEvent());
        }
    }
}
