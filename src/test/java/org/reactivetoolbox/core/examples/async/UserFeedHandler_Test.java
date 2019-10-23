package org.reactivetoolbox.core.examples.async;

import org.reactivetoolbox.core.async.PromiseResult;
import org.reactivetoolbox.core.examples.async.domain.Article;
import org.reactivetoolbox.core.examples.async.domain.Order;
import org.reactivetoolbox.core.examples.async.domain.Topic;
import org.reactivetoolbox.core.examples.async.domain.User;
import org.reactivetoolbox.core.examples.async.services.ArticleService;
import org.reactivetoolbox.core.examples.async.services.TopicService;
import org.reactivetoolbox.core.examples.async.services.UserService;
import org.reactivetoolbox.core.scheduler.Errors;

import java.util.List;

import static org.reactivetoolbox.core.CollectionUtil.map;
import static org.reactivetoolbox.core.async.PromiseAll.all;
import static org.reactivetoolbox.core.lang.Result.failure;
import static org.reactivetoolbox.core.scheduler.Timeout.timeout;

public class UserFeedHandler_Test {
    private ArticleService articleService;
    private TopicService topicService;
    private UserService userService;

    public PromiseResult<List<Article>> userFeedHandler(final User.Id userId) {
        return all(topicService.topicsByUser(userId, Order.ANY),
                   userService.followers(userId))
                .chainMap(tuple -> tuple.map((topics, users) -> articleService.userFeed(map(topics, Topic::id), map(users, User::id))))
                .on(timeout(30).seconds(), failure(Errors.TIMEOUT));
    }
}
