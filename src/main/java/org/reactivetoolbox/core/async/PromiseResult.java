package org.reactivetoolbox.core.async;

import org.reactivetoolbox.core.functional.Functions.FN1;
import org.reactivetoolbox.core.functional.Option;
import org.reactivetoolbox.core.functional.Result;
import org.reactivetoolbox.core.scheduler.Timeout;
import org.reactivetoolbox.core.type.Error;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface PromiseResult<T> extends Promise<Result<T>> {
    /**
     * Convenience method for performing some actions with current promise instance. Useful for cases
     * when there is (quite typical) sequence: create unresolved promise -> setup -> return created promise.
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
    default PromiseResult<T> with(final Timeout timeout, final Result<T> timeoutResult) {
        return (PromiseResult<T>) async(timeout, promise -> promise.resolve(timeoutResult));
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
    default PromiseResult<T> with(final Timeout timeout, final Supplier<Result<T>> timeoutResultSupplier) {
        return (PromiseResult<T>) async(timeout, promise -> promise.resolve(timeoutResultSupplier.get()));
    }

    /**
     * This method enables chaining of calls to functions which return {@link PromiseResult} and require unwrapped
     * results successful previous calls. If current instance is resolved to {@link Result#failure(Error)}, then
     * function passes as parameter is not invoked and resolved instance of {@link PromiseResult} is returned instead.
     *
     * @param mapper
     *        Function to call if current instance is resolved with success.
     * @return Created instance which is either already resolved with same error as current instance or
     *         waiting for resolving by provided mapping function.
     */
    default <R> PromiseResult<R> chainMap(final FN1<PromiseResult<R>, T> mapper) {
        return PromiseResult.result(promise -> then(result -> result.map(error -> PromiseResult.<R>result().resolve(Result.failure(error)),
                                                                         success -> mapper.apply(success)
                                                                                          .then(promise::resolve))));
    }

    static <T> PromiseResult<T> result() {
        return new PromiseResultImpl<>(Promise.give());
    }

    static <T> PromiseResult<T> from(final Promise<Result<T>> promise) {
        return new PromiseResultImpl<>(promise);
    }

    static <T> PromiseResult<T> result(final Consumer<PromiseResult<T>> mapper) {
        return PromiseResult.<T>result().apply(mapper);
    }

    class PromiseResultImpl<T> implements PromiseResult<T> {
        private final Promise<Result<T>> promise;

        private PromiseResultImpl(final Promise<Result<T>> promise) {
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
