package org.reactivetoolbox.core.examples.async.services;

import org.reactivetoolbox.core.async.BaseError;
import org.reactivetoolbox.core.async.Promise;
import org.reactivetoolbox.core.examples.async.domain.Article;
import org.reactivetoolbox.core.examples.async.domain.Order;
import org.reactivetoolbox.core.functional.Either;

import java.util.List;
import java.util.UUID;

public interface ArticleService {
    Promise<Either<? extends BaseError, List<Article>>> articlesByUser(UUID userId, Order desc);
}
