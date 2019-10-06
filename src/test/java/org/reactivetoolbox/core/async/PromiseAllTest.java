package org.reactivetoolbox.core.async;

import org.junit.jupiter.api.Test;
import org.reactivetoolbox.core.functional.Tuple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PromiseAllTest {

    @Test
    void allResolvesWhenAllPromisesAreResolvedForOnePromise() {
        final var promise1 = Promise.<Integer>give();
        final var allPromise = PromiseAll.allOf(promise1);

        assertFalse(allPromise.ready());

        promise1.resolve(1);

        assertTrue(allPromise.ready());
        assertEquals(Tuple.with(1), allPromise.value().otherwise(Tuple.with(0)));
    }

    @Test
    void allResolvesWhenAllPromisesAreResolvedFor2Promises() {
        final var promise1 = Promise.<Integer>give();
        final var promise2 = Promise.<Integer>give();
        final var allPromise = PromiseAll.allOf(promise1, promise2);

        assertFalse(allPromise.ready());

        promise1.resolve(1);

        assertFalse(allPromise.ready());

        promise2.resolve(2);

        assertTrue(allPromise.ready());
        assertEquals(Tuple.with(1, 2), allPromise.value().otherwise(Tuple.with(0, 0)));
    }

    @Test
    void allResolvesWhenAllPromisesAreResolvedFor3Promises() {
        final var promise1 = Promise.<Integer>give();
        final var promise2 = Promise.<Integer>give();
        final var promise3 = Promise.<Integer>give();
        final var allPromise = PromiseAll.allOf(promise1, promise2, promise3);

        assertFalse(allPromise.ready());

        promise1.resolve(1);

        assertFalse(allPromise.ready());

        promise2.resolve(2);

        assertFalse(allPromise.ready());

        promise3.resolve(3);

        assertTrue(allPromise.ready());

        assertEquals(Tuple.with(1, 2, 3), allPromise.value().otherwise(Tuple.with(0, 0, 0)));
    }

    @Test
    void allResolvesWhenAllPromisesAreResolvedFor4Promises() {
        final var promise1 = Promise.<Integer>give();
        final var promise2 = Promise.<Integer>give();
        final var promise3 = Promise.<Integer>give();
        final var promise4 = Promise.<Integer>give();
        final var allPromise = PromiseAll.allOf(promise1, promise2, promise3, promise4);

        assertFalse(allPromise.ready());

        promise1.resolve(1);

        assertFalse(allPromise.ready());

        promise2.resolve(2);

        assertFalse(allPromise.ready());

        promise3.resolve(3);

        assertFalse(allPromise.ready());

        promise4.resolve(4);

        assertTrue(allPromise.ready());

        assertEquals(Tuple.with(1, 2, 3, 4), allPromise.value().otherwise(Tuple.with(0, 0, 0, 0)));
    }

    @Test
    void allResolvesWhenAllPromisesAreResolvedFor5Promises() {
        final var promise1 = Promise.<Integer>give();
        final var promise2 = Promise.<Integer>give();
        final var promise3 = Promise.<Integer>give();
        final var promise4 = Promise.<Integer>give();
        final var promise5 = Promise.<Integer>give();
        final var allPromise = PromiseAll.allOf(promise1, promise2, promise3, promise4, promise5);

        assertFalse(allPromise.ready());

        promise1.resolve(1);

        assertFalse(allPromise.ready());

        promise2.resolve(2);

        assertFalse(allPromise.ready());

        promise3.resolve(3);

        assertFalse(allPromise.ready());

        promise4.resolve(4);

        assertFalse(allPromise.ready());

        promise5.resolve(5);

        assertTrue(allPromise.ready());

        assertEquals(Tuple.with(1, 2, 3, 4, 5), allPromise.value().otherwise(Tuple.with(0, 0, 0, 0, 0)));
    }

    @Test
    void allResolvesWhenAllPromisesAreResolvedFor6Promises() {
        final var promise1 = Promise.<Integer>give();
        final var promise2 = Promise.<Integer>give();
        final var promise3 = Promise.<Integer>give();
        final var promise4 = Promise.<Integer>give();
        final var promise5 = Promise.<Integer>give();
        final var promise6 = Promise.<Integer>give();
        final var allPromise = PromiseAll.allOf(promise1, promise2, promise3, promise4, promise5, promise6);

        assertFalse(allPromise.ready());

        promise1.resolve(1);

        assertFalse(allPromise.ready());

        promise2.resolve(2);

        assertFalse(allPromise.ready());

        promise3.resolve(3);

        assertFalse(allPromise.ready());

        promise4.resolve(4);

        assertFalse(allPromise.ready());

        promise5.resolve(5);

        assertFalse(allPromise.ready());

        promise6.resolve(6);

        assertTrue(allPromise.ready());

        assertEquals(Tuple.with(1, 2, 3, 4, 5, 6), allPromise.value().otherwise(Tuple.with(0, 0, 0, 0, 0, 0)));
    }

    @Test
    void allResolvesWhenAllPromisesAreResolvedFor7Promises() {
        final var promise1 = Promise.<Integer>give();
        final var promise2 = Promise.<Integer>give();
        final var promise3 = Promise.<Integer>give();
        final var promise4 = Promise.<Integer>give();
        final var promise5 = Promise.<Integer>give();
        final var promise6 = Promise.<Integer>give();
        final var promise7 = Promise.<Integer>give();
        final var allPromise = PromiseAll.allOf(promise1, promise2, promise3, promise4, promise5, promise6, promise7);

        assertFalse(allPromise.ready());

        promise1.resolve(1);

        assertFalse(allPromise.ready());

        promise2.resolve(2);

        assertFalse(allPromise.ready());

        promise3.resolve(3);

        assertFalse(allPromise.ready());

        promise4.resolve(4);

        assertFalse(allPromise.ready());

        promise5.resolve(5);

        assertFalse(allPromise.ready());

        promise6.resolve(6);

        assertFalse(allPromise.ready());

        promise7.resolve(7);

        assertTrue(allPromise.ready());

        assertEquals(Tuple.with(1, 2, 3, 4, 5, 6, 7), allPromise.value().otherwise(Tuple.with(0, 0, 0, 0, 0, 0, 0)));
    }

    @Test
    void allResolvesWhenAllPromisesAreResolvedFor8Promises() {
        final var promise1 = Promise.<Integer>give();
        final var promise2 = Promise.<Integer>give();
        final var promise3 = Promise.<Integer>give();
        final var promise4 = Promise.<Integer>give();
        final var promise5 = Promise.<Integer>give();
        final var promise6 = Promise.<Integer>give();
        final var promise7 = Promise.<Integer>give();
        final var promise8 = Promise.<Integer>give();
        final var allPromise = PromiseAll.allOf(promise1, promise2, promise3, promise4, promise5, promise6, promise7, promise8);

        assertFalse(allPromise.ready());

        promise1.resolve(1);

        assertFalse(allPromise.ready());

        promise2.resolve(2);

        assertFalse(allPromise.ready());

        promise3.resolve(3);

        assertFalse(allPromise.ready());

        promise4.resolve(4);

        assertFalse(allPromise.ready());

        promise5.resolve(5);

        assertFalse(allPromise.ready());

        promise6.resolve(6);

        assertFalse(allPromise.ready());

        promise7.resolve(7);

        assertFalse(allPromise.ready());

        promise8.resolve(8);

        assertTrue(allPromise.ready());

        assertEquals(Tuple.with(1, 2, 3, 4, 5, 6, 7, 8), allPromise.value().otherwise(Tuple.with(0, 0, 0, 0, 0, 0, 0, 0)));
    }

    @Test
    void allResolvesWhenAllPromisesAreResolvedFor9Promises() {
        final var promise1 = Promise.<Integer>give();
        final var promise2 = Promise.<Integer>give();
        final var promise3 = Promise.<Integer>give();
        final var promise4 = Promise.<Integer>give();
        final var promise5 = Promise.<Integer>give();
        final var promise6 = Promise.<Integer>give();
        final var promise7 = Promise.<Integer>give();
        final var promise8 = Promise.<Integer>give();
        final var promise9 = Promise.<Integer>give();
        final var  allPromise = PromiseAll.allOf(promise1, promise2, promise3, promise4, promise5, promise6, promise7, promise8, promise9);

        assertFalse(allPromise.ready());

        promise1.resolve(1);

        assertFalse(allPromise.ready());

        promise2.resolve(2);

        assertFalse(allPromise.ready());

        promise3.resolve(3);

        assertFalse(allPromise.ready());

        promise4.resolve(4);

        assertFalse(allPromise.ready());

        promise5.resolve(5);

        assertFalse(allPromise.ready());

        promise6.resolve(6);

        assertFalse(allPromise.ready());

        promise7.resolve(7);

        assertFalse(allPromise.ready());

        promise8.resolve(8);

        assertFalse(allPromise.ready());

        promise9.resolve(9);

        assertTrue(allPromise.ready());

        assertEquals(Tuple.with(1, 2, 3, 4, 5, 6, 7, 8, 9), allPromise.value().otherwise(Tuple.with(0, 0, 0, 0, 0, 0, 0, 0, 0)));
    }

    @Test
    void subsequentResolutionsAreIgnoreByAll() {
        final var promise1 = Promise.<Integer>give();
        final var promise2 = Promise.<Integer>give();
        final var allPromise = PromiseAll.allOf(promise1, promise2);

        assertFalse(allPromise.ready());

        promise1.resolve(1);
        promise2.resolve(2);

        assertTrue(allPromise.ready());

        promise1.resolve(3);
        promise2.resolve(4);

        assertEquals(Tuple.with(1, 2), allPromise.value().otherwise(Tuple.with(0, 0)));
    }

}