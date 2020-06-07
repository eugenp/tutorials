package com.baeldung.inheritence

import scala.reflect.ClassTag

/**
 * Created by yadu on 07/06/20
 */


abstract class BaseRepositoryWithTag[E: ClassTag](tableName: String) {
  //do some operation with reflection using ClassTag
  def getAll(): List[E] = {
    //Add logic to query from the required table
    ???
  }
}