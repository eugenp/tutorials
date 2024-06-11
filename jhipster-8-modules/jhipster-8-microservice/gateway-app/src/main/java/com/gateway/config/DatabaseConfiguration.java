package com.gateway.config;

import io.r2dbc.spi.ConnectionFactory;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.convert.MappingR2dbcConverter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.DialectResolver;
import org.springframework.data.r2dbc.dialect.R2dbcDialect;
import org.springframework.data.r2dbc.query.UpdateMapper;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.relational.core.dialect.RenderContextFactory;
import org.springframework.data.relational.core.sql.render.SqlRenderer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tech.jhipster.config.JHipsterConstants;
import tech.jhipster.config.h2.H2ConfigurationHelper;

@Configuration
@EnableR2dbcRepositories({ "com.gateway.repository" })
@EnableTransactionManagement
public class DatabaseConfiguration {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    private final Environment env;

    public DatabaseConfiguration(Environment env) {
        this.env = env;
    }

    /**
     * Open the TCP port for the H2 database, so it is available remotely.
     *
     * @return the H2 database TCP server.
     * @throws SQLException if the server failed to start.
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    @Profile(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
    public Object h2TCPServer() throws SQLException {
        String port = getValidPortForH2();
        log.debug("H2 database is available on port {}", port);
        return H2ConfigurationHelper.createServer(port);
    }

    private String getValidPortForH2() {
        int port = Integer.parseInt(env.getProperty("server.port"));
        if (port < 10000) {
            port = 10000 + port;
        } else {
            if (port < 63536) {
                port = port + 2000;
            } else {
                port = port - 2000;
            }
        }
        return String.valueOf(port);
    }

    // LocalDateTime seems to be the only type that is supported across all drivers atm
    // See https://github.com/r2dbc/r2dbc-h2/pull/139 https://github.com/mirromutth/r2dbc-mysql/issues/105
    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions(R2dbcDialect dialect) {
        List<Object> converters = new ArrayList<>();
        converters.add(InstantWriteConverter.INSTANCE);
        converters.add(InstantReadConverter.INSTANCE);
        converters.add(BitSetReadConverter.INSTANCE);
        converters.add(DurationWriteConverter.INSTANCE);
        converters.add(DurationReadConverter.INSTANCE);
        converters.add(ZonedDateTimeReadConverter.INSTANCE);
        converters.add(ZonedDateTimeWriteConverter.INSTANCE);
        return R2dbcCustomConversions.of(dialect, converters);
    }

    @Bean
    public R2dbcDialect dialect(ConnectionFactory connectionFactory) {
        return DialectResolver.getDialect(connectionFactory);
    }

    @Bean
    public UpdateMapper updateMapper(R2dbcDialect dialect, MappingR2dbcConverter mappingR2dbcConverter) {
        return new UpdateMapper(dialect, mappingR2dbcConverter);
    }

    @Bean
    public SqlRenderer sqlRenderer(R2dbcDialect dialect) {
        RenderContextFactory factory = new RenderContextFactory(dialect);
        return SqlRenderer.create(factory.createRenderContext());
    }

    @WritingConverter
    public enum InstantWriteConverter implements Converter<Instant, LocalDateTime> {
        INSTANCE;

        public LocalDateTime convert(Instant source) {
            return LocalDateTime.ofInstant(source, ZoneOffset.UTC);
        }
    }

    @ReadingConverter
    public enum InstantReadConverter implements Converter<LocalDateTime, Instant> {
        INSTANCE;

        @Override
        public Instant convert(LocalDateTime localDateTime) {
            return localDateTime.toInstant(ZoneOffset.UTC);
        }
    }

    @ReadingConverter
    public enum BitSetReadConverter implements Converter<BitSet, Boolean> {
        INSTANCE;

        @Override
        public Boolean convert(BitSet bitSet) {
            return bitSet.get(0);
        }
    }

    @ReadingConverter
    public enum ZonedDateTimeReadConverter implements Converter<LocalDateTime, ZonedDateTime> {
        INSTANCE;

        @Override
        public ZonedDateTime convert(LocalDateTime localDateTime) {
            // Be aware - we are using the UTC timezone
            return ZonedDateTime.of(localDateTime, ZoneOffset.UTC);
        }
    }

    @WritingConverter
    public enum ZonedDateTimeWriteConverter implements Converter<ZonedDateTime, LocalDateTime> {
        INSTANCE;

        @Override
        public LocalDateTime convert(ZonedDateTime zonedDateTime) {
            return zonedDateTime.toLocalDateTime();
        }
    }

    @WritingConverter
    public enum DurationWriteConverter implements Converter<Duration, Long> {
        INSTANCE;

        @Override
        public Long convert(Duration source) {
            return source != null ? source.toMillis() : null;
        }
    }

    @ReadingConverter
    public enum DurationReadConverter implements Converter<Long, Duration> {
        INSTANCE;

        @Override
        public Duration convert(Long source) {
            return source != null ? Duration.ofMillis(source) : null;
        }
    }
}
