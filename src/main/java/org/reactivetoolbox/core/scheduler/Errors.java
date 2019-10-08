package org.reactivetoolbox.core.scheduler;
/*
 * Copyright (c) 2017-2019 Sergiy Yevtushenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.reactivetoolbox.core.type.Error;
import org.reactivetoolbox.core.type.WebErrorTypes;

/**
 * Scheduler errors
 */
public interface Errors {
    Error TIMEOUT = Error.of(WebErrorTypes.REQUEST_TIMEOUT, "Processing timeout error");
    Error CANCELLED = Error.of(WebErrorTypes.NO_RESPONSE, "Request cancelled");
}