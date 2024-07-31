package com.baeldung.restexpress;

import com.baeldung.restexpress.serialization.SerializationProvider;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.strategicgains.repoexpress.adapter.Identifiers;
import com.strategicgains.repoexpress.exception.DuplicateItemException;
import com.strategicgains.repoexpress.exception.InvalidObjectIdException;
import com.strategicgains.repoexpress.exception.ItemNotFoundException;
import com.strategicgains.restexpress.plugin.cache.CacheControlPlugin;
import com.strategicgains.restexpress.plugin.cors.CorsHeaderPlugin;
import com.strategicgains.restexpress.plugin.metrics.MetricsConfig;
import com.strategicgains.restexpress.plugin.metrics.MetricsPlugin;
import com.strategicgains.restexpress.plugin.swagger.SwaggerPlugin;
import com.strategicgains.syntaxe.ValidationException;
import org.restexpress.Flags;
import org.restexpress.RestExpress;
import org.restexpress.exception.BadRequestException;
import org.restexpress.exception.ConflictException;
import org.restexpress.exception.NotFoundException;
import org.restexpress.pipeline.SimpleConsoleLogMessageObserver;
import org.restexpress.plugin.hyperexpress.HyperExpressPlugin;
import org.restexpress.plugin.hyperexpress.Linkable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static org.restexpress.Flags.Auth.PUBLIC_ROUTE;

public class Server {
    private static final String SERVICE_NAME = "TODO: Enter service name";
    private static final Logger LOG = LoggerFactory.getLogger(SERVICE_NAME);

    private RestExpress server;
    private Configuration config;
    private boolean isStarted = false;

    public Server(Configuration config) {
        this.config = config;
        RestExpress.setDefaultSerializationProvider(new SerializationProvider());
        Identifiers.UUID.useShortUUID(true);

        this.server = new RestExpress()
          .setName(SERVICE_NAME)
          .setBaseUrl(config.getBaseUrl())
          .setExecutorThreadCount(config.getExecutorThreadPoolSize())
          .addMessageObserver(new SimpleConsoleLogMessageObserver());

        Routes.define(config, server);
        Relationships.define(server);
        configurePlugins(config, server);
        mapExceptions(server);
    }

    public Server start() {
        if (!isStarted) {
            server.bind(config.getPort());
            isStarted = true;
        }

        return this;
    }

    public void awaitShutdown() {
        if (isStarted) server.awaitShutdown();
    }

    public void shutdown() {
        if (isStarted) server.shutdown();
    }

    private void configurePlugins(Configuration config, RestExpress server) {
        configureMetrics(config, server);

        new SwaggerPlugin()
          .flag(Flags.Auth.PUBLIC_ROUTE)
          .register(server);

        new CacheControlPlugin()
          .register(server);

        new HyperExpressPlugin(Linkable.class)
          .register(server);

        new CorsHeaderPlugin("*")
          .flag(PUBLIC_ROUTE)
          .allowHeaders(CONTENT_TYPE, ACCEPT, AUTHORIZATION, REFERER, LOCATION)
          .exposeHeaders(LOCATION)
          .register(server);
    }

    private void configureMetrics(Configuration config, RestExpress server) {
        MetricsConfig mc = config.getMetricsConfig();

        if (mc.isEnabled()) {
            MetricRegistry registry = new MetricRegistry();
            new MetricsPlugin(registry)
              .register(server);

            if (mc.isGraphiteEnabled()) {
                final Graphite graphite = new Graphite(new InetSocketAddress(mc.getGraphiteHost(), mc.getGraphitePort()));
                final GraphiteReporter reporter = GraphiteReporter.forRegistry(registry)
                  .prefixedWith(mc.getPrefix())
                  .convertRatesTo(TimeUnit.SECONDS)
                  .convertDurationsTo(TimeUnit.MILLISECONDS)
                  .filter(MetricFilter.ALL)
                  .build(graphite);
                reporter.start(mc.getPublishSeconds(), TimeUnit.SECONDS);
            } else {
                LOG.warn("*** Graphite Metrics Publishing is Disabled ***");
            }
        } else {
            LOG.warn("*** Metrics Generation is Disabled ***");
        }
    }

    private void mapExceptions(RestExpress server) {
        server
          .mapException(ItemNotFoundException.class, NotFoundException.class)
          .mapException(DuplicateItemException.class, ConflictException.class)
          .mapException(ValidationException.class, BadRequestException.class)
          .mapException(InvalidObjectIdException.class, BadRequestException.class);
    }
}
