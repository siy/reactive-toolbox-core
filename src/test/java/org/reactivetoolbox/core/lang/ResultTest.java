package org.reactivetoolbox.core.lang;

import org.junit.jupiter.api.Test;
import org.reactivetoolbox.core.lang.Result.Result1;
import org.reactivetoolbox.core.scheduler.Errors;
import org.reactivetoolbox.core.type.Error;
import org.reactivetoolbox.core.type.WebErrorTypes;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.reactivetoolbox.core.lang.Result.failure;
import static org.reactivetoolbox.core.lang.Result.success;
import static org.reactivetoolbox.core.lang.Tuple.with;
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
        assertNotEquals(success(1), failure(Errors.TIMEOUT));
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

    @Test
    void rangeValidatorTest() {
        validateBetween(19, 20, 100)
                .map(Objects::toString)
                .ifSuccess(val -> fail());
        validateBetween(101, 20, 100)
                .map(Objects::toString)
                .ifSuccess(val -> fail());
        validateBetween(20, 20, 100)
                .map(Objects::toString)
                .ifFailure(val -> fail());
        validateBetween(100, 20, 100)
                .map(Objects::toString)
                .ifFailure(val -> fail());
        validateBetween(60, 20, 100)
                .map(Objects::toString)
                .ifFailure(val -> fail());
    }

    private Result<Integer> validateBetween(final int value, final int min, final int max) {
        return validateGE(value, min).flatMap(val -> validateLE(val, max));
    }

    private Result<Integer> validateGE(final int value, final int min) {
        return value < min ? failure(Error.with(WebErrorTypes.UNPROCESSABLE_ENTITY, "Input value below %d", min))
                           : success(value);
    }

    private Result<Integer> validateLE(final int value, final int max) {
        return value > max ? failure(Error.with(WebErrorTypes.UNPROCESSABLE_ENTITY, "Input value above %d", max))
                           : success(value);
    }
}