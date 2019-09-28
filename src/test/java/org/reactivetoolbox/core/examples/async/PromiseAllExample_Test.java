package org.reactivetoolbox.core.examples.async;

import org.junit.jupiter.api.Test;
import org.reactivetoolbox.core.functional.Either;
import org.reactivetoolbox.core.functional.Tuples;
import org.reactivetoolbox.core.scheduler.Timeout;

import java.util.UUID;

import static org.reactivetoolbox.core.async.Promise.all;
import static org.reactivetoolbox.core.async.Promise.either;
import static org.reactivetoolbox.core.async.Promise.zipAll;
import static org.reactivetoolbox.core.functional.Either.success;
import static org.reactivetoolbox.core.functional.Tuples.zip;
import static org.reactivetoolbox.core.scheduler.SchedulerError.TIMEOUT;

public class PromiseAllExample_Test {
    @Test
    void simpleAsyncTask() {
        either(Integer.class)
                .perform(promise -> promise.resolve(success(42)))
                .then(result -> result.onSuccess(System.out::println))
                .syncWait();
    }

    @Test
    void simpleAsyncTaskWithTimeout() {
        either(Integer.class)
                .with(Timeout.of(10).sec(), TIMEOUT.asFailure())
                .perform(promise -> promise.resolve(success(42)))
                .then(result -> result.onSuccess(System.out::println))
                .syncWait();
    }

    @Test
    void waitForAllResults1() {
        all(either(Integer.class).perform(p1 -> p1.resolve(success(123))),
            either(String.class).perform(p2 -> p2.resolve(success("text 1"))),
            either(UUID.class).perform(p3 -> p3.resolve(success(UUID.randomUUID()))))
                .map(Tuples::zip)
                .then(result -> result.onSuccess(System.out::println))
                .syncWait();
    }

    @Test
    void waitForAllResults2() {
        all(either(Integer.class).perform(p1 -> p1.resolve(success(321))),
            either(String.class).perform(p2 -> p2.resolve(success("text 2"))),
            either(UUID.class).perform(p3 -> p3.resolve(success(UUID.randomUUID()))))
                .then(result -> zip(result).onSuccess(System.out::println))
                .syncWait();
    }

    @Test
    void waitForAllResults3() {
        zipAll(either(Integer.class).perform(p1 -> p1.resolve(success(231))),
            either(String.class).perform(p2 -> p2.resolve(success("text 3"))),
            either(UUID.class).perform(p3 -> p3.resolve(success(UUID.randomUUID()))))
                .then(result -> result.onSuccess(System.out::println))
                .syncWait();
    }
}
