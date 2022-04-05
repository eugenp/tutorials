package com.baeldung.gson.advance;

/*
 * Copyright (C) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Adapts values whose runtime type may differ from their declaration type. This
 * is necessary when a field's type is not the same type that GSON should create
 * when deserializing that field. For example, consider these types:
 * <pre>   {@code
 *   abstract class Shape {
 *     int x;
 *     int y;
 *   }
 *   class Circle extends Shape {
 *     int radius;
 *   }
 *   class Rectangle extends Shape {
 *     int width;
 *     int height;
 *   }
 *   class Diamond extends Shape {
 *     int width;
 *     int height;
 *   }
 *   class Drawing {
 *     Shape bottomShape;
 *     Shape topShape;
 *   }
 * }</pre>
 * <p>Without additional type information, the serialized JSON is ambiguous. Is
 * the bottom shape in this drawing a rectangle or a diamond? <pre>   {@code
 *   {
 *     "bottomShape": {
 *       "width": 10,
 *       "height": 5,
 *       "x": 0,
 *       "y": 0
 *     },
 *     "topShape": {
 *       "radius": 2,
 *       "x": 4,
 *       "y": 1
 *     }
 *   }}</pre>
 * This class addresses this problem by adding type information to the
 * serialized JSON and honoring that type information when the JSON is
 * deserialized: <pre>   {@code
 *   {
 *     "bottomShape": {
 *       "type": "Diamond",
 *       "width": 10,
 *       "height": 5,
 *       "x": 0,
 *       "y": 0
 *     },
 *     "topShape": {
 *       "type": "Circle",
 *       "radius": 2,
 *       "x": 4,
 *       "y": 1
 *     }
 *   }}</pre>
 * Both the type field name ({@code "type"}) and the type labels ({@code
 * "Rectangle"}) are configurable.
 *
 * <h3>Registering Types</h3>
 * Create a {@code RuntimeTypeAdapterFactory} by passing the base type and type field
 * name to the {@link #of} factory method. If you don't supply an explicit type
 * field name, {@code "type"} will be used. <pre>   {@code
 *   RuntimeTypeAdapterFactory<Shape> shapeAdapterFactory
 *       = RuntimeTypeAdapterFactory.of(Shape.class, "type");
 * }</pre>
 * Next register all of your subtypes. Every subtype must be explicitly
 * registered. This protects your application from injection attacks. If you
 * don't supply an explicit type label, the type's simple name will be used.
 * <pre>   {@code
 *   shapeAdapterFactory.registerSubtype(Rectangle.class, "Rectangle");
 *   shapeAdapterFactory.registerSubtype(Circle.class, "Circle");
 *   shapeAdapterFactory.registerSubtype(Diamond.class, "Diamond");
 * }</pre>
 * Finally, register the type adapter factory in your application's GSON builder:
 * <pre>   {@code
 *   Gson gson = new GsonBuilder()
 *       .registerTypeAdapterFactory(shapeAdapterFactory)
 *       .create();
 * }</pre>
 * Like {@code GsonBuilder}, this API supports chaining: <pre>   {@code
 *   RuntimeTypeAdapterFactory<Shape> shapeAdapterFactory = RuntimeTypeAdapterFactory.of(Shape.class)
 *       .registerSubtype(Rectangle.class)
 *       .registerSubtype(Circle.class)
 *       .registerSubtype(Diamond.class);
 * }</pre>
 */
public final class RuntimeTypeAdapterFactory<T> implements TypeAdapterFactory {
  private final Class<?> baseType;
  private final String typeFieldName;
  private final Map<String, Class<?>> labelToSubtype = new LinkedHashMap<String, Class<?>>();
  private final Map<Class<?>, String> subtypeToLabel = new LinkedHashMap<Class<?>, String>();
  private final boolean maintainType;

  private RuntimeTypeAdapterFactory(Class<?> baseType, String typeFieldName, boolean maintainType) {
    if (typeFieldName == null || baseType == null) {
      throw new NullPointerException();
    }
    this.baseType = baseType;
    this.typeFieldName = typeFieldName;
    this.maintainType = maintainType;
  }

  /**
   * Creates a new runtime type adapter using for {@code baseType} using {@code
   * typeFieldName} as the type field name. Type field names are case sensitive.
   * {@code maintainType} flag decide if the type will be stored in pojo or not.
   */
  public static <T> RuntimeTypeAdapterFactory<T> of(Class<T> baseType, String typeFieldName, boolean maintainType) {
    return new RuntimeTypeAdapterFactory<T>(baseType, typeFieldName, maintainType);
  }

