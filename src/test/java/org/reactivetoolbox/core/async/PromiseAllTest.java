package org.reactivetoolbox.core.async;

import org.junit.jupiter.api.Test;
import org.reactivetoolbox.core.lang.Result;
import org.reactivetoolbox.core.lang.Tuple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.reactivetoolbox.core.Errors.CANCELLED;
import static org.reactivetoolbox.core.Errors.TIMEOUT;
import static org.reactivetoolbox.core.async.Promise.all;
import static org.reactivetoolbox.core.lang.Result.ok;
import static org.reactivetoolbox.core.scheduler.Timeout.timeout;

class PromiseAllTest {

    @Test
    void allResolvesWhenAllPromisesAreResolvedFor1Promise() {
        final var promise1 = Promise.<Integer>promise();

        all(promise1)
                .onFailure(f -> fail())
                .onSuccess(v -> assertEquals(Tuple.tuple(1), v))
                .when(timeout(100).millis(), Result.fail(TIMEOUT));

        promise1.resolve(ok(1));
    }

    @Test
    void allResolvesWhenAllPromisesAreResolvedFor2Promises() {
        final var promise1 = Promise.<Integer>promise();
        final var promise2 = Promise.<Integer>promise();

        all(promise1, promise2)
                .onFailure(f -> fail())
                .onSuccess(v -> assertEquals(Tuple.tuple(1, 2), v))
                .when(timeout(100).millis(), TIMEOUT::asResult);

        promise1.resolve(ok(1));
        promise2.resolve(ok(2));
    }

    @Test
    void allResolvesWhenAllPromisesAreResolvedFor3Promises() {
        final var promise1 = Promise.<Integer>promise();
        final var promise2 = Promise.<Integer>promise();
        final var promise3 = Promise.<Integer>promise();

        all(promise1, promise2, promise3)
                .onFailure(f -> fail())
                .onSuccess(v -> assertEquals(Tuple.tuple(1, 2, 3), v))
                .when(timeout(100).millis(), Result.fail(TIMEOUT));

        promise1.resolve(ok(1));
        promise2.resolve(ok(2));
        promise3.resolve(ok(3));
    }

    @Test
    void allResolvesWhenAllPromisesAreResolvedFor4Promises() {
        final var promise1 = Promise.<Integer>promise();
        final var promise2 = Promise.<Integer>promise();
        final var promise3 = Promise.<Integer>promise();
        final var promise4 = Promise.<Integer>promise();

        all(promise1, promise2, promise3, promise4)
                .onFailure(f -> fail())
                .onSuccess(v -> assertEquals(Tuple.tuple(1, 2, 3, 4), v))
                .when(timeout(100).millis(), Result.fail(TIMEOUT));

        promise1.resolve(ok(1));
        promise2.resolve(ok(2));
        promise3.resolve(ok(3));
        promise4.resolve(ok(4));
    }

    @Test
    void allResolvesWhenAllPromisesAreResolvedFor5Promises() {
        final var promise1 = Promise.<Integer>promise();
        final var promise2 = Promise.<Integer>promise();
        final var promise3 = Promise.<Integer>promise();
        final var promise4 = Promise.<Integer>promise();
        final var promise5 = Promise.<Integer>promise();

        all(promise1, promise2, promise3, promise4, promise5)
                .onFailure(f -> fail())
                .onSuccess(v -> assertEquals(Tuple.tuple(1, 2, 3, 4, 5), v))
                .when(timeout(100).millis(), Result.fail(TIMEOUT));

        promise1.resolve(ok(1));
        promise2.resolve(ok(2));
        promise3.resolve(ok(3));
        promise4.resolve(ok(4));
        promise5.resolve(ok(5));
    }

    @Test
    void allResolvesWhenAllPromisesAreResolvedFor6Promises() {
        final var promise1 = Promise.<Integer>promise();
        final var promise2 = Promise.<Integer>promise();
        final var promise3 = Promise.<Integer>promise();
        final var promise4 = Promise.<Integer>promise();
        final var promise5 = Promise.<Integer>promise();
        final var promise6 = Promise.<Integer>promise();

        all(promise1, promise2, promise3, promise4, promise5, promise6)
                .onFailure(f -> fail())
                .onSuccess(v -> assertEquals(Tuple.tuple(1, 2, 3, 4, 5, 6), v))
                .when(timeout(100).millis(), Result.fail(TIMEOUT));

        promise1.resolve(ok(1));
        promise2.resolve(ok(2));
        promise3.resolve(ok(3));
        promise4.resolve(ok(4));
        promise5.resolve(ok(5));
        promise6.resolve(ok(6));
    }

