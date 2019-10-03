package org.reactivetoolbox.core.async;

import org.reactivetoolbox.core.async.impl.PromiseImpl;
import org.reactivetoolbox.core.functional.Functions.FN1;
import org.reactivetoolbox.core.functional.Option;
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
import org.reactivetoolbox.core.functional.Tuple;
import org.reactivetoolbox.core.functional.Tuple.Tuple1;
import org.reactivetoolbox.core.functional.Tuple.Tuple2;
import org.reactivetoolbox.core.functional.Tuple.Tuple3;
import org.reactivetoolbox.core.functional.Tuple.Tuple4;
import org.reactivetoolbox.core.functional.Tuple.Tuple5;
import org.reactivetoolbox.core.functional.Tuple.Tuple6;
import org.reactivetoolbox.core.functional.Tuple.Tuple7;
import org.reactivetoolbox.core.functional.Tuple.Tuple8;
import org.reactivetoolbox.core.functional.Tuple.Tuple9;
import org.reactivetoolbox.core.functional.TupleResult;
import org.reactivetoolbox.core.scheduler.Timeout;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Simple and lightweight Promise
 *
 * @param <T>
 *        Type of contained value
 */
public interface Promise<T> {
    /**
     * Retrieve value from this instance.
     *
     * @return {@link Option} filled with value if instance is resolved or empty if instance is still not resolved
     */
    Option<T> value();

    /**
     * Check if instance is resolved.
     *
     * @return <code>true</code> if instance is resolved and <code>false</code> if not
     */
    boolean ready();

    /**
     * Resolve the promise by passing non-null value to it. All actions already
     * waiting for resolution will be executed upon return from the call to
     * this method and in the context of current thread.
     *
     * @param result
     *        The value which will be stored in this instance and make it resolved
     *
     * @return Current instance
     */
    Promise<T> resolve(final T result);

    /**
     * Resolve the promise by passing non-null value to it. All actions already
     * waiting for resolution will be scheduled for asynchronous execution.
     *
     * @param result
     *        The value which will be stored in this instance and make it resolved
     *
     * @return Current instance
     */
    Promise<T> resolveAsync(final T result);

    /**
     * Perform user-provided action once this instance will be resolved. Action will be executed once regardless
     * if instance is resolved or not. User may add as many actions as necessary.
     *
     * @param action
     *        Action to perform
     * @return Current instance
     */
    Promise<T> then(final Consumer<T> action);

    /**
     * Create new promise which will be resolved when current instance will be resolved. The value with which
     * new instance will be resolved will be computed from current instance value by application of provided mapper.
     *
     * @param mapper
     *        Function to apply to current instance value upon resolution
     * @return created instance
     */
    <R> Promise<R> map(FN1<R, T> mapper);

    /**
     * Synchronously wait for this instance resolution.
     * Note that this method is not recommended to use for following reasons:
     * <ul>
     *     <li>Synchronous waiting blocks the current thread</li>
     *     <li>If current thread will be {@link Thread#interrupt() interrupted} with other thread, then method may
     *     return non-resolved instance. This may lead to subtle and hard to debug issues.</li>
     * </ul>.
     *
     * @return Current instance
     */
    Promise<T> syncWait();

    /**
     * Synchronously wait for this instance resolution or timeout.
     *
     * Note that this method is not recommended to use because synchronous waiting blocks the current thread.
     * Keep in mind that this method may exit without instance being resolved.
     *
     * @param timeout
     *        Timeout amount
     * @return Current instance
     */
    Promise<T> syncWait(final Timeout timeout);

    /**
     * Set timeout for instance resolution. When timeout expires, instance will be resolved with specified timeout
     * result.
     *
     * @param timeout
     *        Timeout amount
     * @param timeoutResult
     *        Resolution value in case of timeout
     * @return Current instance
     */
    Promise<T> with(final Timeout timeout, final T timeoutResult);

