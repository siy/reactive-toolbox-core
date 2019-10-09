package org.reactivetoolbox.core.examples.async.services;

import org.reactivetoolbox.core.async.PromiseResult;
import org.reactivetoolbox.core.examples.async.domain.User;
import org.reactivetoolbox.core.examples.async.domain.UserProfile;

import java.util.List;

public interface UserService {
    PromiseResult<UserProfile> userProfile(final User.Id userId);
    PromiseResult<List<User>> followers(final User.Id userId);
}
