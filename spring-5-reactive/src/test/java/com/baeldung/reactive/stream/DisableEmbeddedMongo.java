/**
 *
 */
package com.baeldung.reactive.stream;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.test.context.TestPropertySource;

/**
 * Disables MongoDb embedded database.
 * @author goobar
 *
 */
@TestPropertySource(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration")
@Retention(RetentionPolicy.RUNTIME)
public @interface DisableEmbeddedMongo {

}
