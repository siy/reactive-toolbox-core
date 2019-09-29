package org.reactivetoolbox.core.examples.async;

import org.junit.jupiter.api.Test;
import org.reactivetoolbox.core.functional.Tuples;
import org.reactivetoolbox.core.scheduler.Timeout;

import static org.reactivetoolbox.core.async.Promise.all;
import static org.reactivetoolbox.core.async.Promise.zipAll;
import static org.reactivetoolbox.core.functional.Tuples.zip;
import static org.reactivetoolbox.core.scheduler.SchedulerError.TIMEOUT;

public class PromiseAllExample_Test {
    private final AsyncService service = new AsyncService();

    @Test
    void simpleAsyncTask() {
        service.slowRetrieveInteger(42)
               .then(result -> result.onSuccess(System.out::println))
               .syncWait();
    }

    @Test
    void simpleAsyncTaskWithTimeout() {
        service.slowRetrieveInteger(4242)
               .with(Timeout.of(10).sec(), TIMEOUT.asFailure())
               .then(result -> result.onSuccess(System.out::println))
               .syncWait();
    }

    @Test
    void waitForAllResults1() {
        //Using Promise.map()
        all(service.slowRetrieveInteger(123),
            service.slowRetrieveString("text 1"),
            service.slowRetrieveUuid())
                .map(Tuples::zip)
                .then(result -> result.onSuccess(System.out::println))
                .syncWait();
    }

    @Test
    void waitForAllResults2() {
        //Using Tuples.zip()
        all(service.slowRetrieveInteger(234),
            service.slowRetrieveString("text 2"),
            service.slowRetrieveUuid())
                .then(result -> zip(result).onSuccess(System.out::println))
                .syncWait();
    }

    @Test
    void waitForAllResults3() {
        //Using Promise.zipAll
        zipAll(service.slowRetrieveInteger(345),
               service.slowRetrieveString("text 3"),
               service.slowRetrieveUuid())
                .then(result -> result.onSuccess(System.out::println))
                .syncWait();
    }
}
