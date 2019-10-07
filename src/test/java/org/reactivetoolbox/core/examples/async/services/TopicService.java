package org.reactivetoolbox.core.examples.async.services;

import org.reactivetoolbox.core.async.PromiseResult;
import org.reactivetoolbox.core.examples.async.domain.Order;
import org.reactivetoolbox.core.examples.async.domain.Topic;
import org.reactivetoolbox.core.examples.async.domain.User;

import java.util.List;

public interface TopicService {
    PromiseResult<List<Topic>> topicsByUser(final User.Id userId, final Order order);
}
