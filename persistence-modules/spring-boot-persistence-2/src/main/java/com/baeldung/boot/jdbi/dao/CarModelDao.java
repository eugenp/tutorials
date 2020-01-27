package com.baeldung.boot.jdbi.dao;

import java.util.List;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import com.baeldung.boot.jdbi.domain.CarModel;

@UseClasspathSqlLocator
public interface CarModelDao {
    
    @SqlUpdate("insert")
    @GetGeneratedKeys
    Long insert(@BindBean CarModel carModel);

    @SqlBatch("insert")
    @GetGeneratedKeys
    List<Long> bulkInsert(@BindBean List<CarModel> models);

    @SqlQuery
    CarModel findByMakerIdAndSku(@Bind("makerId") Long makerId, @Bind("sku") String sku );
}
