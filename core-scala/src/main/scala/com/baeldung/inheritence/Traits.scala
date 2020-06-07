package com.baeldung.inheritence

/**
 * Created by yadu on 07/06/20
 */


trait BaseEntity

trait MasterData

trait TransactionData

trait Cacheable

case class Employee(id: Long) extends BaseEntity with MasterData with Cacheable