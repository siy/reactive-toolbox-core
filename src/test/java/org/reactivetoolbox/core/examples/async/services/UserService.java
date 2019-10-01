package org.reactivetoolbox.core.examples.async.services;

import org.reactivetoolbox.core.async.BaseError;
import org.reactivetoolbox.core.async.Promise;
import org.reactivetoolbox.core.examples.async.domain.User;
import org.reactivetoolbox.core.examples.async.domain.UserProfile;
import org.reactivetoolbox.core.functional.Either;

import java.util.List;
import java.util.UUID;

public interface UserService {
    Promise<Either<? extends BaseError, UserProfile>> userProfile(UUID userId);
    Promise<Either<? extends  BaseError, List<User>>> followers(UUID userId);
}
