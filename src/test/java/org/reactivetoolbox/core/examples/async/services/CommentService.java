package org.reactivetoolbox.core.examples.async.services;

import org.reactivetoolbox.core.async.PromiseResult;
import org.reactivetoolbox.core.examples.async.domain.Comment;
import org.reactivetoolbox.core.examples.async.domain.Order;
import org.reactivetoolbox.core.examples.async.domain.User;

import java.util.List;

public interface CommentService {
    PromiseResult<List<Comment>> commentsByUser(final User.Id userId, final Order order);
}
