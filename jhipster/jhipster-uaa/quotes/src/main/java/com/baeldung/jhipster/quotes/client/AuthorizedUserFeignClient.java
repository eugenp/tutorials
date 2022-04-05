package com.baeldung.jhipster.quotes.client;

import java.lang.annotation.*;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.core.annotation.AliasFor;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@FeignClient
public @interface AuthorizedUserFeignClient {

    @AliasFor(annotation = FeignClient.class, attribute = "name")
    String name() default "";

    /**
     * A custom <code>@Configuration</code> for the feign client.
     *
     * Can contain override <code>@Bean</code> definition for the pieces that make up the client, for instance {@link
     * feign.codec.Decoder}, {@link feign.codec.Encoder}, {@link feign.Contract}.
     *
     * @see FeignClientsConfiguration for the defaults
     */
    @AliasFor(annotation = FeignClient.class, attribute = "configuration")
    Class<?>[] configuration() default OAuth2UserClientFeignConfiguration.class;

    /**
     * An absolute URL or resolvable hostname (the protocol is optional).
     */
    String url() default "";

    /**
     * Whether 404s should be decoded instead of throwing FeignExceptions.
     */
    boolean decode404() default false;

    /**
     * Fallback class for the specified Feign client interface. The fallback class must implement the interface
     * annotated by this annotation and be a valid Spring bean.
     */
    Class<?> fallback() default void.class;

    /**
     * Path prefix to be used by all method-level mappings. Can be used with or without <code>@RibbonClient</code>.
     */
    String path() default "";
}
