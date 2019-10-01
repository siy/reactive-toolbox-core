package org.reactivetoolbox.core.examples.async;

import org.reactivetoolbox.core.async.BaseError;
import org.reactivetoolbox.core.async.Promise;
import org.reactivetoolbox.core.examples.async.domain.Order;
import org.reactivetoolbox.core.examples.async.domain.UserDashboard;
import org.reactivetoolbox.core.examples.async.services.ArticleService;
import org.reactivetoolbox.core.examples.async.services.CommentService;
import org.reactivetoolbox.core.examples.async.services.UserService;
import org.reactivetoolbox.core.functional.Either;

import java.util.UUID;

import static org.reactivetoolbox.core.async.Promise.zipAll;
import static org.reactivetoolbox.core.functional.Either.mapTuple;

public class UserProfileHandler_Test {
    private UserService userService;
    private ArticleService articleService;
    private CommentService commentService;

    public Promise<Either<? extends BaseError, UserDashboard>> userProfileHandler(final UUID userId) {
        return zipAll(userService.userProfile(userId),
                      userService.followers(userId),
                      articleService.articlesByUser(userId, Order.DESC),
                      commentService.commentsByUser(userId, Order.DESC))
                .map(result -> mapTuple(result, UserDashboard::with));
    }
}