    /**
     * Set timeout for instance resolution. When timeout expires, instance will be resolved with value returned by
     * provided supplier. Resolution value is lazily evaluated.
     *
     * @param timeout
     *        Timeout amount
     * @param timeoutResultSupplier
     *        Supplier of resolution value in case of timeout
     * @return Current instance
     */
    Promise<T> with(final Timeout timeout, final Supplier<T> timeoutResultSupplier);

    /**
     * Run specified task asynchronously. Current instance of {@link Promise} is passed to the task as a parameter.
     * Note that it is expected that by the time of invocation of this method, instance has it's timeout already
     * configured. If this is not the case, then task may run (theoretically) indefinitely.
     *
     * @param task
     *        Task to execute with this promise
     *
     * @return Current instance
     */
    Promise<T> async(final Consumer<Promise<T>> task);

    /**
     * Create and empty (unresolved) promise
     *
     * @return created promise
     */
    static <T> Promise<T> give() {
        return new PromiseImpl<>();
    }

    static <T> Promise<Result<T>> result() {
        return new PromiseImpl<>();
    }

    //TODO: add Class and TypeToken versions
    static <T1> Promise<Result1<T1>> result1() {
        return new PromiseImpl<>();
    }

    static <T1, T2> Promise<Result2<T1, T2>> result2() {
        return new PromiseImpl<>();
    }

    static <T1, T2, T3> Promise<Result3<T1, T2, T3>> result3() {
        return new PromiseImpl<>();
    }

    static <T1, T2, T3, T4> Promise<Result4<T1, T2, T3, T4>> result4() {
        return new PromiseImpl<>();
    }

    static <T1, T2, T3, T4, T5> Promise<Result5<T1, T2, T3, T4, T5>> result5() {
        return new PromiseImpl<>();
    }

    static <T1, T2, T3, T4, T5, T6> Promise<Result6<T1, T2, T3, T4, T5, T6>> result6() {
        return new PromiseImpl<>();
    }

