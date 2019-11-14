package org.reactivetoolbox.core.examples.async.domain;

import org.reactivetoolbox.core.lang.Collection;

public class UserDashboard {
    private final UserProfile user;
    private final Collection<org.reactivetoolbox.core.examples.async.domain.User> followers;
    private final Collection<org.reactivetoolbox.core.examples.async.domain.Article> article;
    private final Collection<org.reactivetoolbox.core.examples.async.domain.Comment> comments;

    private UserDashboard(final UserProfile user,
                          final Collection<org.reactivetoolbox.core.examples.async.domain.User> followers,
                          final Collection<org.reactivetoolbox.core.examples.async.domain.Article> article,
                          final Collection<org.reactivetoolbox.core.examples.async.domain.Comment> comments) {
        this.user = user;
        this.followers = followers;
        this.article = article;
        this.comments = comments;
    }

    public static UserDashboard with(final UserProfile user,
                                     final Collection<org.reactivetoolbox.core.examples.async.domain.User> followers,
                                     final Collection<org.reactivetoolbox.core.examples.async.domain.Article> article,
                                     final Collection<org.reactivetoolbox.core.examples.async.domain.Comment> comments) {
        return new UserDashboard(user, followers, article, comments);
    }
}
