package com.baeldung.graphql.fileupload;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class MultipartVariableMapper {

    private static final Pattern PERIOD = Pattern.compile("\\.");

    private static final MultipartVariableMapper.Mapper<Map<String, Object>> MAP_MAPPER = new MultipartVariableMapper.Mapper<Map<String, Object>>() {
        @Override
        public <P> Object set(Map<String, Object> location, String target, P value) {
            return location.put(target, value);
        }

        @Override
        public Object recurse(Map<String, Object> location, String target) {
            return location.get(target);
        }
    };
    private static final MultipartVariableMapper.Mapper<List<Object>> LIST_MAPPER = new MultipartVariableMapper.Mapper<List<Object>>() {
        @Override
        public <P> Object set(List<Object> location, String target, P value) {
            return location.set(Integer.parseInt(target), value);
        }

        @Override
        public Object recurse(List<Object> location, String target) {
            return location.get(Integer.parseInt(target));
        }
    };

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <P> void mapVariable(String objectPath, Map<String, Object> variables, P file) {
        String[] segments = PERIOD.split(objectPath);

        if (segments.length < 2) {
            throw new RuntimeException("object-path in map must have at least two segments");
        } else if (!"variables".equals(segments[0])) {
            throw new RuntimeException("can only map into variables");
        }

        Object currentLocation = variables;
        for (int i = 1; i < segments.length; i++) {
            String segmentName = segments[i];
            MultipartVariableMapper.Mapper mapper = determineMapper(currentLocation, objectPath, segmentName);

            if (i == segments.length - 1) {
                if (null != mapper.set(currentLocation, segmentName, file)) {
                    throw new RuntimeException("expected null value when mapping " + objectPath);
                }
            } else {
                currentLocation = mapper.recurse(currentLocation, segmentName);
                if (null == currentLocation) {
                    throw new RuntimeException("found null intermediate value when trying to map " + objectPath);
                }
            }
        }
    }

    private static MultipartVariableMapper.Mapper<?> determineMapper(Object currentLocation, String objectPath, String segmentName) {
        if (currentLocation instanceof Map) {
            return MAP_MAPPER;
        } else if (currentLocation instanceof List) {
            return LIST_MAPPER;
        }

        throw new RuntimeException("expected a map or list at " + segmentName + " when trying to map " + objectPath);
    }

    interface Mapper<T> {

        <P> Object set(T location, String target, P value);

        Object recurse(T location, String target);
    }
}