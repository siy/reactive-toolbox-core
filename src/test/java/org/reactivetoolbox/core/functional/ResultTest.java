package org.reactivetoolbox.core.functional;

import org.junit.jupiter.api.Test;
import org.reactivetoolbox.core.scheduler.SchedulerError;

class ResultTest {
    @Test
    void name() {
        final var resultSuccess = Result.Result1.from(Result.success(1).flatMap(vv11 -> Result.success(Tuple.with(vv11))));
        final var resultFailure = Result.Result1.from(Result.failure(SchedulerError.TIMEOUT).flatMap(vv1 -> Result.success(Tuple.with(vv1))));
    }
}