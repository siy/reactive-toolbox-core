package org.reactivetoolbox.core.examples.async.services;

import org.reactivetoolbox.core.async.Promise;
import org.reactivetoolbox.core.examples.async.domain.Order;
import org.reactivetoolbox.core.examples.async.domain.User;
import org.reactivetoolbox.core.lang.Collection;

public interface ArticleService {
    Promise<Collection<org.reactivetoolbox.core.examples.async.domain.Article>> articlesByUser(final User.Id userId, final Order order);
    Promise<Collection<org.reactivetoolbox.core.examples.async.domain.Article>> articlesByUserTopics(final User.Id userId, final Collection<org.reactivetoolbox.core.examples.async.domain.Topic.Id> topicIds);
    // Returns list of articles for specified topics posted by specified users
    Promise<Collection<org.reactivetoolbox.core.examples.async.domain.Article>> userFeed(final Collection<org.reactivetoolbox.core.examples.async.domain.Topic.Id> topics, final Collection<org.reactivetoolbox.core.examples.async.domain.User.Id> users);
}
