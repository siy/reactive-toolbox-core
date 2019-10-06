package org.reactivetoolbox.core.async;

import org.reactivetoolbox.core.functional.Result;
import org.reactivetoolbox.core.functional.Result.Result1;
import org.reactivetoolbox.core.functional.Result.Result2;
import org.reactivetoolbox.core.functional.Result.Result3;
import org.reactivetoolbox.core.functional.Result.Result4;
import org.reactivetoolbox.core.functional.Result.Result5;
import org.reactivetoolbox.core.functional.Result.Result6;
import org.reactivetoolbox.core.functional.Result.Result7;
import org.reactivetoolbox.core.functional.Result.Result8;
import org.reactivetoolbox.core.functional.Result.Result9;
import org.reactivetoolbox.core.functional.ResultTuple;

public interface PromiseResult {
    static <T1> Promise<Result1<T1>> resultOf(final Promise<Result<T1>> promise1) {
        return PromiseAll.allOf(promise1)
                         .map(tuple -> ResultTuple.of(tuple).zip());
    }

    static <T1, T2> Promise<Result2<T1, T2>> resultOf(final Promise<Result<T1>> promise1,
                                                      final Promise<Result<T2>> promise2) {
        return PromiseAll.allOf(promise1, promise2)
                         .map(tuple -> ResultTuple.of(tuple).zip());
    }

    static <T1, T2, T3> Promise<Result3<T1, T2, T3>> resultOf(final Promise<Result<T1>> promise1,
                                                              final Promise<Result<T2>> promise2,
                                                              final Promise<Result<T3>> promise3) {
        return PromiseAll.allOf(promise1, promise2, promise3)
                         .map(tuple -> ResultTuple.of(tuple).zip());
    }

    static <T1, T2, T3, T4> Promise<Result4<T1, T2, T3, T4>> resultOf(final Promise<Result<T1>> promise1,
                                                                      final Promise<Result<T2>> promise2,
                                                                      final Promise<Result<T3>> promise3,
                                                                      final Promise<Result<T4>> promise4) {
        return PromiseAll.allOf(promise1, promise2, promise3, promise4)
                         .map(tuple -> ResultTuple.of(tuple).zip());
    }

    static <T1, T2, T3, T4, T5> Promise<Result5<T1, T2, T3, T4, T5>> resultOf(final Promise<Result<T1>> promise1,
                                                                              final Promise<Result<T2>> promise2,
                                                                              final Promise<Result<T3>> promise3,
                                                                              final Promise<Result<T4>> promise4,
                                                                              final Promise<Result<T5>> promise5) {
        return PromiseAll.allOf(promise1, promise2, promise3, promise4, promise5)
                         .map(tuple -> ResultTuple.of(tuple).zip());
    }

    static <T1, T2, T3, T4, T5, T6> Promise<Result6<T1, T2, T3, T4, T5, T6>> resultOf(final Promise<Result<T1>> promise1,
                                                                                      final Promise<Result<T2>> promise2,
                                                                                      final Promise<Result<T3>> promise3,
                                                                                      final Promise<Result<T4>> promise4,
                                                                                      final Promise<Result<T5>> promise5,
                                                                                      final Promise<Result<T6>> promise6) {
        return PromiseAll.allOf(promise1, promise2, promise3, promise4, promise5, promise6)
                         .map(tuple -> ResultTuple.of(tuple).zip());
    }

    static <T1, T2, T3, T4, T5, T6, T7> Promise<Result7<T1, T2, T3, T4, T5, T6, T7>> resultOf(final Promise<Result<T1>> promise1,
                                                                                              final Promise<Result<T2>> promise2,
                                                                                              final Promise<Result<T3>> promise3,
                                                                                              final Promise<Result<T4>> promise4,
                                                                                              final Promise<Result<T5>> promise5,
                                                                                              final Promise<Result<T6>> promise6,
                                                                                              final Promise<Result<T7>> promise7) {
        return PromiseAll.allOf(promise1, promise2, promise3, promise4, promise5, promise6, promise7)
                         .map(tuple -> ResultTuple.of(tuple).zip());
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8> Promise<Result8<T1, T2, T3, T4, T5, T6, T7, T8>> resultOf(final Promise<Result<T1>> promise1,
                                                                                                      final Promise<Result<T2>> promise2,
                                                                                                      final Promise<Result<T3>> promise3,
                                                                                                      final Promise<Result<T4>> promise4,
                                                                                                      final Promise<Result<T5>> promise5,
                                                                                                      final Promise<Result<T6>> promise6,
                                                                                                      final Promise<Result<T7>> promise7,
                                                                                                      final Promise<Result<T8>> promise8) {
        return PromiseAll.allOf(promise1, promise2, promise3, promise4,
                                promise5, promise6, promise7, promise8)
                         .map(tuple -> ResultTuple.of(tuple).zip());
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Promise<Result9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> resultOf(final Promise<Result<T1>> promise1,
                                                                                                              final Promise<Result<T2>> promise2,
                                                                                                              final Promise<Result<T3>> promise3,
                                                                                                              final Promise<Result<T4>> promise4,
                                                                                                              final Promise<Result<T5>> promise5,
                                                                                                              final Promise<Result<T6>> promise6,
                                                                                                              final Promise<Result<T7>> promise7,
                                                                                                              final Promise<Result<T8>> promise8,
                                                                                                              final Promise<Result<T9>> promise9) {
        return PromiseAll.allOf(promise1, promise2, promise3,
                                promise4, promise5, promise6,
                                promise7, promise8, promise9)
                         .map(tuple -> ResultTuple.of(tuple).zip());
    }
}
