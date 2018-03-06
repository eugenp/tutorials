/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javax.websocket;

import java.nio.ByteBuffer;

public class DecodeException extends Exception {

    private static final long serialVersionUID = 1L;

    private ByteBuffer bb;
    private String encodedString;

    public DecodeException(ByteBuffer bb, String message, Throwable cause) {
        super(message, cause);
        this.bb = bb;
    }

    public DecodeException(String encodedString, String message,
            Throwable cause) {
        super(message, cause);
        this.encodedString = encodedString;
    }

    public DecodeException(ByteBuffer bb, String message) {
        super(message);
        this.bb = bb;
    }

    public DecodeException(String encodedString, String message) {
        super(message);
        this.encodedString = encodedString;
    }

    public ByteBuffer getBytes() {
        return bb;
    }

    public String getText() {
        return encodedString;
    }
}