    @Test
    void allResolvesWhenAllPromisesAreResolvedFor7Promises() {
        final var promise1 = Promise.<Integer>promise();
        final var promise2 = Promise.<Integer>promise();
        final var promise3 = Promise.<Integer>promise();
        final var promise4 = Promise.<Integer>promise();
        final var promise5 = Promise.<Integer>promise();
        final var promise6 = Promise.<Integer>promise();
        final var promise7 = Promise.<Integer>promise();

        all(promise1, promise2, promise3, promise4, promise5, promise6, promise7)
                .onFailure(f -> fail())
                .onSuccess(v -> assertEquals(Tuple.tuple(1, 2, 3, 4, 5, 6, 7), v))
                .when(timeout(100).millis(), Result.fail(TIMEOUT));

        promise1.resolve(ok(1));
        promise2.resolve(ok(2));
        promise3.resolve(ok(3));
        promise4.resolve(ok(4));
        promise5.resolve(ok(5));
        promise6.resolve(ok(6));
        promise7.resolve(ok(7));
    }

    @Test
    void allResolvesWhenAllPromisesAreResolvedFor8Promises() {
        final var promise1 = Promise.<Integer>promise();
        final var promise2 = Promise.<Integer>promise();
        final var promise3 = Promise.<Integer>promise();
        final var promise4 = Promise.<Integer>promise();
        final var promise5 = Promise.<Integer>promise();
        final var promise6 = Promise.<Integer>promise();
        final var promise7 = Promise.<Integer>promise();
        final var promise8 = Promise.<Integer>promise();

        all(promise1, promise2, promise3, promise4, promise5, promise6, promise7, promise8)
                .onFailure(f -> fail())
                .onSuccess(v -> assertEquals(Tuple.tuple(1, 2, 3, 4, 5, 6, 7, 8), v))
                .when(timeout(100).millis(), Result.fail(TIMEOUT));

        promise1.resolve(ok(1));
        promise2.resolve(ok(2));
        promise3.resolve(ok(3));
        promise4.resolve(ok(4));
        promise5.resolve(ok(5));
        promise6.resolve(ok(6));
        promise7.resolve(ok(7));
        promise8.resolve(ok(8));
    }

    @Test
    void allResolvesWhenAllPromisesAreResolvedFor9Promises() {
        final var promise1 = Promise.<Integer>promise();
        final var promise2 = Promise.<Integer>promise();
        final var promise3 = Promise.<Integer>promise();
        final var promise4 = Promise.<Integer>promise();
        final var promise5 = Promise.<Integer>promise();
        final var promise6 = Promise.<Integer>promise();
        final var promise7 = Promise.<Integer>promise();
        final var promise8 = Promise.<Integer>promise();
        final var promise9 = Promise.<Integer>promise();

        all(promise1, promise2, promise3, promise4, promise5, promise6, promise7, promise8, promise9)
                .onFailure(f -> fail())
                .onSuccess(v -> assertEquals(Tuple.tuple(1, 2, 3, 4, 5, 6, 7, 8, 9), v))
                .when(timeout(100).millis(), Result.fail(TIMEOUT));

        promise1.resolve(ok(1));
        promise2.resolve(ok(2));
        promise3.resolve(ok(3));
        promise4.resolve(ok(4));
        promise5.resolve(ok(5));
        promise6.resolve(ok(6));
        promise7.resolve(ok(7));
        promise8.resolve(ok(8));
        promise9.resolve(ok(9));
    }

    @Test
    void anyErrorResolvesToFailureImmediatelyFor1Promise() {
        final var promise1 = Promise.<Integer>promise();

        all(promise1)
                .onFailure(f -> assertEquals(CANCELLED, f))
                .onSuccess(v -> fail())
                .when(timeout(100).millis(), Result.fail(TIMEOUT));

        promise1.resolve(Result.fail(CANCELLED));
    }

    @Test
    void anyErrorResolvesToFailureImmediatelyFor2Promises() {
        final var promise1 = Promise.<Integer>promise();
        final var promise2 = Promise.<Integer>promise();

        all(promise1, promise2)
                .onFailure(f -> assertEquals(CANCELLED, f))
                .onSuccess(v -> fail())
                .when(timeout(100).millis(), Result.fail(TIMEOUT));

        promise1.resolve(Result.fail(CANCELLED));
    }

    @Test
    void anyErrorResolvesToFailureImmediatelyFor3Promises() {
        final var promise1 = Promise.<Integer>promise();
        final var promise2 = Promise.<Integer>promise();
        final var promise3 = Promise.<Integer>promise();

        all(promise1, promise2, promise3)
                .onFailure(f -> assertEquals(CANCELLED, f))
                .onSuccess(v -> fail())
                .when(timeout(100).millis(), Result.fail(TIMEOUT));

        promise1.resolve(Result.fail(CANCELLED));
    }

