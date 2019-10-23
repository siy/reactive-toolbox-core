package org.reactivetoolbox.core.examples.async;

import org.reactivetoolbox.core.async.Promise;
import org.reactivetoolbox.core.examples.async.domain.Article;
import org.reactivetoolbox.core.examples.async.domain.Order;
import org.reactivetoolbox.core.examples.async.domain.Topic;
import org.reactivetoolbox.core.examples.async.domain.User;
import org.reactivetoolbox.core.examples.async.services.ArticleService;
import org.reactivetoolbox.core.examples.async.services.TopicService;

import java.util.List;

import static org.reactivetoolbox.core.CollectionUtil.map;

public class UserTopicHandler_Test {
    private ArticleService articleService;
    private TopicService topicService;

    public Promise<List<Article>> userTopicHandler(final User.Id userId) {
        return topicService.topicsByUser(userId, Order.ANY)
                           .chainMap(topicsList -> articleService.articlesByUserTopics(userId, map(topicsList, Topic::id)));
    }
}