  /**
   * Creates a new runtime type adapter using for {@code baseType} using {@code
   * typeFieldName} as the type field name. Type field names are case sensitive.
   */
  public static <T> RuntimeTypeAdapterFactory<T> of(Class<T> baseType, String typeFieldName) {
    return new RuntimeTypeAdapterFactory<T>(baseType, typeFieldName, false);
  }

  /**
   * Creates a new runtime type adapter for {@code baseType} using {@code "type"} as
   * the type field name.
   */
  public static <T> RuntimeTypeAdapterFactory<T> of(Class<T> baseType) {
    return new RuntimeTypeAdapterFactory<T>(baseType, "type", false);
  }

  /**
   * Registers {@code type} identified by {@code label}. Labels are case
   * sensitive.
   *
   * @throws IllegalArgumentException if either {@code type} or {@code label}
   *     have already been registered on this type adapter.
   */
  public RuntimeTypeAdapterFactory<T> registerSubtype(Class<? extends T> type, String label) {
    if (type == null || label == null) {
      throw new NullPointerException();
    }
    if (subtypeToLabel.containsKey(type) || labelToSubtype.containsKey(label)) {
      throw new IllegalArgumentException("types and labels must be unique");
    }
    labelToSubtype.put(label, type);
    subtypeToLabel.put(type, label);
    return this;
  }

  /**
   * Registers {@code type} identified by its {@link Class#getSimpleName simple
   * name}. Labels are case sensitive.
   *
   * @throws IllegalArgumentException if either {@code type} or its simple name
   *     have already been registered on this type adapter.
   */
  public RuntimeTypeAdapterFactory<T> registerSubtype(Class<? extends T> type) {
    return registerSubtype(type, type.getSimpleName());
  }

  public <R> TypeAdapter<R> create(Gson gson, TypeToken<R> type) {
    if (type.getRawType() != baseType) {
      return null;
    }

    final Map<String, TypeAdapter<?>> labelToDelegate
        = new LinkedHashMap<String, TypeAdapter<?>>();
    final Map<Class<?>, TypeAdapter<?>> subtypeToDelegate
        = new LinkedHashMap<Class<?>, TypeAdapter<?>>();
    for (Map.Entry<String, Class<?>> entry : labelToSubtype.entrySet()) {
      TypeAdapter<?> delegate = gson.getDelegateAdapter(this, TypeToken.get(entry.getValue()));
      labelToDelegate.put(entry.getKey(), delegate);
      subtypeToDelegate.put(entry.getValue(), delegate);
    }

    return new TypeAdapter<R>() {
      @Override public R read(JsonReader in) throws IOException {
        JsonElement jsonElement = Streams.parse(in);
        JsonElement labelJsonElement;
        if (maintainType) {
          labelJsonElement = jsonElement.getAsJsonObject().get(typeFieldName);
        } else {
          labelJsonElement = jsonElement.getAsJsonObject().remove(typeFieldName);
        }

        if (labelJsonElement == null) {
          throw new JsonParseException("cannot deserialize " + baseType
              + " because it does not define a field named " + typeFieldName);
        }
        String label = labelJsonElement.getAsString();
        @SuppressWarnings("unchecked") // registration requires that subtype extends T
            TypeAdapter<R> delegate = (TypeAdapter<R>) labelToDelegate.get(label);
        if (delegate == null) {
          throw new JsonParseException("cannot deserialize " + baseType + " subtype named "
              + label + "; did you forget to register a subtype?");
        }
        return delegate.fromJsonTree(jsonElement);
      }

      @Override public void write(JsonWriter out, R value) throws IOException {
        Class<?> srcType = value.getClass();
        String label = subtypeToLabel.get(srcType);
        @SuppressWarnings("unchecked") // registration requires that subtype extends T
            TypeAdapter<R> delegate = (TypeAdapter<R>) subtypeToDelegate.get(srcType);
        if (delegate == null) {
          throw new JsonParseException("cannot serialize " + srcType.getName()
              + "; did you forget to register a subtype?");
        }
        JsonObject jsonObject = delegate.toJsonTree(value).getAsJsonObject();

        if (maintainType) {
          Streams.write(jsonObject, out);
          return;
        }

        JsonObject clone = new JsonObject();

        if (jsonObject.has(typeFieldName)) {
          throw new JsonParseException("cannot serialize " + srcType.getName()
              + " because it already defines a field named " + typeFieldName);
        }
        clone.add(typeFieldName, new JsonPrimitive(label));

        for (Map.Entry<String, JsonElement> e : jsonObject.entrySet()) {
          clone.add(e.getKey(), e.getValue());
        }
        Streams.write(clone, out);
      }
    }.nullSafe();
  }
}
