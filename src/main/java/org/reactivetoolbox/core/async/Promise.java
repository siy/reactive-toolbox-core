package org.reactivetoolbox.core.async;

import org.reactivetoolbox.core.async.impl.PromiseImpl;
import org.reactivetoolbox.core.functional.Functions.FN1;
import org.reactivetoolbox.core.functional.Option;
import org.reactivetoolbox.core.functional.Result;
import org.reactivetoolbox.core.scheduler.Timeout;
import org.reactivetoolbox.core.type.TypeToken;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Simple and lightweight Promise implementation
 *
 * @param <T>
 *        Type of contained value
 * @see PromiseAll - for Promise's which depend on several Promises
 * @see PromiseResult - for Promise's which depend on several Promises containing {@link Result}
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
     * Convenience method for performing some actions with current promise instance. Useful for cases
     * when there is (quite typical) sequence: create unresolved promise -> setup -> return created promise.
     *
     * @param consumer
     *        Action to perform on current instance.
     * @return Current instance
     */
    default Promise<T> apply(final Consumer<Promise<T>> consumer) {
        consumer.accept(this);
        return this;
    }

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
    default <R> Promise<R> map(final FN1<R, T> mapper) {
        return Promise.<R>give().apply(promise -> then(val -> promise.resolve(mapper.apply(val))));
    }

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

    static <T> Promise<T> me(final Class<T> $) {
        return new PromiseImpl<>();
    }

    static <T> Promise<T> me(final TypeToken<T> $) {
        return new PromiseImpl<>();
    }

    static <T> Promise<Result<T>> result() {
        return new PromiseImpl<>();
    }

    static <T> Promise<Result<T>> result(final Class<T> $) {
        return new PromiseImpl<>();
    }

    static <T> Promise<Result<T>> result(final TypeToken<T> $) {
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
        return Promise.<T>give().apply(result -> List.of(promises)
                                                     .forEach(promise -> promise.then(result::resolve)));
    }
}