    @Test
    void anyErrorResolvesToFailureImmediatelyFor4Promises() {
        final var promise1 = Promise.<Integer>promise();
        final var promise2 = Promise.<Integer>promise();
        final var promise3 = Promise.<Integer>promise();
        final var promise4 = Promise.<Integer>promise();

        all(promise1, promise2, promise3, promise4)
                .onFailure(f -> assertEquals(CANCELLED, f))
                .onSuccess(v -> fail())
                .when(timeout(100).millis(), Result.fail(TIMEOUT));

        promise1.resolve(Result.fail(CANCELLED));
    }

    @Test
    void anyErrorResolvesToFailureImmediatelyFor5Promises() {
        final var promise1 = Promise.<Integer>promise();
        final var promise2 = Promise.<Integer>promise();
        final var promise3 = Promise.<Integer>promise();
        final var promise4 = Promise.<Integer>promise();
        final var promise5 = Promise.<Integer>promise();

        all(promise1, promise2, promise3, promise4, promise5)
                .onFailure(f -> assertEquals(CANCELLED, f))
                .onSuccess(v -> fail())
                .when(timeout(100).millis(), Result.fail(TIMEOUT));

        promise1.resolve(Result.fail(CANCELLED));
    }

    @Test
    void anyErrorResolvesToFailureImmediatelyFor6Promises() {
        final var promise1 = Promise.<Integer>promise();
        final var promise2 = Promise.<Integer>promise();
        final var promise3 = Promise.<Integer>promise();
        final var promise4 = Promise.<Integer>promise();
        final var promise5 = Promise.<Integer>promise();
        final var promise6 = Promise.<Integer>promise();

        all(promise1, promise2, promise3, promise4, promise5, promise6)
                .onFailure(f -> assertEquals(CANCELLED, f))
                .onSuccess(v -> fail())
                .when(timeout(100).millis(), Result.fail(TIMEOUT));

        promise1.resolve(Result.fail(CANCELLED));
    }

    @Test
    void anyErrorResolvesToFailureImmediatelyFor7Promises() {
        final var promise1 = Promise.<Integer>promise();
        final var promise2 = Promise.<Integer>promise();
        final var promise3 = Promise.<Integer>promise();
        final var promise4 = Promise.<Integer>promise();
        final var promise5 = Promise.<Integer>promise();
        final var promise6 = Promise.<Integer>promise();
        final var promise7 = Promise.<Integer>promise();

        all(promise1, promise2, promise3, promise4, promise5, promise6, promise7)
                .onFailure(f -> assertEquals(CANCELLED, f))
                .onSuccess(v -> fail())
                .when(timeout(100).millis(), Result.fail(TIMEOUT));

        promise1.resolve(Result.fail(CANCELLED));
    }

    @Test
    void anyErrorResolvesToFailureImmediatelyFor8Promises() {
        final var promise1 = Promise.<Integer>promise();
        final var promise2 = Promise.<Integer>promise();
        final var promise3 = Promise.<Integer>promise();
        final var promise4 = Promise.<Integer>promise();
        final var promise5 = Promise.<Integer>promise();
        final var promise6 = Promise.<Integer>promise();
        final var promise7 = Promise.<Integer>promise();
        final var promise8 = Promise.<Integer>promise();

        all(promise1, promise2, promise3, promise4, promise5, promise6, promise7, promise8)
                .onFailure(f -> assertEquals(CANCELLED, f))
                .onSuccess(v -> fail())
                .when(timeout(100).millis(), Result.fail(TIMEOUT));

        promise1.resolve(Result.fail(CANCELLED));
    }

    @Test
    void anyErrorResolvesToFailureImmediatelyFor9Promises() {
        final var promise1 = Promise.<Integer>promise();
        final var promise2 = Promise.<Integer>promise();
        final var promise3 = Promise.<Integer>promise();
        final var promise4 = Promise.<Integer>promise();
        final var promise5 = Promise.<Integer>promise();
        final var promise6 = Promise.<Integer>promise();
        final var promise7 = Promise.<Integer>promise();
        final var promise8 = Promise.<Integer>promise();
        final var promise9 = Promise.<Integer>promise();

        all(promise1, promise2, promise3, promise4, promise5, promise6, promise7, promise8, promise9)
                .onFailure(f -> assertEquals(CANCELLED, f))
                .onSuccess(v -> fail())
                .when(timeout(100).millis(), Result.fail(TIMEOUT));

        promise1.resolve(Result.fail(CANCELLED));
    }
}