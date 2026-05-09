package com.baeldung.paimon;

import org.apache.paimon.catalog.Catalog;
import org.apache.paimon.catalog.CatalogContext;
import org.apache.paimon.catalog.CatalogFactory;
import org.apache.paimon.catalog.Identifier;
import org.apache.paimon.fs.Path;
import org.apache.paimon.schema.Schema;
import org.apache.paimon.types.DataTypes;

public class PaimonDatabaseManager {
    public static Catalog createCatalog(String warehousePath) {
        CatalogContext context = CatalogContext.create(new Path(warehousePath));
        return CatalogFactory.createCatalog(context);
    }

    public static Identifier createTable(Catalog catalog) throws Exception {

        Schema schema = Schema.newBuilder()
            .column("device_id", DataTypes.STRING())
            .column("metrics_name", DataTypes.STRING())
            .column("metrics_value", DataTypes.DOUBLE())
            .column("source", DataTypes.STRING())
            .column("create_time", DataTypes.TIMESTAMP(3))
            .primaryKey("device_id", "metrics_name", "create_time")  
            .build();

        Identifier tableId = Identifier.create("metric_db", "metrics");
        catalog.createDatabase("metric_db", false);


  
        catalog.createTable(tableId, schema, false);
        return tableId;
    }
}
