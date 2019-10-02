package org.reactivetoolbox.core.examples.async.services;

import org.reactivetoolbox.core.async.Promise;
import org.reactivetoolbox.core.examples.async.domain.User;
import org.reactivetoolbox.core.examples.async.domain.UserProfile;
import org.reactivetoolbox.core.functional.Result;

import java.util.List;
import java.util.UUID;

public interface UserService {
    Promise<Result<UserProfile>> userProfile(UUID userId);
    Promise<Result<List<User>>> followers(UUID userId);
}
