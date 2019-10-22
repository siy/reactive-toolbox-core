package org.reactivetoolbox.core.examples.async;

import org.junit.jupiter.api.Test;
import org.reactivetoolbox.core.async.PromiseAll;
import org.reactivetoolbox.core.scheduler.Timeout;

import static org.reactivetoolbox.core.async.PromiseAll.resultsOf;
import static org.reactivetoolbox.core.scheduler.Errors.TIMEOUT;

public class PromiseAllExample_Test {
    private final AsyncService service = new AsyncService();

    @Test
    void simpleAsyncTask() {
        service.slowRetrieveInteger(42)
               .then(result -> result.whenSuccess(System.out::println))
               .syncWait();
    }

    @Test
    void simpleAsyncTaskWithTimeout() {
        service.slowRetrieveInteger(4242)
               .on(Timeout.of(10).seconds(), TIMEOUT.asFailure())
               .then(result -> result.whenSuccess(System.out::println))
               .syncWait();
    }

    @Test
    void waitForAllResults1() {
        resultsOf(service.slowRetrieveInteger(123),
              service.slowRetrieveString("text 1"),
              service.slowRetrieveUuid())
                .then(result -> result.whenSuccess(System.out::println))
                .syncWait();
    }

    @Test
    void waitForAllResults3() {
        //Using PromiseResult.resultOf
        PromiseAll.resultsOf(service.slowRetrieveInteger(345),
                             service.slowRetrieveString("text 3"),
                             service.slowRetrieveUuid())
                  .then(result -> result.whenSuccess(System.out::println))
                  .syncWait();
    }
}
