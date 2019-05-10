package org.baeldung.examples.olingo4.edm;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.sql.Time;
import java.util.AbstractMap.SimpleEntry;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.springframework.stereotype.Component;

@Component
public class EdmTypeMapper {

    public EdmPrimitiveTypeKind java2edm(Class<?> clazz) {
        EdmPrimitiveTypeKind result = java2edm.get(clazz);
        if ( result == null ) {
            throw new IllegalArgumentException("[E19] Unsupported class mapping: class=" + clazz);
        }
        else {
            return result;
        }
    }
    
    // Static map used generate attribute metadada based on Java types
    static Map<Class<?>,EdmPrimitiveTypeKind> java2edm = Collections
      .unmodifiableMap(Stream.of(
        new SimpleEntry<>(Boolean.class,EdmPrimitiveTypeKind.Boolean),
        new SimpleEntry<>(Byte.class,EdmPrimitiveTypeKind.SByte),
        new SimpleEntry<>(Date.class,EdmPrimitiveTypeKind.Date),
        new SimpleEntry<>(Time.class,EdmPrimitiveTypeKind.TimeOfDay),
        new SimpleEntry<>(Number.class,EdmPrimitiveTypeKind.Decimal),
        new SimpleEntry<>(Float.class,EdmPrimitiveTypeKind.Single),
        new SimpleEntry<>(Double.class,EdmPrimitiveTypeKind.Double),
        new SimpleEntry<>(UUID.class,EdmPrimitiveTypeKind.Guid),
        new SimpleEntry<>(Short.class,EdmPrimitiveTypeKind.Int16),
        new SimpleEntry<>(Integer.class,EdmPrimitiveTypeKind.Int32),
        new SimpleEntry<>(Long.class,EdmPrimitiveTypeKind.Int64),
        new SimpleEntry<>(String.class,EdmPrimitiveTypeKind.String)
      
      ).collect(Collectors.toMap((e)-> e.getKey(),(e)-> e.getValue())));

}
