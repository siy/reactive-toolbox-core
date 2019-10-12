package org.reactivetoolbox.core.lang;

import org.reactivetoolbox.core.lang.Functions.FN1;
import org.reactivetoolbox.core.lang.Functions.FN2;
import org.reactivetoolbox.core.lang.Functions.FN3;
import org.reactivetoolbox.core.lang.Functions.FN4;
import org.reactivetoolbox.core.lang.Functions.FN5;
import org.reactivetoolbox.core.lang.Functions.FN6;
import org.reactivetoolbox.core.lang.Functions.FN7;
import org.reactivetoolbox.core.lang.Functions.FN8;
import org.reactivetoolbox.core.lang.Functions.FN9;
import org.reactivetoolbox.core.lang.Result.Result1;
import org.reactivetoolbox.core.lang.Result.Result2;
import org.reactivetoolbox.core.lang.Result.Result3;
import org.reactivetoolbox.core.lang.Result.Result4;
import org.reactivetoolbox.core.lang.Result.Result5;
import org.reactivetoolbox.core.lang.Result.Result6;
import org.reactivetoolbox.core.lang.Result.Result7;
import org.reactivetoolbox.core.lang.Result.Result8;
import org.reactivetoolbox.core.lang.Result.Result9;
import org.reactivetoolbox.core.lang.Tuple.Tuple1;
import org.reactivetoolbox.core.lang.Tuple.Tuple2;
import org.reactivetoolbox.core.lang.Tuple.Tuple3;
import org.reactivetoolbox.core.lang.Tuple.Tuple4;
import org.reactivetoolbox.core.lang.Tuple.Tuple5;
import org.reactivetoolbox.core.lang.Tuple.Tuple6;
import org.reactivetoolbox.core.lang.Tuple.Tuple7;
import org.reactivetoolbox.core.lang.Tuple.Tuple8;
import org.reactivetoolbox.core.lang.Tuple.Tuple9;

import static org.reactivetoolbox.core.lang.Result.success;
import static org.reactivetoolbox.core.lang.Tuple.with;

public interface ResultTuple {
    interface Zipable<T> {
        T zip();
    }

    static <T1> ResultTuple1<T1> of(final Result<T1> param1) {
        return new ResultTuple1<T1>() {
            @Override
            public <T> T map(final FN1<T, Result<T1>> mapper) {
                return mapper.apply(param1);
            }
        };
    }

    static <T1, T2> ResultTuple2<T1, T2> of(final Result<T1> param1, final Result<T2> param2) {
        return new ResultTuple2<T1, T2>() {
            @Override
            public <T> T map(final FN2<T, Result<T1>, Result<T2>> mapper) {
                return mapper.apply(param1, param2);
            }
        };
    }

    static <T1, T2, T3> ResultTuple3<T1, T2, T3> of(final Result<T1> param1, final Result<T2> param2, final Result<T3> param3) {
        return new ResultTuple3<T1, T2, T3>() {
            @Override
            public <T> T map(final FN3<T, Result<T1>, Result<T2>, Result<T3>> mapper) {
                return mapper.apply(param1, param2, param3);
            }
        };
    }

    static <T1, T2, T3, T4> ResultTuple4<T1, T2, T3, T4> of(final Result<T1> param1, final Result<T2> param2, final Result<T3> param3,
                                                            final Result<T4> param4) {
        return new ResultTuple4<T1, T2, T3, T4>() {
            @Override
            public <T> T map(final FN4<T, Result<T1>, Result<T2>, Result<T3>, Result<T4>> mapper) {
                return mapper.apply(param1, param2, param3, param4);
            }
        };
    }

    static <T1, T2, T3, T4, T5> ResultTuple5<T1, T2, T3, T4, T5> of(final Result<T1> param1, final Result<T2> param2, final Result<T3> param3,
                                                                    final Result<T4> param4, final Result<T5> param5) {
        return new ResultTuple5<T1, T2, T3, T4, T5>() {
            @Override
            public <T> T map(final FN5<T, Result<T1>, Result<T2>, Result<T3>, Result<T4>, Result<T5>> mapper) {
                return mapper.apply(param1, param2, param3, param4, param5);
            }
        };
    }

