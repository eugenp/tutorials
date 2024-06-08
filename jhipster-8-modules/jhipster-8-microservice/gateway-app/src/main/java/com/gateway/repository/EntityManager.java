package com.gateway.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.core.StatementMapper;
import org.springframework.data.r2dbc.query.UpdateMapper;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.OrderByField;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoin;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoinCondition;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectOrdered;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.core.sql.render.SqlRenderer;
import org.springframework.r2dbc.core.Parameter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Helper class to create SQL selects based on the entity, paging parameters and criteria.
 *
 */
@Component
public class EntityManager {

    public static final String ENTITY_ALIAS = "e";
    public static final String ALIAS_PREFIX = "e_";

    public static class LinkTable {

        final String tableName;
        final String idColumn;
        final String referenceColumn;

        public LinkTable(String tableName, String idColumn, String referenceColumn) {
            Assert.notNull(tableName, "tableName is null");
            Assert.notNull(idColumn, "idColumn is null");
            Assert.notNull(referenceColumn, "referenceColumn is null");
            this.tableName = tableName;
            this.idColumn = idColumn;
            this.referenceColumn = referenceColumn;
        }
    }

    private final SqlRenderer sqlRenderer;
    private final UpdateMapper updateMapper;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final StatementMapper statementMapper;

    public EntityManager(SqlRenderer sqlRenderer, UpdateMapper updateMapper, R2dbcEntityTemplate r2dbcEntityTemplate) {
        this.sqlRenderer = sqlRenderer;
        this.updateMapper = updateMapper;
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
        this.statementMapper = r2dbcEntityTemplate.getDataAccessStrategy().getStatementMapper();
    }

    /**
     * Creates an SQL select statement from the given fragment and pagination parameters.
     * @param selectFrom a representation of a select statement.
     * @param entityType the entity type which holds the table name.
     * @param pageable page parameter, or null, if everything needs to be returned.
     * @param where condition or null. The condition to apply as where clause.
     * @return sql select statement
     */
    public String createSelect(SelectFromAndJoin selectFrom, Class<?> entityType, Pageable pageable, Condition where) {
        if (pageable != null) {
            if (where != null) {
                return createSelectImpl(
                    selectFrom.limitOffset(pageable.getPageSize(), pageable.getOffset()).where(where),
                    entityType,
                    pageable.getSort()
                );
            } else {
                return createSelectImpl(
                    selectFrom.limitOffset(pageable.getPageSize(), pageable.getOffset()),
                    entityType,
                    pageable.getSort()
                );
            }
        } else {
            if (where != null) {
                return createSelectImpl(selectFrom.where(where), entityType, null);
            } else {
                return createSelectImpl(selectFrom, entityType, null);
            }
        }
    }

    /**
     * Creates an SQL select statement from the given fragment and pagination parameters.
     * @param selectFrom a representation of a select statement.
     * @param entityType the entity type which holds the table name.
     * @param pageable page parameter, or null, if everything needs to be returned
     * @param where condition or null. The condition to apply as where clause.
     * @return sql select statement
     */
    public String createSelect(SelectFromAndJoinCondition selectFrom, Class<?> entityType, Pageable pageable, Condition where) {
        if (pageable != null) {
            if (where != null) {
                return createSelectImpl(
                    selectFrom.limitOffset(pageable.getPageSize(), pageable.getOffset()).where(where),
                    entityType,
                    pageable.getSort()
                );
            } else {
                return createSelectImpl(
                    selectFrom.limitOffset(pageable.getPageSize(), pageable.getOffset()),
                    entityType,
                    pageable.getSort()
                );
            }
        } else {
            if (where != null) {
                return createSelectImpl(selectFrom.where(where), entityType, null);
            } else {
                return createSelectImpl(selectFrom, entityType, null);
            }
        }
    }

    /**
     * Generate an actual SQL from the given {@link Select}.
     * @param select a representation of a select statement.
     * @return the generated SQL select.
     */
    public String createSelect(Select select) {
        return sqlRenderer.render(select);
    }

    /**
     * Delete all the entity with the given type, and return the number of deletions.
     * @param entityType the entity type which holds the table name.
     * @return the number of deleted entity
     */
    public Mono<Long> deleteAll(Class<?> entityType) {
        return r2dbcEntityTemplate.delete(entityType).all();
    }

    /**
     * Delete all the rows from the given table, and return the number of deletions.
     * @param tableName the name of the table to delete.
     * @return the number of deleted rows.
     */
    public Mono<Long> deleteAll(String tableName) {
        StatementMapper.DeleteSpec delete = statementMapper.createDelete(tableName);
        return r2dbcEntityTemplate.getDatabaseClient().sql(statementMapper.getMappedObject(delete)).fetch().rowsUpdated();
    }

