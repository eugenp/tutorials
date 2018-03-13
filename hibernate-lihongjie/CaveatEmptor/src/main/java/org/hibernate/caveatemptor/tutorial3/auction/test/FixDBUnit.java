package org.hibernate.caveatemptor.tutorial3.auction.test;

import org.dbunit.dataset.datatype.*;

import java.sql.Types;

/**
 * TODO: Direct SQL is lots of fun. This fixes DBUnit with the latest HSQL DB.
 *
 * http://www.carbonfive.com/community/archives/2005/07/dbunit_hsql_and.html
 */
public class FixDBUnit extends DefaultDataTypeFactory {

   public DataType createDataType(int sqlType, String sqlTypeName)
     throws DataTypeException {
      if (sqlType == Types.BOOLEAN) {
         return DataType.BOOLEAN;
       }
      return super.createDataType(sqlType, sqlTypeName);
    }
}
