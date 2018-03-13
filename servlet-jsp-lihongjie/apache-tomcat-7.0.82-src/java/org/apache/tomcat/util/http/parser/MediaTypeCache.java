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
package org.apache.tomcat.util.http.parser;

import java.io.IOException;
import java.io.StringReader;

import org.apache.tomcat.util.collections.ConcurrentCache;

/**
 * Caches the results of parsing content-type headers.
 */
public class MediaTypeCache {

    private final ConcurrentCache<String,String[]> cache;

    public MediaTypeCache(int size) {
        cache = new ConcurrentCache<String,String[]>(size);
    }

    /**
     * Looks in the cache and returns the cached value if one is present. If no
     * match exists in the cache, a new parser is created, the input parsed and
     * the results placed in the cache and returned to the user.
     *
     * @param input The content-type header value to parse
     * @return      The results are provided as a two element String array. The
     *                  first element is the media type less the charset and
     *                  the second element is the charset
     */
    public String[] parse(String input) {
        String[] result = cache.get(input);

        if (result != null) {
            return result;
        }

        MediaType m = null;
        try {
            m = HttpParser.parseMediaType(new StringReader(input));
        } catch (IOException e) {
            // Ignore - return null
        }
        if (m != null) {
            result = new String[] {m.toStringNoCharset(), m.getCharset()};
            cache.put(input, result);
        }

        return result;
    }
}
