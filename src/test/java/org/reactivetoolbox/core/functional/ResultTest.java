package org.reactivetoolbox.core.functional;

import org.junit.jupiter.api.Test;
import org.reactivetoolbox.core.functional.Result.Result1;
import org.reactivetoolbox.core.scheduler.SchedulerError;
import org.reactivetoolbox.core.type.Error;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.reactivetoolbox.core.functional.Result.failure;
import static org.reactivetoolbox.core.functional.Result.success;
import static org.reactivetoolbox.core.functional.Tuple.with;
import static org.reactivetoolbox.core.type.WebErrorTypes.INTERNAL_SERVER_ERROR;

class ResultTest {
    static final Error TEST_ERROR = Error.of(INTERNAL_SERVER_ERROR, "Test error");

    @Test
    void flatMapIsSafeInRegardToTypes() {
        Result1.from(success(1).flatMap(vv11 -> success(with(vv11))));
        Result1.from(failure(TEST_ERROR).flatMap(vv1 -> success(with(vv1))));
    }

    @Test
    void equalsFollowsContract() {
        assertEquals(success("1"), success("1"));
        assertNotEquals(success(1), success(2));
        assertNotEquals(success(1), failure(SchedulerError.TIMEOUT));
    }

    @Test
    void orSelectsFirstSuccess() {
        assertEquals(success(1), success(1).or(success(2)).or(success(3)));
        assertEquals(success(2), failure(TEST_ERROR).or(success(2)).or(success(3)));
        assertEquals(success(3), failure(TEST_ERROR).or(failure(TEST_ERROR)).or(success(3)));
    }

    @Test
    void ifSuccessCalledForSuccessResult() {
        final Integer[] result = new Integer[1];
        success(5).ifSuccess(v -> result[0] = v);
        assertEquals(5, result[0]);
    }

    @Test
    void ifFailureCalledForSuccessResult() {
        final Error[] result = new Error[1];
        failure(TEST_ERROR).ifFailure(v -> result[0] = v);
        assertEquals(TEST_ERROR, result[0]);
    }
}