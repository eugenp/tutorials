package com.baeldung.sequencenaming;

import static org.hibernate.id.enhanced.TableGenerator.DEF_TABLE;

import java.util.Map;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.relational.QualifiedName;
import org.hibernate.boot.model.relational.QualifiedNameParser;
import org.hibernate.boot.model.relational.QualifiedSequenceName;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.id.enhanced.ImplicitDatabaseObjectNamingStrategy;
import org.hibernate.service.ServiceRegistry;

public class CustomSequenceNamingStrategy implements ImplicitDatabaseObjectNamingStrategy {

    @Override
    public QualifiedName determineSequenceName(Identifier catalogName, Identifier schemaName, Map<?, ?> map, ServiceRegistry serviceRegistry) {
        final JdbcEnvironment jdbcEnvironment = serviceRegistry.getService(JdbcEnvironment.class);
        final String seqName = ((String) map.get("jpa_entity_name")).concat("_custom_seq");
        return new QualifiedSequenceName(
            catalogName,
            schemaName,
            jdbcEnvironment.getIdentifierHelper().toIdentifier(seqName));
    }

    @Override
    public QualifiedName determineTableName(Identifier catalogName, Identifier schemaName, Map<?, ?> map, ServiceRegistry serviceRegistry) {
        final JdbcEnvironment jdbcEnvironment = serviceRegistry.getService(JdbcEnvironment.class);

        return new QualifiedNameParser.NameParts(
            catalogName,
            schemaName,
            jdbcEnvironment.getIdentifierHelper().toIdentifier(DEF_TABLE));
    }
}
