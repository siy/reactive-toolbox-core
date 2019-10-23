package org.reactivetoolbox.core.examples.async;

import org.junit.jupiter.api.Test;

import static org.reactivetoolbox.core.async.PromiseAll.all;
import static org.reactivetoolbox.core.lang.Result.failure;
import static org.reactivetoolbox.core.scheduler.Errors.TIMEOUT;
import static org.reactivetoolbox.core.scheduler.Timeout.timeout;

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
               .on(timeout(10).seconds(), failure(TIMEOUT))
               .then(result -> result.whenSuccess(System.out::println))
               .syncWait();
    }

    @Test
    void waitForAllResults1() {
        all(service.slowRetrieveInteger(123),
            service.slowRetrieveString("text 1"),
            service.slowRetrieveUuid())
                .then(result -> result.whenSuccess(System.out::println))
                .syncWait();
    }
}
