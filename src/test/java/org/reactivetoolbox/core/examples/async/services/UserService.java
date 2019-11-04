package org.reactivetoolbox.core.examples.async.services;

import org.reactivetoolbox.core.async.Promise;
import org.reactivetoolbox.core.examples.async.domain.User;
import org.reactivetoolbox.core.examples.async.domain.UserProfile;
import org.reactivetoolbox.core.lang.List;

public interface UserService {
    Promise<UserProfile> userProfile(final User.Id userId);
    Promise<List<User>> followers(final User.Id userId);
}
