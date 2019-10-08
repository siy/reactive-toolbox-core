package org.reactivetoolbox.core.async;

import org.reactivetoolbox.core.async.impl.PromiseImpl;
import org.reactivetoolbox.core.functional.Functions.FN1;
import org.reactivetoolbox.core.functional.Option;
import org.reactivetoolbox.core.scheduler.Timeout;

import java.util.List;
import java.util.function.Consumer;

/**
 * Simple and lightweight Promise implementation
 *
 * @param <T>
 *        Type of contained value
 * @see PromiseAll - for Promise's which depend on several Promises
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
     * Run specified task asynchronously. Current instance of {@link Promise} is passed to the task as a parameter.
     *
     * @param task
     *        Task to execute with this promise
     *
     * @return Current instance
     */
    Promise<T> async(final Consumer<Promise<T>> task);

    /**
     * Run specified task asynchronously after specified timeout expires. Current instance of {@link Promise} is passed
     * to the task as a parameter.
     *
     * @param task
     *        Task to execute with this promise
     *
     * @return Current instance
     */
    Promise<T> async(final Timeout timeout, final Consumer<Promise<T>> task);

    /**
     * Create new promise which will be resolved when current instance will be resolved. The value with which
     * new instance will be resolved will be computed from current instance value by application of provided mapper.
     *
     * @param mapper
     *        Function to apply to current instance value upon resolution
     * @return created instance
     */
    default <R> Promise<R> map(final FN1<R, T> mapper) {
        return Promise.give(promise -> then(val -> promise.resolve(mapper.apply(val))));
    }

    /**
     * Create new promise which will be resolved when will be resolved promise returned by mapping function. The invocation
     * of mapping function will performed when current instance will be resolved and resolution value will be passed
     * as argument to mapping function. Overall this method is necessary to postpone invocation of mapping function
     * until current instance will be resolved.
     *
     * @param mapper
     *        Function to invoke with current instance value upon resolution
     * @return created instance
     */
    default <R> Promise<R> flatMap(final FN1<Promise<R>, T> mapper) {
        return Promise.give(promise -> then(val -> mapper.apply(val).then(promise::resolve)));
    }

    /**
     * Create and empty (unresolved) promise
     *
     * @return created promise
     */
    static <T> Promise<T> give() {
        return new PromiseImpl<>();
    }

    static <T> Promise<T> give(final Consumer<Promise<T>> consumer) {
        final Promise<T> promise = new PromiseImpl<>();
        consumer.accept(promise);
        return promise;
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
        return Promise.give(result -> List.of(promises)
                                          .forEach(promise -> promise.then(result::resolve)));
    }
}
