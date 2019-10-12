package org.reactivetoolbox.core.async;

import org.junit.jupiter.api.Test;
import org.reactivetoolbox.core.scheduler.Timeout;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.reactivetoolbox.core.lang.Option.with;


class PromiseTest {
    private final Executor executor = Executors.newSingleThreadExecutor();

    @Test
    void multipleAssignmentsAreIgnored() {
        final var promise = Promise.<Integer>give();

        promise.resolve(1);
        promise.resolve(2);
        promise.resolve(3);
        promise.resolve(4);

        assertTrue(promise.ready());
        assertEquals(1, promise.value().otherwise(0));
    }

    @Test
    void fulfilledPromiseIsAlreadyResolved() {
        assertTrue(Promise.fulfilled(123).ready());
    }

    @Test
    void thenActionsAreExecuted() {
        final var promise = Promise.<Integer>give();
        final var holder = new AtomicInteger(-1);

        promise.then(holder::set);
        promise.resolve(1);

        assertEquals(1, holder.get());
    }

    @Test
    void thenActionsAreExecutedEvenIfAddedAfterPromiseResolution() {
        final var promise = Promise.<Integer>give();
        final var holder = new AtomicInteger(-1);

        promise.resolve(1);
        promise.then(holder::set);

        assertEquals(1, holder.get());
    }

    @Test
    void syncWaitIsWaitingForResolution() {
        final var promise = Promise.<Integer>give();

        assertFalse(promise.ready());

        executor.execute(() -> {safeSleep(20); promise.resolve(1);});

        promise.syncWait();

        assertTrue(promise.ready());

        assertEquals(1, promise.value().otherwise(0));
    }

    @Test
    void syncWaitDoesNotWaitForAlreadyResolved() {
        final var promise = Promise.<Integer>give();

        assertFalse(promise.ready());

        promise.resolve(1);

        promise.syncWait();

        assertTrue(promise.ready());

        assertEquals(1, promise.value().otherwise(0));
    }

    @Test
    void syncWaitWithTimeoutIsWaitingForResolution() {
        final var promise = Promise.<Integer>give();

        assertFalse(promise.ready());

        executor.execute(() -> {safeSleep(20); promise.resolve(1);});

        promise.syncWait(Timeout.of(100).millis());

        assertTrue(promise.ready());

        assertEquals(1, promise.value().otherwise(0));
    }

    @Test
    void syncWaitWithTimeoutIsWaitingForTimeout() {
        final var promise = Promise.<Integer>give();

        assertFalse(promise.ready());

        executor.execute(() -> {safeSleep(200); promise.resolve(1);});

        assertFalse(promise.syncWait(Timeout.of(10).millis()).ready());
    }

    @Test
    void promiseIsResolvedWhenTimeoutExpires() {
        final var promise = Promise.<Integer>give().async(Timeout.of(100).millis(), task -> task.resolve(123));

        assertFalse(promise.ready());

        promise.syncWait();

        assertTrue(promise.ready());
        assertEquals(with(123), promise.value());
    }

    @Test
    void taskCanBeExecuted() {
        final var promise = Promise.<Integer>give().async(Timeout.of(100).millis(), task -> task.resolve(123));

        assertFalse(promise.ready());

        promise.async((p) -> p.resolve(345))
               .then(val -> assertEquals(345, val));

        assertTrue(promise.syncWait().ready());
        assertEquals(with(345), promise.value());
    }

    @Test
    void anyResolvedPromiseResolvesResultForFirstPromise() {
        final var promise1 = Promise.<Integer>give();
        final var promise2 = Promise.<Integer>give();
        final var anyPromise = Promise.any(promise1, promise2);

        assertFalse(anyPromise.ready());

        promise1.resolve(1);

        assertTrue(anyPromise.ready());
        assertEquals(1, anyPromise.value().otherwise(0));
    }

    @Test
    void anyResolvedPromiseResolvesResultForSecondPromise() {
        final var promise1 = Promise.<Integer>give();
        final var promise2 = Promise.<Integer>give();
        final Promise<Integer> anyPromise = Promise.any(promise1, promise2);

        assertFalse(anyPromise.ready());

        promise2.resolve(1);

        assertTrue(anyPromise.ready());
        assertEquals(1, anyPromise.value().otherwise(0));
    }

    private static void safeSleep(final long delay) {
        try {
            Thread.sleep(delay);
        } catch (final InterruptedException e) {
            //Ignore
        }
    }
}
