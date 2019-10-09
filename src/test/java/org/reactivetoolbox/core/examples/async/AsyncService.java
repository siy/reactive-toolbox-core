package org.reactivetoolbox.core.examples.async;

import org.reactivetoolbox.core.async.PromiseResult;
import org.reactivetoolbox.core.functional.Result;
import org.reactivetoolbox.core.meta.AppMetaRepository;
import org.reactivetoolbox.core.scheduler.TaskScheduler;
import org.reactivetoolbox.core.scheduler.Timeout;

import java.util.UUID;

public class AsyncService {
    private static final Timeout DEFAULT_DELAY = Timeout.of(50).millis();

    public PromiseResult<Integer> slowRetrieveInteger(final Integer value) {
        return slowRetrieveInteger(DEFAULT_DELAY, value);
    }

    public PromiseResult<Integer> slowRetrieveInteger(final Timeout delay, final Integer value) {
        return slowRetrieve(Integer.class, delay, value);
    }

    public PromiseResult<String> slowRetrieveString(final String value) {
        return slowRetrieveString(DEFAULT_DELAY, value);
    }

    public PromiseResult<String> slowRetrieveString(final Timeout delay, final String value) {
        return slowRetrieve(String.class, delay, value);
    }

    public PromiseResult<UUID> slowRetrieveUuid() {
        return slowRetrieveUuid(UUID.randomUUID());
    }

    public PromiseResult<UUID> slowRetrieveUuid(final UUID value) {
        return slowRetrieveUuid(DEFAULT_DELAY, value);
    }

    public PromiseResult<UUID> slowRetrieveUuid(final Timeout delay, final UUID value) {
        return slowRetrieve(UUID.class, delay, value);
    }

    private static <T> PromiseResult<T> slowRetrieve(final Class<T> clazz, final Timeout delay, final T value) {
        return PromiseResult.<T>result().with(delay, Result.success(value));
    }

    private static final class TaskSchedulerHolder {
        private static final TaskScheduler taskScheduler = AppMetaRepository.instance().seal().get(TaskScheduler.class);

        static TaskScheduler instance() {
            return taskScheduler;
        }
    }
}
