package org.reactivetoolbox.core.functional;

import org.reactivetoolbox.core.functional.Functions.FN1;

/**
 * The core interface which represents variables which can hold either one value
 * or other, but not both.
 *
 * @param <L>
 * @param <R>
 */
public interface Either<L, R> {
    <T> T map(FN1<? extends T, ? super L> leftMapper, FN1<? extends T, ? super R> rightMapper);
}