    static <T1, T2, T3, T4, T5, T6> ResultTuple6<T1, T2, T3, T4, T5, T6> of(final Result<T1> param1, final Result<T2> param2, final Result<T3> param3,
                                                                            final Result<T4> param4, final Result<T5> param5, final Result<T6> param6) {
        return new ResultTuple6<T1, T2, T3, T4, T5, T6>() {
            @Override
            public <T> T map(final FN6<T, Result<T1>, Result<T2>, Result<T3>, Result<T4>, Result<T5>, Result<T6>> mapper) {
                return mapper.apply(param1, param2, param3, param4, param5, param6);
            }
        };
    }

    static <T1, T2, T3, T4, T5, T6, T7> ResultTuple7<T1, T2, T3, T4, T5, T6, T7> of(final Result<T1> param1, final Result<T2> param2, final Result<T3> param3,
                                                                                    final Result<T4> param4, final Result<T5> param5, final Result<T6> param6,
                                                                                    final Result<T7> param7) {
        return new ResultTuple7<T1, T2, T3, T4, T5, T6, T7>() {
            @Override
            public <T> T map(final FN7<T, Result<T1>, Result<T2>, Result<T3>, Result<T4>, Result<T5>, Result<T6>, Result<T7>> mapper) {
                return mapper.apply(param1, param2, param3, param4, param5, param6, param7);
            }
        };
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8> ResultTuple8<T1, T2, T3, T4, T5, T6, T7, T8> of(final Result<T1> param1, final Result<T2> param2, final Result<T3> param3,
                                                                                            final Result<T4> param4, final Result<T5> param5, final Result<T6> param6,
                                                                                            final Result<T7> param7, final Result<T8> param8) {
        return new ResultTuple8<T1, T2, T3, T4, T5, T6, T7, T8>() {
            @Override
            public <T> T map(final FN8<T, Result<T1>, Result<T2>, Result<T3>, Result<T4>, Result<T5>, Result<T6>, Result<T7>, Result<T8>> mapper) {
                return mapper.apply(param1, param2, param3, param4, param5, param6, param7, param8);
            }
        };
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8, T9> ResultTuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> of(final Result<T1> param1, final Result<T2> param2, final Result<T3> param3,
                                                                                                    final Result<T4> param4, final Result<T5> param5, final Result<T6> param6,
                                                                                                    final Result<T7> param7, final Result<T8> param8, final Result<T9> param9) {
        return new ResultTuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>() {
            @Override
            public <T> T map(final FN9<T, Result<T1>, Result<T2>, Result<T3>, Result<T4>, Result<T5>, Result<T6>, Result<T7>, Result<T8>, Result<T9>> mapper) {
                return mapper.apply(param1, param2, param3, param4, param5, param6, param7, param8, param9);
            }
        };
    }

    static <T1> ResultTuple1<T1> of(final Tuple1<Result<T1>> value) {
        return value.map(ResultTuple::of);
    }

    static <T1, T2> ResultTuple2<T1, T2> of(final Tuple2<Result<T1>, Result<T2>> value) {
        return value.map(ResultTuple::of);
    }

    static <T1, T2, T3> ResultTuple3<T1, T2, T3> of(final Tuple3<Result<T1>, Result<T2>, Result<T3>> value) {
        return value.map(ResultTuple::of);
    }

    static <T1, T2, T3, T4> ResultTuple4<T1, T2, T3, T4> of(final Tuple4<Result<T1>, Result<T2>, Result<T3>, Result<T4>> value) {
        return value.map(ResultTuple::of);
    }

    static <T1, T2, T3, T4, T5> ResultTuple5<T1, T2, T3, T4, T5> of(final Tuple5<Result<T1>, Result<T2>, Result<T3>,
                                                                                 Result<T4>, Result<T5>> value) {
        return value.map(ResultTuple::of);
    }

    static <T1, T2, T3, T4, T5, T6> ResultTuple6<T1, T2, T3, T4, T5, T6> of(final Tuple6<Result<T1>, Result<T2>, Result<T3>,
                                                                                         Result<T4>, Result<T5>, Result<T6>> value) {
        return value.map(ResultTuple::of);
    }

    static <T1, T2, T3, T4, T5, T6, T7> ResultTuple7<T1, T2, T3, T4, T5, T6, T7> of(final Tuple7<Result<T1>, Result<T2>, Result<T3>,
                                                                                                 Result<T4>, Result<T5>, Result<T6>,
                                                                                                 Result<T7>> value) {
        return value.map(ResultTuple::of);
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8> ResultTuple8<T1, T2, T3, T4, T5, T6, T7, T8> of(final Tuple8<Result<T1>, Result<T2>, Result<T3>,
                                                                                                         Result<T4>, Result<T5>, Result<T6>,
                                                                                                         Result<T7>, Result<T8>> value) {
        return value.map(ResultTuple::of);
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8, T9> ResultTuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> of(final Tuple9<Result<T1>, Result<T2>, Result<T3>,
                                                                                                                 Result<T4>, Result<T5>, Result<T6>,
                                                                                                                 Result<T7>, Result<T8>, Result<T9>> value) {
        return value.map(ResultTuple::of);
    }

    interface ResultTuple1<T1> extends Tuple1<Result<T1>>,
                                       Zipable<Result1<T1>> {
        @Override
        default Result1<T1> zip() {
            return map(value -> Result1.from(value.flatMap(vv1 -> success(with(vv1)))));
        }
    }

    interface ResultTuple2<T1, T2> extends Tuple2<Result<T1>, Result<T2>>,
                                           Zipable<Result2<T1, T2>> {
        @Override
        default Result2<T1, T2> zip() {
            return map((value1, value2) ->
                               Result2.from(value1.flatMap(vv1 ->
                                             value2.flatMap(vv2 -> success(with(vv1, vv2))))));
        }
    }

    interface ResultTuple3<T1, T2, T3> extends Tuple3<Result<T1>, Result<T2>, Result<T3>>,
                                               Zipable<Result3<T1, T2, T3>> {
        @Override
        default Result3<T1, T2, T3> zip() {
            return map((value1, value2, value3) ->
                               Result3.from(value1.flatMap(vv1 ->
                                             value2.flatMap(vv2 ->
                                              value3.flatMap(vv3 -> success(with(vv1, vv2, vv3)))))));
        }
    }

    interface ResultTuple4<T1, T2, T3, T4> extends Tuple4<Result<T1>, Result<T2>, Result<T3>, Result<T4>>,
                                                   Zipable<Result4<T1, T2, T3, T4>> {
        @Override
        default Result4<T1, T2, T3, T4> zip() {
            return map((value1, value2, value3, value4) ->
                               Result4.from(value1.flatMap(vv1 ->
                                             value2.flatMap(vv2 ->
                                              value3.flatMap(vv3 ->
                                               value4.flatMap(vv4 -> success(with(vv1, vv2, vv3, vv4))))))));
        }
    }

    interface ResultTuple5<T1, T2, T3, T4, T5> extends Tuple5<Result<T1>, Result<T2>, Result<T3>, Result<T4>, Result<T5>>,
                                                       Zipable<Result5<T1, T2, T3, T4, T5>> {
        @Override
        default Result5<T1, T2, T3, T4, T5> zip() {
            return map((value1, value2, value3, value4, value5) ->
                               Result5.from(value1.flatMap(vv1 ->
                                             value2.flatMap(vv2 ->
                                              value3.flatMap(vv3 ->
                                               value4.flatMap(vv4 ->
                                                value5.flatMap(vv5 -> success(with(vv1, vv2, vv3, vv4, vv5)))))))));
        }
    }

    interface ResultTuple6<T1, T2, T3, T4, T5, T6> extends Tuple6<Result<T1>, Result<T2>, Result<T3>, Result<T4>, Result<T5>, Result<T6>>,
                                                           Zipable<Result6<T1, T2, T3, T4, T5, T6>> {
        @Override
        default Result6<T1, T2, T3, T4, T5, T6> zip() {
            return map((value1, value2, value3, value4, value5, value6) ->
                               Result6.from(value1.flatMap(vv1 ->
                                             value2.flatMap(vv2 ->
                                              value3.flatMap(vv3 ->
                                               value4.flatMap(vv4 ->
                                                value5.flatMap(vv5 ->
                                                 value6.flatMap(vv6 -> success(with(vv1, vv2, vv3, vv4, vv5, vv6))))))))));
        }
    }

    interface ResultTuple7<T1, T2, T3, T4, T5, T6, T7> extends Tuple7<Result<T1>, Result<T2>, Result<T3>, Result<T4>, Result<T5>, Result<T6>, Result<T7>>,
                                                               Zipable<Result7<T1, T2, T3, T4, T5, T6, T7>> {
        @Override
        default Result7<T1, T2, T3, T4, T5, T6, T7> zip() {
            return map((value1, value2, value3, value4, value5, value6, value7) ->
                               Result7.from(value1.flatMap(vv1 ->
                                             value2.flatMap(vv2 ->
                                              value3.flatMap(vv3 ->
                                               value4.flatMap(vv4 ->
                                                value5.flatMap(vv5 ->
                                                 value6.flatMap(vv6 ->
                                                  value7.flatMap(vv7 -> success(with(vv1, vv2, vv3, vv4, vv5, vv6, vv7)))))))))));
        }
    }

    interface ResultTuple8<T1, T2, T3, T4, T5, T6, T7, T8> extends Tuple8<Result<T1>, Result<T2>, Result<T3>, Result<T4>, Result<T5>, Result<T6>, Result<T7>, Result<T8>>,
                                                                   Zipable<Result8<T1, T2, T3, T4, T5, T6, T7, T8>> {
        @Override
        default Result8<T1, T2, T3, T4, T5, T6, T7, T8> zip() {
            return map((value1, value2, value3, value4, value5, value6, value7, value8) ->
                               Result8.from(value1.flatMap(vv1 ->
                                             value2.flatMap(vv2 ->
                                              value3.flatMap(vv3 ->
                                               value4.flatMap(vv4 ->
                                                value5.flatMap(vv5 ->
                                                 value6.flatMap(vv6 ->
                                                  value7.flatMap(vv7 ->
                                                   value8.flatMap(vv8 -> success(with(vv1, vv2, vv3, vv4, vv5, vv6, vv7, vv8))))))))))));
        }
    }

    interface ResultTuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends Tuple9<Result<T1>, Result<T2>, Result<T3>, Result<T4>, Result<T5>, Result<T6>, Result<T7>, Result<T8>, Result<T9>>,
                                                                       Zipable<Result9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> {
        @Override
        default Result9<T1, T2, T3, T4, T5, T6, T7, T8, T9> zip() {
            return map((value1, value2, value3, value4, value5, value6, value7, value8, value9) ->
                               Result9.from(value1.flatMap(vv1 ->
                                             value2.flatMap(vv2 ->
                                              value3.flatMap(vv3 ->
                                               value4.flatMap(vv4 ->
                                                value5.flatMap(vv5 ->
                                                 value6.flatMap(vv6 ->
                                                  value7.flatMap(vv7 ->
                                                   value8.flatMap(vv8 ->
                                                    value9.flatMap(vv9 -> success(with(vv1, vv2, vv3, vv4, vv5, vv6, vv7, vv8, vv9)))))))))))));
        }
    }
}
