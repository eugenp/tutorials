/**
 * 
 */
package com.baeldung.boot.jdbi.dao;

import java.util.List;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import com.baeldung.boot.jdbi.domain.CarMaker;

/**
 * @author Philippe
 *
 */
@UseClasspathSqlLocator
public interface CarMakerDao {

    @SqlUpdate
    @GetGeneratedKeys
    Long insert(@BindBean CarMaker carMaker);
    
    @SqlBatch("insert")
    @GetGeneratedKeys
    List<Long> bulkInsert(@BindBean List<CarMaker> carMakers);
    
    @SqlQuery
    CarMaker findById(@Bind("id") Long id);
}
