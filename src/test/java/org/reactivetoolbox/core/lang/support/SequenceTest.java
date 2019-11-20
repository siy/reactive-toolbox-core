package org.reactivetoolbox.core.lang.support;

import org.junit.jupiter.api.Test;
import org.reactivetoolbox.core.lang.Failure;
import org.reactivetoolbox.core.lang.List;
import org.reactivetoolbox.core.lang.Option;
import org.reactivetoolbox.core.lang.Result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.reactivetoolbox.core.lang.support.Sequence.empty;
import static org.reactivetoolbox.core.lang.support.Sequence.sequence;

class SequenceTest {

    @Test
    void collectsZeroElementsFromEmptySequence() {
        empty().collect(list -> list)
               .onFailure(f -> fail())
               .onSuccess(list -> assertEquals(0, list.size()));
    }

    @Test
    void collectsAllElements() {
        sequence(1, 2, 3, 4, 5).collect()
                               .onFailure(f -> fail())
                               .onSuccess(list -> assertEquals(List.list(1, 2, 3, 4, 5), list));
    }

    private Result<Option<Object>> newError(final String message) {
        return Result.failure(Failure.failure(WebFailureTypes.INTERNAL_SERVER_ERROR, message));
    }
}