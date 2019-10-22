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

import org.reactivetoolbox.core.lang.Failure;
import org.reactivetoolbox.core.lang.Functions.FN1;
import org.reactivetoolbox.core.lang.Option;
import org.reactivetoolbox.core.lang.Result;
import org.reactivetoolbox.core.scheduler.Errors;
import org.reactivetoolbox.core.scheduler.Timeout;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.reactivetoolbox.core.lang.Result.failure;

/**
 * Extended {@link Promise} implementation which works with {@link Result} values.
 */
public interface PromiseResult<T> extends Promise<Result<T>> {
    /**
     * Convenience method for performing some actions with current promise instance.
     *
     * @param consumer
     *        Action to perform on current instance.
     * @return Current instance
     */
    default PromiseResult<T> apply(final Consumer<PromiseResult<T>> consumer) {
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
    default PromiseResult<T> on(final Timeout timeout, final Result<T> timeoutResult) {
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
    default PromiseResult<T> on(final Timeout timeout, final Supplier<Result<T>> timeoutResultSupplier) {
        return async(timeout, promise -> promise.resolve(timeoutResultSupplier.get()));
    }

    default PromiseResult<T> onSuccess(final Consumer<T> consumer) {
        then(result -> result.whenSuccess(consumer));
        return this;
    }

    default PromiseResult<T> onFailure(final Consumer<? super Failure> consumer) {
        then(result -> result.whenFailure(consumer));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    PromiseResult<T> async(final Consumer<Promise<Result<T>>> task);

    /**
     * {@inheritDoc}
     */
    @Override
    PromiseResult<T> async(final Timeout timeout, final Consumer<Promise<Result<T>>> task);

    /**
     * Resolve instance with {@link Errors#CANCELLED}. Often necessary if pending request need to be resolved prematurely,
     * without waiting for response or timeout.
     *
     * @return Current instance
     */
    default PromiseResult<T> cancel() {
        resolve(failure(Errors.CANCELLED));
        return this;
    }

    /**
     * This method enables chaining of calls to functions which return {@link PromiseResult} and require unwrapped
     * results successful previous calls. If current instance is resolved to {@link Result#failure(Failure)}, then
     * function passes as parameter is not invoked and resolved instance of {@link PromiseResult} is returned instead.
     *
     * @param mapper
     *        Function to call if current instance is resolved with success.
     * @return Created instance which is either already resolved with same error as current instance or
     *         waiting for resolving by provided mapping function.
     */
    default <R> PromiseResult<R> chainMap(final FN1<PromiseResult<R>, T> mapper) {
        return result(promise -> then(result -> result.map(error -> promise.resolve(failure(error)),
                                                           success -> mapper.apply(success)
                                                                            .then(promise::resolve))));
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
    default <R> PromiseResult<R> mapResult(final FN1<R, T> mapper) {
        return result(promise -> then(result -> promise.resolve(result.map(mapper))));
    }

    /**
     * Create new unresolved instance.
     *
     * @return Created instance
     */
    static <T> PromiseResult<T> result() {
        return new PromiseResultImpl<>(Promise.give());
    }

    /**
     * Create instance from instance of {@link Promise}.
     *
     * @return Created instance
     */
    static <T> PromiseResult<T> from(final Promise<Result<T>> promise) {
        return new PromiseResultImpl<>(promise);
    }

    /**
     * Create instance and immediately invoke provided function with created instance.
     * Usually this function is used to configure actions on created instance.
     *
     * @param setup
     *        Function to invoke with created instance
     * @return Created instance
     */
    static <T> PromiseResult<T> result(final Consumer<PromiseResult<T>> setup) {
        return PromiseResult.<T>result().apply(setup);
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
    static <T> PromiseResult<T> any(final PromiseResult<T>... promises) {
        return result(result -> List.of(promises).forEach(promise -> promise.then(result::resolve)
                                                                                          .then(v -> cancelAll(promises))));
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
    static <T> PromiseResult<T> anySuccess(final PromiseResult<T>... promises) {
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
    static <T> PromiseResult<T> anySuccess(final Result<T> failureResult, final PromiseResult<T>... promises) {
        final var result = PromiseResult.<T>result();
        final var counter = ActionableThreshold.threshold(promises.length, () -> resolveAll(failureResult, promises));

        List.of(promises)
            .forEach(promise -> promise.then($ -> counter.registerEvent())
                                       .then(res -> res.whenSuccess(t -> { result.resolve(res); resolveAll(failureResult, promises);})));

        return result;
    }

    /**
     * Cancel several promises at once.
     *
     * @param promises
     *        Promises to cancel.
     */
    static <T> void cancelAll(final PromiseResult<T>... promises) {
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
    static <T> void resolveAll(final Result<T> result, final PromiseResult<T>... promises) {
        List.of(promises).forEach(promise -> promise.resolve(result));
    }

    class PromiseResultImpl<T> implements PromiseResult<T> {
        private final Promise<Result<T>> promise;

        protected PromiseResultImpl(final Promise<Result<T>> promise) {
            this.promise = promise;
        }

        @Override
        public Option<Result<T>> value() {
            return promise.value();
        }

        @Override
        public boolean ready() {
            return promise.ready();
        }

        @Override
        public PromiseResult<T> resolve(final Result<T> result) {
            promise.resolve(result);
            return this;
        }

        @Override
        public PromiseResult<T> resolveAsync(final Result<T> result) {
            promise.resolveAsync(result);
            return this;
        }

        @Override
        public PromiseResult<T> then(final Consumer<Result<T>> action) {
            promise.then(action);
            return this;
        }

        @Override
        public PromiseResult<T> syncWait() {
            promise.syncWait();
            return this;
        }

        @Override
        public PromiseResult<T> syncWait(final Timeout timeout) {
            promise.syncWait(timeout);
            return this;
        }

        @Override
        public PromiseResult<T> async(final Consumer<Promise<Result<T>>> task) {
            promise.async(task);
            return this;
        }

        @Override
        public PromiseResult<T> async(final Timeout timeout,
                                      final Consumer<Promise<Result<T>>> task) {
            promise.async(timeout, task);
            return this;
        }

        @Override
        public String toString() {
            return promise.toString();
        }
    }
}
