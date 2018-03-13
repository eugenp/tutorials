/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.coyote;

public enum ErrorState {

    /**
     * Not in an error state.
     */
    NONE(false, 0, true),

    /**
     * The current request/response is in an error state and while it is safe to
     * complete the current response it is not safe to continue to use the
     * existing connection which must be closed once the response has been
     * completed.
     */
    CLOSE_CLEAN(true, 1, true),

    /**
     * The current request/response is in an error state and it is not safe to
     * continue to use the existing connection which must be closed immediately.
     */
    CLOSE_NOW(true, 2, false);

    private final boolean error;
    private final int severity;
    private final boolean ioAllowed;

    private ErrorState(boolean error, int severity, boolean ioAllowed) {
        this.error = error;
        this.severity = severity;
        this.ioAllowed = ioAllowed;
    }

    public boolean isError() {
        return error;
    }

    /**
     * Compare this ErrorState with the provided ErrorState and return the most
     * severe.
     */
    public ErrorState getMostSevere(ErrorState input) {
        if (input.severity > this.severity) {
            return input;
        } else {
            return this;
        }
    }

    public boolean isIoAllowed() {
        return ioAllowed;
    }
}
