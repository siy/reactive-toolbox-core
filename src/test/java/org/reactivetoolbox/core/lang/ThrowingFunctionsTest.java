package org.reactivetoolbox.core.lang;

import org.junit.jupiter.api.Test;
import org.reactivetoolbox.core.lang.ThrowingFunctions.TFN1;
import org.reactivetoolbox.core.lang.support.WebFailureTypes;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.reactivetoolbox.core.lang.ThrowingFunctions.lift;

class ThrowingFunctionsTest {

    @Test
    void lift1() {
        final var uriParser = lift((TFN1<URI, String>) URI::new);

        uriParser.apply("https://dev.to/")
                 .ifFailure(failure -> fail())
                 .ifSuccess(uri -> assertEquals("https://dev.to/", uri.toString()));

        uriParser.apply(":malformed/url")
                 .ifFailure(failure -> assertEquals(failure.type(), WebFailureTypes.BAD_REQUEST))
                 .ifSuccess(uri -> fail());
    }
}