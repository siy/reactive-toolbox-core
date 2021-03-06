package org.reactivetoolbox.core.lang;

import org.junit.jupiter.api.Test;
import org.reactivetoolbox.core.Errors;
import org.reactivetoolbox.core.lang.support.WebFailureTypes;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.reactivetoolbox.core.lang.Result.ok;
import static org.reactivetoolbox.core.lang.support.WebFailureTypes.INTERNAL_SERVER_ERROR;

class ResultTest {
    static final Failure TEST_FAILURE = Failure.failure(INTERNAL_SERVER_ERROR, "Test error");

    @Test
    void equalsFollowsContract() {
        assertEquals(ok("1"), ok("1"));
        assertNotEquals(ok(1), ok(2));
        assertNotEquals(ok(1), Result.fail(Errors.TIMEOUT));
    }

    @Test
    void orSelectsFirstSuccess() {
        assertEquals(ok(1), ok(1).or(ok(2)).or(ok(3)));
        assertEquals(ok(2), Result.fail(TEST_FAILURE).or(ok(2)).or(ok(3)));
        assertEquals(ok(3), Result.fail(TEST_FAILURE).or(Result.fail(TEST_FAILURE)).or(ok(3)));
    }

    @Test
    void ifSuccessCalledForSuccessResult() {
        final Integer[] result = new Integer[1];
        ok(5).onSuccess(v -> result[0] = v);
        assertEquals(5, result[0]);
    }

    @Test
    void ifFailureCalledForSuccessResult() {
        final Failure[] result = new Failure[1];
        Result.fail(TEST_FAILURE).onFailure(v -> result[0] = v);
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
        return value < min ? Result.fail(Failure.failure(WebFailureTypes.UNPROCESSABLE_ENTITY, "Input value below %d", min))
                           : ok(value);
    }

    private Result<Integer> validateLE(final int value, final int max) {
        return value > max ? Result.fail(Failure.failure(WebFailureTypes.UNPROCESSABLE_ENTITY, "Input value above %d", max))
                           : ok(value);
    }
}