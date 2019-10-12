package org.reactivetoolbox.core.type;

import org.reactivetoolbox.core.lang.Result;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Simple implementation of type token which allows to capture full generic type.
 * <br />
 * In order to use this class, one should create anonymous instance of it with required
 * type:
 * <pre> {@code
 *  new TypeToken<Map<Key, List<Values>>() {}
 * }</pre>
 *
 * Then this instance can be used to retrieve complete generic type of the created instance.
 * Note that this implementation is rudimentary and does not provide any extras, but it's good
 * fit to purposes of capturing parameter type.
 *
 * See http://gafter.blogspot.com/2006/12/super-type-tokens.html for more details.
 */
public abstract class TypeToken<T> {
    public Result<Type> type() {
        final Type type = getClass().getGenericSuperclass();

        if (type instanceof ParameterizedType) {
            return Result.success(((ParameterizedType) type).getActualTypeArguments()[0]);
        }

        return Error.with(WebErrorTypes.INTERNAL_SERVER_ERROR, "Unable to recognize type $1", type).asFailure();
    }
}
