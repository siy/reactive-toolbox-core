package org.reactivetoolbox.core.examples.async;

import org.reactivetoolbox.core.async.Promise;
import org.reactivetoolbox.core.examples.async.domain.Order;
import org.reactivetoolbox.core.examples.async.domain.UserDashboard;
import org.reactivetoolbox.core.examples.async.services.ArticleService;
import org.reactivetoolbox.core.examples.async.services.CommentService;
import org.reactivetoolbox.core.examples.async.services.UserService;
import org.reactivetoolbox.core.functional.Result;

import java.util.UUID;

import static org.reactivetoolbox.core.async.PromiseResult.resultOf;

public class UserProfileHandler_Test {
    private UserService userService;
    private ArticleService articleService;
    private CommentService commentService;

    public Promise<Result<UserDashboard>> userProfileHandler(final UUID userId) {
        return resultOf(userService.userProfile(userId),
                     userService.followers(userId),
                     articleService.articlesByUser(userId, Order.DESC),
                     commentService.commentsByUser(userId, Order.DESC))
                .map(result -> result.mapTuple(UserDashboard::with));
    }
}
