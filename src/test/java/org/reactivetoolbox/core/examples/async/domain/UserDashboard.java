package org.reactivetoolbox.core.examples.async.domain;

import org.reactivetoolbox.core.lang.List;

public class UserDashboard {
    private final UserProfile user;
    private final List<User> followers;
    private final List<Article> article;
    private final List<Comment> comments;

    private UserDashboard(final UserProfile user,
                          final List<User> followers,
                          final List<Article> article,
                          final List<Comment> comments) {
        this.user = user;
        this.followers = followers;
        this.article = article;
        this.comments = comments;
    }

    public static UserDashboard with(final UserProfile user,
                                     final List<User> followers,
                                     final List<Article> article,
                                     final List<Comment> comments) {
        return new UserDashboard(user, followers, article, comments);
    }
}
