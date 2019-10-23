package org.reactivetoolbox.core.async;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.reactivetoolbox.core.scheduler.Timeout.timeout;


class PromiseTest {
    private final Executor executor = Executors.newSingleThreadExecutor();

    @Test
    void multipleResolutionsAreIgnored() {
        final var holder = new AtomicInteger(-1);
        final var promise = Promise.<Integer>promise().onSuccess(holder::set);

        promise.ok(1);
        promise.ok(2);
        promise.ok(3);
        promise.ok(4);

        assertEquals(1, holder.get());
    }

    @Test
    void fulfilledPromiseIsAlreadyResolved() {
        final var holder = new AtomicInteger(-1);
        Promise.fulfilled(123).onSuccess(holder::set);

        assertEquals(123, holder.get());
    }

    @Test
    void thenActionsAreExecuted() {
        final var holder = new AtomicInteger(-1);
        final var promise = Promise.<Integer>promise().onSuccess(holder::set);

        promise.ok(1);

        assertEquals(1, holder.get());
    }

    @Test
    void thenActionsAreExecutedEvenIfAddedAfterPromiseResolution() {
        final var holder = new AtomicInteger(-1);
        final var promise = Promise.<Integer>promise();

        promise.ok(1);
        promise.onSuccess(holder::set);

        assertEquals(1, holder.get());
    }

    @Test
    void syncWaitIsWaitingForResolution() {
        final var holder = new AtomicInteger(-1);
        final var promise = Promise.<Integer>promise().onSuccess(holder::set);

        executor.execute(() -> {safeSleep(20); promise.ok(1);});

        promise.syncWait();

        assertEquals(1, holder.get());
    }

    @Test
    void syncWaitDoesNotWaitForAlreadyResolved() {
        final var holder = new AtomicInteger(-1);
        final var promise = Promise.<Integer>promise().onSuccess(holder::set);

        assertEquals(-1, holder.get());

        promise.ok(1);

        promise.syncWait();

        assertEquals(1, holder.get());
    }

    @Test
    void syncWaitWithTimeoutIsWaitingForResolution() {
        final var holder = new AtomicInteger(-1);
        final var promise = Promise.<Integer>promise().onSuccess(holder::set);

        assertEquals(-1, holder.get());

        executor.execute(() -> {safeSleep(20); promise.ok(1);});

        assertEquals(-1, holder.get());

        promise.syncWait(timeout(100).millis());

        assertEquals(1, holder.get());
    }

    @Test
    void syncWaitWithTimeoutIsWaitingForTimeout() {
        final var holder = new AtomicInteger(-1);
        final var promise = Promise.<Integer>promise().onSuccess(holder::set);

        assertEquals(-1, holder.get());

        executor.execute(() -> {safeSleep(200); promise.ok(1);});

        promise.syncWait(timeout(10).millis());

        assertEquals(-1, holder.get());
    }

    @Test
    void promiseIsResolvedWhenTimeoutExpires() {
        final var holder = new AtomicInteger(-1);
        final var promise = Promise.<Integer>promise().onSuccess(holder::set).async(timeout(100).millis(), task -> task.ok(123));

        assertEquals(-1, holder.get());

        promise.syncWait();

        assertEquals(123, holder.get());
    }

    @Test
    void taskCanBeExecuted() {
        final var holder = new AtomicInteger(-1);
        final var promise = Promise.<Integer>promise()
                .onSuccess(holder::set)
                .async(timeout(100).millis(), task -> task.ok(123));

        assertEquals(-1, holder.get());

        promise.async(p -> p.ok(345)).syncWait();

        assertEquals(345, holder.get());
    }

    @Test
    void anyResolvedPromiseResolvesResultForFirstPromise() {
        final var holder = new AtomicInteger(-1);
        final var promise1 = Promise.<Integer>promise();
        final var promise2 = Promise.<Integer>promise();

        Promise.any(promise1, promise2).onSuccess(holder::set);

        assertEquals(-1, holder.get());

        promise1.ok(1);

        assertEquals(1, holder.get());
    }

    @Test
    void anyResolvedPromiseResolvesResultForSecondPromise() {
        final var holder = new AtomicInteger(-1);
        final var promise1 = Promise.<Integer>promise();
        final var promise2 = Promise.<Integer>promise();
        Promise.any(promise1, promise2).onSuccess(holder::set);

        assertEquals(-1, holder.get());

        promise2.ok(1);

        assertEquals(1, holder.get());
    }

    private static void safeSleep(final long delay) {
        try {
            Thread.sleep(delay);
        } catch (final InterruptedException e) {
            //Ignore
        }
    }
}
