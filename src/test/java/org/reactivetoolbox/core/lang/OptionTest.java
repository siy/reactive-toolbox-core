package org.reactivetoolbox.core.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

//TODO: fix tests
class OptionTest {
    @Test
    void emptyOptionCanBeCreated() {
        Option.empty()
              .ifPresent(v -> fail());
    }

    @Test
    void optionWithDataCanBeCreated() {
        Option.with("not empty")
              .ifPresent(v -> assertEquals("not empty", v))
              .ifEmpty(Assertions::fail);
    }

    @Test
    void nonEmptyOptionCanBeMappedToOtherOption() {
        Option.with(123)
                .ifEmpty(Assertions::fail)
                .ifPresent(v -> assertEquals(123, v))
                .map(Object::toString)
                .ifEmpty(Assertions::fail)
                .ifPresent(v -> assertEquals("123", v));
    }

    @Test
    void emptyOptionRemainsEmptyAfterMapping() {
        Option.empty()
              .ifPresent(v -> fail())
              .map(Object::toString)
              .ifPresent(v -> fail());
    }

    @Test
    void nonEmptyCanContainNull() {
        Option.with(null)
              .ifEmpty(Assertions::fail)
              .ifPresent(Assertions::assertNull);
    }

    @Test
    void nonEmptyOptionCanBeFlatMappedIntoOtherOption() {
        Option.with(345)
              .ifEmpty(Assertions::fail)
              .ifPresent(v -> assertEquals(345, v))
              .flatMap(val -> Option.with(val + 2))
              .ifEmpty(Assertions::fail)
              .ifPresent(v -> assertEquals(347, v));
    }

    @Test
    void emptyOptionRemainsEmptyAndNotFlatMapped() {
        Option.empty()
              .ifPresent(v -> fail())
              .flatMap(val -> Option.with("not empty"))
              .ifPresent(v -> fail());
    }

    @Test
    void logicalOrChoosesFirsNonEmptyOption1() {
        final var firstNonEmpty = Option.with("1");
        final var secondNonEmpty = Option.with("2");
        final var firstEmpty = Option.<String>empty();
        final var secondEmpty = Option.<String>empty();

        assertEquals(firstNonEmpty, firstNonEmpty.or(() -> secondNonEmpty));
        assertEquals(firstNonEmpty, firstEmpty.or(() -> firstNonEmpty));
        assertEquals(firstNonEmpty, firstEmpty.or(() -> firstNonEmpty).or(() -> secondNonEmpty));
        assertEquals(firstNonEmpty, firstEmpty.or(() -> secondEmpty).or(() -> firstNonEmpty));
    }

    @Test
    void logicalOrChoosesFirsNonEmptyOption2() {
        final var firstNonEmpty = Option.with("1");
        final var secondNonEmpty = Option.with("2");
        final var firstEmpty = Option.<String>empty();
        final var secondEmpty = Option.<String>empty();

        assertEquals(firstNonEmpty, firstNonEmpty.or(secondNonEmpty));
        assertEquals(firstNonEmpty, firstEmpty.or(firstNonEmpty));
        assertEquals(firstNonEmpty, firstEmpty.or(firstNonEmpty).or(secondNonEmpty));
        assertEquals(firstNonEmpty, firstEmpty.or(secondEmpty).or(firstNonEmpty));
    }

    @Test
    void otherwiseProvidesValueIfOptionIsEmpty() {
        assertEquals(123, Option.empty().otherwise(123));
        assertEquals(123, Option.empty().otherwise(() -> 123));
    }

    @Test
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    void optionCanBeStreamed() {
        assertTrue(Option.empty().stream().findFirst().isEmpty());
        assertEquals(123, Option.with(123).stream().findFirst().get());
    }

    @Test
    void nonEmptyInstanceCanBeFiltered() {
        Option.with(123)
              .ifEmpty(Assertions::fail)
              .filter(val -> val > 1)
              .ifEmpty(Assertions::fail)
              .filter(val -> val < 100)
              .ifPresent(val -> fail());
    }

    @Test
    void emptyInstanceRemainsEmptyAfterFilteringAndPredicateIsNotInvoked() {
        Option.empty()
              .ifPresent(v -> fail())
              .filter(v -> true)
              .ifPresent(v -> fail());
    }
}