    /**
     * Inserts the given entity into the database - and sets the id, if it's an autoincrement field.
     * @param <S> the type of the persisted entity.
     * @param entity the entity to be inserted into the database.
     * @return the persisted entity.
     */
    public <S> Mono<S> insert(S entity) {
        return r2dbcEntityTemplate.insert(entity);
    }

    /**
     * Updates the table, which links the entity with the referred entities.
     * @param table describes the link table, it contains a table name, the column name for the id, and for the referred entity id.
     * @param entityId the id of the entity, for which the links are created.
     * @param referencedIds the id of the referred entities.
     * @return the number of inserted rows.
     */
    public Mono<Long> updateLinkTable(LinkTable table, Object entityId, Stream<?> referencedIds) {
        return deleteFromLinkTable(table, entityId).then(
            Flux.fromStream(referencedIds)
                .flatMap((Object referenceId) -> {
                    StatementMapper.InsertSpec insert = r2dbcEntityTemplate
                        .getDataAccessStrategy()
                        .getStatementMapper()
                        .createInsert(table.tableName)
                        .withColumn(table.idColumn, Parameter.from(entityId))
                        .withColumn(table.referenceColumn, Parameter.from(referenceId));

                    return r2dbcEntityTemplate.getDatabaseClient().sql(statementMapper.getMappedObject(insert)).fetch().rowsUpdated();
                })
                .collectList()
                .map((List<Long> updates) -> updates.stream().reduce(Long::sum).orElse(0l))
        );
    }

    public Mono<Void> deleteFromLinkTable(LinkTable table, Object entityId) {
        Assert.notNull(entityId, "entityId is null");
        StatementMapper.DeleteSpec deleteSpec = r2dbcEntityTemplate
            .getDataAccessStrategy()
            .getStatementMapper()
            .createDelete(table.tableName)
            .withCriteria(Criteria.from(Criteria.where(table.idColumn).is(entityId)));
        return r2dbcEntityTemplate.getDatabaseClient().sql(statementMapper.getMappedObject(deleteSpec)).then();
    }

    private String createSelectImpl(SelectOrdered selectFrom, Class<?> entityType, Sort sortParameter) {
        if (sortParameter != null && sortParameter.isSorted()) {
            RelationalPersistentEntity<?> entity = getPersistentEntity(entityType);
            if (entity != null) {
                Sort sort = updateMapper.getMappedObject(sortParameter, entity);
                selectFrom = selectFrom.orderBy(
                    createOrderByFields(Table.create(entity.getTableName()).as(EntityManager.ENTITY_ALIAS), sort)
                );
            }
        }
        return createSelect(selectFrom.build());
    }

    private RelationalPersistentEntity<?> getPersistentEntity(Class<?> entityType) {
        return r2dbcEntityTemplate.getConverter().getMappingContext().getPersistentEntity(entityType);
    }

    private static Collection<? extends OrderByField> createOrderByFields(Table table, Sort sortToUse) {
        List<OrderByField> fields = new ArrayList<>();

        for (Sort.Order order : sortToUse) {
            String propertyName = order.getProperty();
            OrderByField orderByField = !propertyName.contains(".")
                ? OrderByField.from(table.column(propertyName).as(EntityManager.ALIAS_PREFIX + propertyName))
                : createOrderByField(propertyName);

            fields.add(order.isAscending() ? orderByField.asc() : orderByField.desc());
        }

        return fields;
    }

    /**
     * Creates an OrderByField instance for sorting a query by the specified property.
     *
     * @param propertyName The full property name in the format "tableName.columnName".
     * @return An OrderByField instance for sorting by the specified property.
     */
    private static OrderByField createOrderByField(String propertyName) {
        // Split the propertyName into table name and column name
        String[] parts = propertyName.split("\\.");
        String tableName = parts[0];
        String columnName = parts[1];

        // Create and return an OrderByField instance
        return OrderByField.from(
            // Create a column with the given name and alias it with the table name and column name
            Column.aliased(
                columnName,
                // Create a table alias with the same name as the table
                Table.aliased(camelCaseToSnakeCase(tableName), tableName),
                // Use a composite alias of "tableName_columnName"
                String.format("%s_%s", tableName, columnName)
            )
        );
    }

    /**
     * Converts a camel case string to snake case.
     *
     * @param input The camel case string to be converted to snake case.
     * @return The input string converted to snake case.
     */
    public static String camelCaseToSnakeCase(String input) {
        // Regular Expression
        String regex = "([a-z])([A-Z]+)";

        // Replacement string
        String replacement = "$1_$2";

        // Replace the given regex
        // with replacement string
        // and convert it to lower case.
        return input.replaceAll(regex, replacement).toLowerCase();
    }
}
