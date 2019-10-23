package org.reactivetoolbox.core.scheduler;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeoutTest {
    @Test
    void timeoutCreatedProperly() {
        assertEquals(1234, Timeout.timeout(1234).millis().timeout());
        assertEquals(TimeUnit.SECONDS.toMillis(123), Timeout.timeout(123).seconds().timeout());
        assertEquals(TimeUnit.MINUTES.toMillis(12), Timeout.timeout(12).minutes().timeout());
        assertEquals(TimeUnit.HOURS.toMillis(32), Timeout.timeout(32).hours().timeout());
    }
}