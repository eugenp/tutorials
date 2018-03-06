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

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;

public interface Decoder {

    abstract void init(EndpointConfig endpointConfig);

    abstract void destroy();

    interface Binary<T> extends Decoder {

        T decode(ByteBuffer bytes) throws DecodeException;

        boolean willDecode(ByteBuffer bytes);
    }

    interface BinaryStream<T> extends Decoder {

        T decode(InputStream is) throws DecodeException, IOException;
    }

    interface Text<T> extends Decoder {

        T decode(String s) throws DecodeException;

        boolean willDecode(String s);
    }

    interface TextStream<T> extends Decoder {

        T decode(Reader reader) throws DecodeException, IOException;
    }
}
