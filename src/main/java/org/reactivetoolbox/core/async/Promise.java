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
import org.reactivetoolbox.core.lang.Failure;
import org.reactivetoolbox.core.lang.Functions.FN1;
import org.reactivetoolbox.core.lang.Result;
import org.reactivetoolbox.core.scheduler.Errors;
import org.reactivetoolbox.core.scheduler.Timeout;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.reactivetoolbox.core.lang.Result.failure;
import static org.reactivetoolbox.core.lang.Result.success;

/**
 * Extended {@link Promise} implementation which works with {@link Result} values.
 */
public interface Promise<T> {
    /**
     * Perform user-provided action once this instance will be resolved. Action will be executed once regardless
     * if instance is already resolved or not. User may add as many actions as necessary.
     *
     * @param action
     *        Action to perform
     * @return Current instance
     */
    Promise<T> onResult(final Consumer<Result<T>> action);

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
     * Synchronously wait for this instance resolution.
     * <br/>
     * This method is provided only for testing purposes, it is not recommended to use for following reasons:
     * <ul>
     *     <li>Synchronous waiting blocks the current thread</li>
     *     <li>If current thread will be {@link Thread#interrupt() interrupted} with other thread, then method may
     *     return non-resolved instance. This may lead to subtle and hard to debug issues.</li>
     * </ul>
     *
     * @return Current instance
     */
    @Deprecated
    Promise<T> syncWait();

    /**
     * Synchronously wait for this instance resolution or timeout.
     *
     * <br/>
     * This method is provided only for testing purposes, it is not recommended to use for following reasons:
     * <ul>
     *     <li>Synchronous waiting blocks the current thread</li>
     *     <li>If current thread will be {@link Thread#interrupt() interrupted} with other thread or timeout
     *     expires, then method may return non-resolved instance. This may lead to subtle and hard to debug issues.</li>
     * </ul>
     *
     * @param timeout
     *        Timeout amount
     * @return Current instance
     */
    @Deprecated
    Promise<T> syncWait(final Timeout timeout);

    /**
     * Resolve the promise with specified result. All actions already
     * waiting for resolution will be scheduled for synchronous execution.
     *
     * @param result
     *        The value which will be stored in this instance and make it resolved
     *
     * @return Current instance
     */
    Promise<T> resolve(final Result<T> result);

    /**
     * Resolve the promise with specified result. All actions already
     * waiting for resolution will be scheduled for asynchronous execution.
     *
     * @param result
     *        The value which will be stored in this instance and make it resolved
     *
     * @return Current instance
     */
    default Promise<T> resolveAsync(final Result<T> result) {
        return async(promise -> promise.resolve(result));
    }

    /**
     * Resolve current instance with successful result.
     *
     * @param result
     *        Successful result value
     *
     * @return Current instance
     */
    default Promise<T> ok(final T result) {
        return resolve(Result.success(result));
    }

    /**
     * Resolve current instance with failure result.
     *
     * @param failure
     *        Failure result value
     *
     * @return Current instance
     */
    default Promise<T> fail(final Failure failure) {
        return resolve(failure(failure));
    }

    /**
     * Convenience method for performing some actions with current promise instance.
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
     * Set timeout for instance resolution. When timeout expires, instance will be resolved with specified timeout
     * result.
     *
     * @param timeout
     *        Timeout amount
     * @param timeoutResult
     *        Resolution value in case of timeout
     * @return Current instance
     */
    default Promise<T> when(final Timeout timeout, final Result<T> timeoutResult) {
        return async(timeout, promise -> promise.resolve(timeoutResult));
    }

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
    default Promise<T> when(final Timeout timeout, final Supplier<Result<T>> timeoutResultSupplier) {
        return async(timeout, promise -> promise.resolve(timeoutResultSupplier.get()));
    }

    default Promise<T> onSuccess(final Consumer<T> consumer) {
        onResult(result -> result.onSuccess(consumer));
        return this;
    }

    default Promise<T> onFailure(final Consumer<? super Failure> consumer) {
        onResult(result -> result.onFailure(consumer));
        return this;
    }

    /**
     * Resolve instance with {@link Errors#CANCELLED}. Often necessary if pending request need to be resolved prematurely,
     * without waiting for response or timeout.
     *
     * @return Current instance
     */
    default Promise<T> cancel() {
        fail(Errors.CANCELLED);
        return this;
    }

    /**
     * This method enables chaining of calls to functions which return {@link Promise} and require unwrapped
     * results successful previous calls. If current instance is resolved to {@link Result#failure(Failure)}, then
     * function passes as parameter is not invoked and resolved instance of {@link Promise} is returned instead.
     *
     * @param mapper
     *        Function to call if current instance is resolved with success.
     * @return Created instance which is either already resolved with same error as current instance or
     *         waiting for resolving by provided mapping function.
     */
    default <R> Promise<R> chainMap(final FN1<Promise<R>, T> mapper) {
        return promise(promise -> onResult(result -> result.map(error -> promise.resolve(failure(error)),
                                                                success -> mapper.apply(success)
                                                                                 .onResult(promise::resolve))));
    }

    /**
     * Convenience method which provides access to inner value of successful result. If current instance
     * contains failure, then mapping function is not called and created instance is resolved with same error
     * as current instance.
     *
     * @param mapper
     *        Function to transform successful result value if current instance is resolved with success
     * @return Created instance
     */
    default <R> Promise<R> map(final FN1<R, T> mapper) {
        return promise(promise -> onResult(result -> promise.resolve(result.map(mapper))));
    }

    /**
     * Create new unresolved instance.
     *
     * @return Created instance
     */
    static <T> Promise<T> promise() {
        return new PromiseImpl<>();
    }

    /**
     * Create new resolved instance.
     *
     * @return Created instance
     */
    static <T> Promise<T> fulfilled(final Result<T> result) {
        return Promise.<T>promise().resolve(result);
    }

    /**
     * Create new resolved instance.
     *
     * @return Created instance
     */
    static <T> Promise<T> fulfilled(final T result) {
        return Promise.<T>promise().resolve(success(result));
    }

    /**
     * Create instance and immediately invoke provided function with created instance.
     * Usually this function is used to configure actions on created instance.
     *
     * @param setup
     *        Function to invoke with created instance
     * @return Created instance
     */
    static <T> Promise<T> promise(final Consumer<Promise<T>> setup) {
        return Promise.<T>promise().apply(setup);
    }

    /**
     * Create instance which will be resolved once any of the promises
     * provided as a parameters will be resolved.
     * <br/>
     * Note: unlike {@link Promise#any(Promise[])} this method cancels all remaining
     * instances once one is resolved.
     *
     * @param promises
     *        Input promises
     *
     * @return created instance
     */
    @SafeVarargs
    static <T> Promise<T> any(final Promise<T>... promises) {
        return promise(result -> List.of(promises).forEach(promise -> promise.onResult(result::resolve)
                                                                             .onResult(v -> cancelAll(promises))));
    }

    /**
     * Create instance which will be resolved once any of the promises provided as a parameters will be resolved
     * with successful result. If none of the promises will be resolved with successful result, then created
     * instance will be resolved with {@link Errors#CANCELLED}.
     *
     * @param promises
     *        Input promises
     *
     * @return Created instance
     */
    @SafeVarargs
    static <T> Promise<T> anySuccess(final Promise<T>... promises) {
        return anySuccess(failure(Errors.CANCELLED), promises);
    }

    /**
     * Create instance which will be resolved once any of the promises provided as a parameters will be resolved
     * with successful result. If none of the promises will be resolved with successful result, then created
     * instance will be resolved with provided {@code failureResult}.
     *
     * @param failureResult
     *        Result in case if no instances were resolved with success
     * @param promises
     *        Input promises
     *
     * @return Created instance
     */
    static <T> Promise<T> anySuccess(final Result<T> failureResult, final Promise<T>... promises) {
        final var result = Promise.<T>promise();
        final var counter = ActionableThreshold.threshold(promises.length, () -> resolveAll(failureResult, promises));

        List.of(promises)
            .forEach(promise -> promise.onResult($ -> counter.registerEvent())
                                       .onResult(res -> res.onSuccess(t -> { result.resolve(res); resolveAll(failureResult, promises); })));

        return result;
    }

    /**
     * Cancel several promises at once.
     *
     * @param promises
     *        Promises to cancel.
     */
    static <T> void cancelAll(final Promise<T>... promises) {
        resolveAll(failure(Errors.CANCELLED), promises);
    }

    /**
     * Resolve several promises at once.
     *
     * @param result
     *        Resolution result
     * @param promises
     *        Promises to resolve
     */
    static <T> void resolveAll(final Result<T> result, final Promise<T>... promises) {
        List.of(promises).forEach(promise -> promise.resolve(result));
    }
}
