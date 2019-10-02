package org.reactivetoolbox.core.examples.async.services;

import org.reactivetoolbox.core.async.Promise;
import org.reactivetoolbox.core.examples.async.domain.Comment;
import org.reactivetoolbox.core.examples.async.domain.Order;
import org.reactivetoolbox.core.functional.Result;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    Promise<Result<List<Comment>>> commentsByUser(UUID userId, Order desc);
}
