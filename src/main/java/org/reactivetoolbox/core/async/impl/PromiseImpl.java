package org.reactivetoolbox.core.async.impl;

/*
 * Copyright (c) 2019 Sergiy Yevtushenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.reactivetoolbox.core.async.Promise;
import org.reactivetoolbox.core.lang.Option;
import org.reactivetoolbox.core.meta.AppMetaRepository;
import org.reactivetoolbox.core.scheduler.TaskScheduler;
import org.reactivetoolbox.core.scheduler.Timeout;

import java.util.StringJoiner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.function.Consumer;

/**
 * Implementation of {@link Promise}
 */
public class PromiseImpl<T> implements Promise<T> {
    private final AtomicMarkableReference<T> value = new AtomicMarkableReference<>(null, false);
    private final BlockingQueue<Consumer<T>> thenActions = new LinkedBlockingQueue<>();

    public PromiseImpl() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Option<T> value() {
        return Option.of(value.getReference());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean ready() {
        return value.isMarked();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<T> resolve(final T result) {
        if (value.compareAndSet(null, result, false, true)) {
            thenActions.forEach(action -> action.accept(value.getReference()));
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<T> resolveAsync(final T result) {
        return async(promise -> promise.resolve(result));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<T> then(final Consumer<T> action) {
        if (value.isMarked()) {
            action.accept(value.getReference());
        } else {
            thenActions.offer(action);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<T> syncWait() {
        final var latch = new CountDownLatch(1);
        then(value -> latch.countDown());

        try {
            latch.await();
        } catch (final InterruptedException e) {
            // Ignore exception
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<T> syncWait(final Timeout timeout) {
        final var latch = new CountDownLatch(1);
        then(value -> latch.countDown());

        try {
            latch.await(timeout.timeout(), TimeUnit.MILLISECONDS);
        } catch (final InterruptedException e) {
            // Ignore exception
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<T> async(final Consumer<Promise<T>> task) {
        TaskSchedulerHolder.instance().submit(() -> task.accept(this));
        return this;
    }

    @Override
    public Promise<T> async(final Timeout timeout, final Consumer<Promise<T>> task) {
        TaskSchedulerHolder.instance().submit(timeout, () -> task.accept(this));
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "Promise(", ")")
                .add(ready() ? value.getReference().toString() : "")
                .toString();
    }

    private static final class TaskSchedulerHolder {
        private static final TaskScheduler taskScheduler = AppMetaRepository.instance().seal().get(TaskScheduler.class);

        static TaskScheduler instance() {
            return taskScheduler;
        }
    }
}
