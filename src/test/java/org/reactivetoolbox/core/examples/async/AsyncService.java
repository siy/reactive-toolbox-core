package org.reactivetoolbox.core.examples.async;

import org.reactivetoolbox.core.async.BaseError;
import org.reactivetoolbox.core.async.Promise;
import org.reactivetoolbox.core.functional.Either;
import org.reactivetoolbox.core.meta.AppMetaRepository;
import org.reactivetoolbox.core.scheduler.TaskScheduler;
import org.reactivetoolbox.core.scheduler.Timeout;

import java.util.UUID;

public class AsyncService {
    private static final Timeout DEFAULT_DELAY = Timeout.of(50).millis();

    public Promise<Either<? extends BaseError, Integer>> slowRetrieveInteger(final Integer value) {
        return slowRetrieveInteger(DEFAULT_DELAY, value);
    }

    public Promise<Either<? extends BaseError, Integer>> slowRetrieveInteger(final Timeout delay, final Integer value) {
        return slowRetrieve(Integer.class, delay, value);
    }

    public Promise<Either<? extends BaseError, String>> slowRetrieveString(final String value) {
        return slowRetrieveString(DEFAULT_DELAY, value);
    }

    public Promise<Either<? extends BaseError, String>> slowRetrieveString(final Timeout delay, final String value) {
        return slowRetrieve(String.class, delay, value);
    }

    public Promise<Either<? extends BaseError, UUID>> slowRetrieveUuid() {
        return slowRetrieveUuid(UUID.randomUUID());
    }

    public Promise<Either<? extends BaseError, UUID>> slowRetrieveUuid(final UUID value) {
        return slowRetrieveUuid(DEFAULT_DELAY, value);
    }

    public Promise<Either<? extends BaseError, UUID>> slowRetrieveUuid(final Timeout delay, final UUID value) {
        return slowRetrieve(UUID.class, delay, value);
    }

    private static <T> Promise<Either<? extends BaseError, T>> slowRetrieve(final Class<T> clazz, final Timeout delay, final T value) {
        final var result = Promise.either(clazz);
        TaskSchedulerHolder.instance().submit(delay, () -> result.resolve(Either.success(value)));
        return result;
    }

    private static final class TaskSchedulerHolder {
        private static final TaskScheduler taskScheduler = AppMetaRepository.instance().seal().get(TaskScheduler.class);

        static TaskScheduler instance() {
            return taskScheduler;
        }
    }
}
