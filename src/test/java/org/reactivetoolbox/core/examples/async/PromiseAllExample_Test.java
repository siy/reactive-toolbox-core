package org.reactivetoolbox.core.examples.async;

import org.junit.jupiter.api.Test;
import org.reactivetoolbox.core.functional.TupleResult;
import org.reactivetoolbox.core.scheduler.Timeout;

import static org.reactivetoolbox.core.async.Promise.all;
import static org.reactivetoolbox.core.async.Promise.allOf;
import static org.reactivetoolbox.core.scheduler.SchedulerError.TIMEOUT;

public class PromiseAllExample_Test {
    private final AsyncService service = new AsyncService();

    @Test
    void simpleAsyncTask() {
        service.slowRetrieveInteger(42)
               .then(result -> result.ifSuccess(System.out::println))
               .syncWait();
    }

    @Test
    void simpleAsyncTaskWithTimeout() {
        service.slowRetrieveInteger(4242)
               .with(Timeout.of(10).sec(), TIMEOUT.asFailure())
               .then(result -> result.ifSuccess(System.out::println))
               .syncWait();
    }

    @Test
    void waitForAllResults1() {
        //Using Promise.map()
        all(service.slowRetrieveInteger(123),
            service.slowRetrieveString("text 1"),
            service.slowRetrieveUuid())
                .map(tuple -> TupleResult.of(tuple).zip())
                .then(result -> result.ifSuccess(System.out::println))
                .syncWait();
    }

    @Test
    void waitForAllResults3() {
        //Using Promise.zipAll
        allOf(service.slowRetrieveInteger(345),
               service.slowRetrieveString("text 3"),
               service.slowRetrieveUuid())
                .then(result -> result.ifSuccess(System.out::println))
                .syncWait();
    }
}