    static <T1, T2, T3, T4, T5, T6, T7> Promise<Result7<T1, T2, T3, T4, T5, T6, T7>> result7() {
        return new PromiseImpl<>();
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8> Promise<Result8<T1, T2, T3, T4, T5, T6, T7, T8>> result8() {
        return new PromiseImpl<>();
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Promise<Result9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> result9() {
        return new PromiseImpl<>();
    }

    static <T> Promise<Result<T>> result(final Class<T> clazz) {
        return new PromiseImpl<>();
    }

    /**
     * Create an filled (resolved) promise
     *
     * @param value
     *        value to resolve created instance
     * @return created promise
     */
    static <T> Promise<T> fulfilled(final T value) {
        return Promise.<T>give().resolve(value);
    }

    /**
     * Create instance which will be resolved once any of the promises
     * provided as a parameters will be resolved
     *
     * @param promises
     *        Input promises
     *
     * @return created instance
     */
    @SafeVarargs
    static <T> Promise<T> any(final Promise<T>... promises) {
        final var result = Promise.<T>give();
        List.of(promises)
            .forEach(promise -> promise.then(result::resolve));
        return result;
    }

    /**
     * Create a promise which will be resolved when all promises provided as a parameters will be resolved.
     * Upon resolution returned promised will contain {@link Tuple} with all values from input promises.
     *
     * @param promise
     *        Input promise
     * @return Created promise
     */
    @SuppressWarnings("unchecked")
    static <T1> Promise<Tuple1<T1>> all(final Promise<T1> promise) {
        return zipper(values -> Tuple.with((T1) values[0]),
                      t -> t,
                      promise);
    }

    /**
     * Create a promise which will be resolved when all promises provided as a parameters will be resolved.
     * Upon resolution returned promised will contain {@link Tuple} with all values from input promises.
     *
     * @param promise1
     *        Input promise #1
     * @param promise2
     *        Input promise #2
     * @return Created promise
     */
    @SuppressWarnings("unchecked")
    static <T1, T2> Promise<Tuple2<T1, T2>> all(final Promise<T1> promise1,
                                                final Promise<T2> promise2) {
        return zipper(values -> Tuple.with((T1) values[0],
                                           (T2) values[1]),
                      t -> t,
                      promise1, promise2);
    }

    /**
     * Create a promise which will be resolved when all promises provided as a parameters will be resolved.
     * Upon resolution returned promised will contain {@link Tuple} with all values from input promises.
     *
     * @param promise1
     *        Input promise #1
     * @param promise2
     *        Input promise #2
     * @param promise3
     *        Input promise #3
     * @return Created promise
     */
    @SuppressWarnings("unchecked")
    static <T1, T2, T3> Promise<Tuple3<T1, T2, T3>> all(final Promise<T1> promise1,
                                                        final Promise<T2> promise2,
                                                        final Promise<T3> promise3) {
        return zipper(values -> Tuple.with((T1) values[0], (T2) values[1], (T3) values[2]),
                      t -> t,
                      promise1, promise2, promise3);
    }

    /**
     * Create a promise which will be resolved when all promises provided as a parameters will be resolved.
     * Upon resolution returned promised will contain {@link Tuple} with all values from input promises.
     *
     * @param promise1
     *        Input promise #1
     * @param promise2
     *        Input promise #2
     * @param promise3
     *        Input promise #3
     * @param promise4
     *        Input promise #4
     * @return Created promise
     */
    @SuppressWarnings("unchecked")
    static <T1, T2, T3, T4> Promise<Tuple4<T1, T2, T3, T4>> all(final Promise<T1> promise1,
                                                                final Promise<T2> promise2,
                                                                final Promise<T3> promise3,
                                                                final Promise<T4> promise4) {
        return zipper(values -> Tuple.with((T1) values[0], (T2) values[1], (T3) values[2], (T4) values[3]),
                      t -> t,
                      promise1, promise2, promise3, promise4);
    }

    /**
     * Create a promise which will be resolved when all promises provided as a parameters will be resolved.
     * Upon resolution returned promised will contain {@link Tuple} with all values from input promises.
     *
     * @param promise1
     *        Input promise #1
     * @param promise2
     *        Input promise #2
     * @param promise3
     *        Input promise #3
     * @param promise4
     *        Input promise #4
     * @param promise5
     *        Input promise #5
     * @return Created promise
     */
    @SuppressWarnings("unchecked")
    static <T1, T2, T3, T4, T5> Promise<Tuple5<T1, T2, T3, T4, T5>> all(final Promise<T1> promise1,
                                                                        final Promise<T2> promise2,
                                                                        final Promise<T3> promise3,
                                                                        final Promise<T4> promise4,
                                                                        final Promise<T5> promise5) {
        return zipper(values -> Tuple.with((T1) values[0], (T2) values[1], (T3) values[2], (T4) values[3], (T5) values[4]),
                      t -> t,
                      promise1, promise2, promise3, promise4, promise5);
    }

    /**
     * Create a promise which will be resolved when all promises provided as a parameters will be resolved.
     * Upon resolution returned promised will contain {@link Tuple} with all values from input promises.
     *
     * @param promise1
     *        Input promise #1
     * @param promise2
     *        Input promise #2
     * @param promise3
     *        Input promise #3
     * @param promise4
     *        Input promise #4
     * @param promise5
     *        Input promise #5
     * @param promise6
     *        Input promise #6
     * @return Created promise
     */
    @SuppressWarnings("unchecked")
    static <T1, T2, T3, T4, T5, T6> Promise<Tuple6<T1, T2, T3, T4, T5, T6>> all(final Promise<T1> promise1,
                                                                                final Promise<T2> promise2,
                                                                                final Promise<T3> promise3,
                                                                                final Promise<T4> promise4,
                                                                                final Promise<T5> promise5,
                                                                                final Promise<T6> promise6) {
        return zipper(values -> Tuple.with((T1) values[0], (T2) values[1], (T3) values[2], (T4) values[3],
                                           (T5) values[4], (T6) values[5]),
                      t -> t,
                      promise1, promise2, promise3, promise4, promise5, promise6);
    }

    /**
     * Create a promise which will be resolved when all promises provided as a parameters will be resolved.
     * Upon resolution returned promised will contain {@link Tuple} with all values from input promises.
     *
     * @param promise1
     *        Input promise #1
     * @param promise2
     *        Input promise #2
     * @param promise3
     *        Input promise #3
     * @param promise4
     *        Input promise #4
     * @param promise5
     *        Input promise #5
     * @param promise6
     *        Input promise #6
     * @param promise7
     *        Input promise #7
     * @return Created promise
     */
    @SuppressWarnings("unchecked")
    static <T1, T2, T3, T4, T5, T6, T7> Promise<Tuple7<T1, T2, T3, T4, T5, T6, T7>> all(final Promise<T1> promise1,
                                                                                        final Promise<T2> promise2,
                                                                                        final Promise<T3> promise3,
                                                                                        final Promise<T4> promise4,
                                                                                        final Promise<T5> promise5,
                                                                                        final Promise<T6> promise6,
                                                                                        final Promise<T7> promise7) {
        return zipper(values -> Tuple.with((T1) values[0], (T2) values[1], (T3) values[2], (T4) values[3],
                                           (T5) values[4], (T6) values[5], (T7) values[6]),
                      t -> t,
                      promise1, promise2, promise3, promise4, promise5, promise6, promise7);
    }

    /**
     * Create a promise which will be resolved when all promises provided as a parameters will be resolved.
     * Upon resolution returned promised will contain {@link Tuple} with all values from input promises.
     *
     * @param promise1
     *        Input promise #1
     * @param promise2
     *        Input promise #2
     * @param promise3
     *        Input promise #3
     * @param promise4
     *        Input promise #4
     * @param promise5
     *        Input promise #5
     * @param promise6
     *        Input promise #6
     * @param promise7
     *        Input promise #7
     * @param promise8
     *        Input promise #8
     * @return Created promise
     */
    @SuppressWarnings("unchecked")
    static <T1, T2, T3, T4, T5, T6, T7, T8> Promise<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> all(final Promise<T1> promise1,
                                                                                                final Promise<T2> promise2,
                                                                                                final Promise<T3> promise3,
                                                                                                final Promise<T4> promise4,
                                                                                                final Promise<T5> promise5,
                                                                                                final Promise<T6> promise6,
                                                                                                final Promise<T7> promise7,
                                                                                                final Promise<T8> promise8) {
        return zipper(values -> Tuple.with((T1) values[0], (T2) values[1], (T3) values[2], (T4) values[3],
                                           (T5) values[4], (T6) values[5], (T7) values[6], (T8) values[7]),
                      t -> t,
                      promise1, promise2, promise3, promise4, promise5, promise6, promise7, promise8);
    }

    /**
     * Create a promise which will be resolved when all promises provided as a parameters will be resolved.
     * Upon resolution returned promised will contain {@link Tuple} with all values from input promises.
     *
     * @param promise1
     *        Input promise #1
     * @param promise2
     *        Input promise #2
     * @param promise3
     *        Input promise #3
     * @param promise4
     *        Input promise #4
     * @param promise5
     *        Input promise #5
     * @param promise6
     *        Input promise #6
     * @param promise7
     *        Input promise #7
     * @param promise8
     *        Input promise #8
     * @param promise9
     *        Input promise #9
     * @return Created promise
     */
    @SuppressWarnings("unchecked")
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Promise<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> all(final Promise<T1> promise1,
                                                                                                        final Promise<T2> promise2,
                                                                                                        final Promise<T3> promise3,
                                                                                                        final Promise<T4> promise4,
                                                                                                        final Promise<T5> promise5,
                                                                                                        final Promise<T6> promise6,
                                                                                                        final Promise<T7> promise7,
                                                                                                        final Promise<T8> promise8,
                                                                                                        final Promise<T9> promise9) {
        return zipper(values -> Tuple.with((T1) values[0], (T2) values[1], (T3) values[2], (T4) values[3],
                                           (T5) values[4], (T6) values[5], (T7) values[6], (T8) values[7],
                                           (T9) values[8]),
                      t -> t,
                      promise1, promise2, promise3, promise4, promise5, promise6, promise7, promise8, promise9);
    }

    @SuppressWarnings("unchecked")
    static <T1> Promise<Result1<T1>> allOf(final Promise<Result<T1>> promise1) {
        return zipper(values -> Tuple.with((Result<T1>) values[0]),
                      tuple -> TupleResult.of(tuple).zip(),
                      promise1);
    }

    @SuppressWarnings("unchecked")
    static <T1, T2> Promise<Result2<T1, T2>> allOf(final Promise<Result<T1>> promise1,
                                                   final Promise<Result<T2>> promise2) {
        return zipper(values -> Tuple.with((Result<T1>) values[0], (Result<T2>) values[1]),
                      tuple -> TupleResult.of(tuple).zip(),
                      promise1, promise2);
    }

    @SuppressWarnings("unchecked")
    static <T1, T2, T3> Promise<Result3<T1, T2, T3>> allOf(final Promise<Result<T1>> promise1,
                                                           final Promise<Result<T2>> promise2,
                                                           final Promise<Result<T3>> promise3) {
        return zipper(values -> Tuple.with((Result<T1>) values[0], (Result<T2>) values[1], (Result<T3>) values[2]),
                      tuple -> TupleResult.of(tuple).zip(),
                      promise1, promise2, promise3);
    }

    @SuppressWarnings("unchecked")
    static <T1, T2, T3, T4> Promise<Result4<T1, T2, T3, T4>> allOf(final Promise<Result<T1>> promise1,
                                                                   final Promise<Result<T2>> promise2,
                                                                   final Promise<Result<T3>> promise3,
                                                                   final Promise<Result<T4>> promise4) {
        return zipper(values -> Tuple.with((Result<T1>) values[0], (Result<T2>) values[1], (Result<T3>) values[2], (Result<T4>) values[3]),
                      tuple -> TupleResult.of(tuple).zip(),
                      promise1, promise2, promise3, promise4);
    }

    @SuppressWarnings("unchecked")
    static <T1, T2, T3, T4, T5> Promise<Result5<T1, T2, T3, T4, T5>> allOf(final Promise<Result<T1>> promise1,
                                                                           final Promise<Result<T2>> promise2,
                                                                           final Promise<Result<T3>> promise3,
                                                                           final Promise<Result<T4>> promise4,
                                                                           final Promise<Result<T5>> promise5) {
        return zipper(values -> Tuple.with((Result<T1>) values[0], (Result<T2>) values[1], (Result<T3>) values[2], (Result<T4>) values[3],
                                           (Result<T5>) values[4]),
                      tuple -> TupleResult.of(tuple).zip(),
                      promise1, promise2, promise3, promise4, promise5);
    }

    @SuppressWarnings("unchecked")
    static <T1, T2, T3, T4, T5, T6> Promise<Result6<T1, T2, T3, T4, T5, T6>> allOf(final Promise<Result<T1>> promise1,
                                                                                   final Promise<Result<T2>> promise2,
                                                                                   final Promise<Result<T3>> promise3,
                                                                                   final Promise<Result<T4>> promise4,
                                                                                   final Promise<Result<T5>> promise5,
                                                                                   final Promise<Result<T6>> promise6) {
        return zipper(values -> Tuple.with((Result<T1>) values[0], (Result<T2>) values[1], (Result<T3>) values[2], (Result<T4>) values[3],
                                           (Result<T5>) values[4], (Result<T6>) values[5]),
                      tuple -> TupleResult.of(tuple).zip(),
                      promise1, promise2, promise3, promise4, promise5, promise6);
    }

    @SuppressWarnings("unchecked")
    static <T1, T2, T3, T4, T5, T6, T7> Promise<Result7<T1, T2, T3, T4, T5, T6, T7>> allOf(final Promise<Result<T1>> promise1,
                                                                                           final Promise<Result<T2>> promise2,
                                                                                           final Promise<Result<T3>> promise3,
                                                                                           final Promise<Result<T4>> promise4,
                                                                                           final Promise<Result<T5>> promise5,
                                                                                           final Promise<Result<T6>> promise6,
                                                                                           final Promise<Result<T7>> promise7) {
        return zipper(values -> Tuple.with((Result<T1>) values[0], (Result<T2>) values[1], (Result<T3>) values[2], (Result<T4>) values[3],
                                           (Result<T5>) values[4], (Result<T6>) values[5], (Result<T7>) values[6]),
                      tuple -> TupleResult.of(tuple).zip(),
                      promise1, promise2, promise3, promise4, promise5, promise6, promise7);
    }

    @SuppressWarnings("unchecked")
    static <T1, T2, T3, T4, T5, T6, T7, T8> Promise<Result8<T1, T2, T3, T4, T5, T6, T7, T8>> allOf(final Promise<Result<T1>> promise1,
                                                                                                   final Promise<Result<T2>> promise2,
                                                                                                   final Promise<Result<T3>> promise3,
                                                                                                   final Promise<Result<T4>> promise4,
                                                                                                   final Promise<Result<T5>> promise5,
                                                                                                   final Promise<Result<T6>> promise6,
                                                                                                   final Promise<Result<T7>> promise7,
                                                                                                   final Promise<Result<T8>> promise8) {
        return zipper(values -> Tuple.with((Result<T1>) values[0], (Result<T2>) values[1], (Result<T3>) values[2], (Result<T4>) values[3],
                                           (Result<T5>) values[4], (Result<T6>) values[5], (Result<T7>) values[6], (Result<T8>) values[7]),
                      tuple -> TupleResult.of(tuple).zip(),
                      promise1, promise2, promise3, promise4, promise5, promise6, promise7, promise8);
    }

    @SuppressWarnings("unchecked")
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Promise<Result9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> allOf(final Promise<Result<T1>> promise1,
                                                                                                           final Promise<Result<T2>> promise2,
                                                                                                           final Promise<Result<T3>> promise3,
                                                                                                           final Promise<Result<T4>> promise4,
                                                                                                           final Promise<Result<T5>> promise5,
                                                                                                           final Promise<Result<T6>> promise6,
                                                                                                           final Promise<Result<T7>> promise7,
                                                                                                           final Promise<Result<T8>> promise8,
                                                                                                           final Promise<Result<T9>> promise9) {
        return zipper(values -> Tuple.with((Result<T1>) values[0], (Result<T2>) values[1], (Result<T3>) values[2], (Result<T4>) values[3],
                                           (Result<T5>) values[4], (Result<T6>) values[5], (Result<T7>) values[6], (Result<T8>) values[7],
                                           (Result<T9>) values[8]),
                      tuple -> tuple.map(TupleResult::of).zip(),
                      promise1, promise2, promise3, promise4, promise5, promise6, promise7, promise8, promise9);
    }

    /**
     * Main worker method behind all {@code all(...)} and {@code zipAll(...)} methods.
     * This method is not intended for external use.
     *
     * @param tupleBuilder
     *        Function which transforms array of result values into single output value
     * @param promises
     *        Promises whose values created promise will be waiting for
     * @return created promise
     */
    //TODO: switch to tuples?
     static <R, T> Promise<R> zipper(final FN1<T, Object[]> tupleBuilder, final FN1<R, T> valueTransformer, final Promise<?>... promises) {
        final var values = new Object[promises.length];
        final var result = Promise.<R>give();
        final var thresholdAction = ActionableThreshold.of(promises.length,
                                                           () -> result.resolve(valueTransformer.apply(tupleBuilder.apply(values))));

        int i = 0;
        for (final var promise : promises) {
            final var index = i;

            promise.then(value -> {
                values[index] = value;
                thresholdAction.registerEvent();
            });
            i++;
        }
        return result;
    }
}
