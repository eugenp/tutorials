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
package org.apache.tomcat.websocket.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.DeploymentException;

import org.apache.tomcat.util.res.StringManager;

/**
 * Extracts path parameters from URIs used to create web socket connections
 * using the URI template defined for the associated Endpoint.
 */
public class UriTemplate {

    private static final StringManager sm =
            StringManager.getManager(Constants.PACKAGE_NAME);

    private final String normalized;
    private final List<Segment> segments = new ArrayList<Segment>();
    private final boolean hasParameters;


    public UriTemplate(String path) throws DeploymentException {

        if (path == null || path.length() ==0 || !path.startsWith("/")) {
            throw new DeploymentException(
                    sm.getString("uriTemplate.invalidPath", path));
        }

        StringBuilder normalized = new StringBuilder(path.length());
        Set<String> paramNames = new HashSet<String>();

        // Include empty segments.
        String[] segments = path.split("/", -1);
        int paramCount = 0;
        int segmentCount = 0;

        for (int i = 0; i < segments.length; i++) {
            String segment = segments[i];
            if (segment.length() == 0) {
                if (i == 0 || (i == segments.length - 1 && paramCount == 0)) {
                    // Ignore the first empty segment as the path must always
                    // start with '/'
                    // Ending with a '/' is also OK for instances used for
                    // matches but not for parameterised templates.
                    continue;
                } else {
                    // As per EG discussion, all other empty segments are
                    // invalid
                    throw new IllegalArgumentException(sm.getString(
                            "uriTemplate.emptySegment", path));
                }
            }
            normalized.append('/');
            int index = -1;
            if (segment.startsWith("{") && segment.endsWith("}")) {
                index = segmentCount;
                segment = segment.substring(1, segment.length() - 1);
                normalized.append('{');
                normalized.append(paramCount++);
                normalized.append('}');
                if (!paramNames.add(segment)) {
                    throw new IllegalArgumentException(sm.getString(
                            "uriTemplate.duplicateParameter", segment));
                }
            } else {
                if (segment.contains("{") || segment.contains("}")) {
                    throw new IllegalArgumentException(sm.getString(
                            "uriTemplate.invalidSegment", segment, path));
                }
                normalized.append(segment);
            }
            this.segments.add(new Segment(index, segment));
            segmentCount++;
        }

        this.normalized = normalized.toString();
        this.hasParameters = paramCount > 0;
    }


    public Map<String,String> match(UriTemplate candidate) {

        Map<String,String> result = new HashMap<String, String>();

        // Should not happen but for safety
        if (candidate.getSegmentCount() != getSegmentCount()) {
            return null;
        }

        Iterator<Segment> candidateSegments =
                candidate.getSegments().iterator();
        Iterator<Segment> targetSegments = segments.iterator();

        while (candidateSegments.hasNext()) {
            Segment candidateSegment = candidateSegments.next();
            Segment targetSegment = targetSegments.next();

            if (targetSegment.getParameterIndex() == -1) {
                // Not a parameter - values must match
                if (!targetSegment.getValue().equals(
                        candidateSegment.getValue())) {
                    // Not a match. Stop here
                    return null;
                }
            } else {
                // Parameter
                result.put(targetSegment.getValue(),
                        candidateSegment.getValue());
            }
        }

        return result;
    }


    public boolean hasParameters() {
        return hasParameters;
    }


    public int getSegmentCount() {
        return segments.size();
    }


    public String getNormalizedPath() {
        return normalized;
    }


    private List<Segment> getSegments() {
        return segments;
    }


    private static class Segment {
        private final int parameterIndex;
        private final String value;

        public Segment(int parameterIndex, String value) {
            this.parameterIndex = parameterIndex;
            this.value = value;
        }


        public int getParameterIndex() {
            return parameterIndex;
        }


        public String getValue() {
            return value;
        }
    }
}
