package org.reactivetoolbox.core.examples.async.services;

import org.reactivetoolbox.core.async.PromiseResult;
import org.reactivetoolbox.core.examples.async.domain.Article;
import org.reactivetoolbox.core.examples.async.domain.Order;
import org.reactivetoolbox.core.examples.async.domain.Topic;
import org.reactivetoolbox.core.examples.async.domain.User;

import java.util.List;

public interface ArticleService {
    PromiseResult<List<Article>> articlesByUser(final User.Id userId, final Order order);
    PromiseResult<List<Article>> articlesByUserTopics(final User.Id userId, final List<Topic.Id> topicIds);
    // Returns list of articles for specified topics posted by specified users
    PromiseResult<List<Article>> userFeed(final List<Topic.Id> topics, final List<User.Id> users);
}
