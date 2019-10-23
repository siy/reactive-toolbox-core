package org.reactivetoolbox.core.lang;

import org.junit.jupiter.api.Test;
import org.reactivetoolbox.core.lang.support.WebFailureTypes;
import org.reactivetoolbox.core.scheduler.Errors;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.reactivetoolbox.core.lang.Result.failure;
import static org.reactivetoolbox.core.lang.Result.success;
import static org.reactivetoolbox.core.lang.support.WebFailureTypes.INTERNAL_SERVER_ERROR;

class ResultTest {
    static final Failure TEST_FAILURE = Failure.of(INTERNAL_SERVER_ERROR, "Test error");

    @Test
    void equalsFollowsContract() {
        assertEquals(success("1"), success("1"));
        assertNotEquals(success(1), success(2));
        assertNotEquals(success(1), failure(Errors.TIMEOUT));
    }

    @Test
    void orSelectsFirstSuccess() {
        assertEquals(success(1), success(1).or(success(2)).or(success(3)));
        assertEquals(success(2), failure(TEST_FAILURE).or(success(2)).or(success(3)));
        assertEquals(success(3), failure(TEST_FAILURE).or(failure(TEST_FAILURE)).or(success(3)));
    }

    @Test
    void ifSuccessCalledForSuccessResult() {
        final Integer[] result = new Integer[1];
        success(5).onSuccess(v -> result[0] = v);
        assertEquals(5, result[0]);
    }

    @Test
    void ifFailureCalledForSuccessResult() {
        final Failure[] result = new Failure[1];
        failure(TEST_FAILURE).onFailure(v -> result[0] = v);
        assertEquals(TEST_FAILURE, result[0]);
    }

    @Test
    void rangeValidatorTest() {
        validateBetween(19, 20, 100)
                .map(Objects::toString)
                .onSuccess(val -> fail());
        validateBetween(101, 20, 100)
                .map(Objects::toString)
                .onSuccess(val -> fail());
        validateBetween(20, 20, 100)
                .map(Objects::toString)
                .onFailure(val -> fail());
        validateBetween(100, 20, 100)
                .map(Objects::toString)
                .onFailure(val -> fail());
        validateBetween(60, 20, 100)
                .map(Objects::toString)
                .onFailure(val -> fail());
    }

    private Result<Integer> validateBetween(final int value, final int min, final int max) {
        return validateGE(value, min).flatMap(val -> validateLE(val, max));
    }

    private Result<Integer> validateGE(final int value, final int min) {
        return value < min ? failure(Failure.with(WebFailureTypes.UNPROCESSABLE_ENTITY, "Input value below %d", min))
                           : success(value);
    }

    private Result<Integer> validateLE(final int value, final int max) {
        return value > max ? failure(Failure.with(WebFailureTypes.UNPROCESSABLE_ENTITY, "Input value above %d", max))
                           : success(value);
    }
}