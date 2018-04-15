package com.baeldung.kotlin.jpa

import org.hibernate.cfg.Configuration
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase
import org.hibernate.testing.transaction.TransactionUtil.doInHibernate
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException
import java.util.*


class HibernateKotlinIntegrationTest : BaseCoreFunctionalTestCase() {

    private val properties: Properties
        @Throws(IOException::class)
        get() {
            val properties = Properties()
            properties.load(javaClass.classLoader.getResourceAsStream("hibernate.properties"))
            return properties
        }

    override fun getAnnotatedClasses(): Array<Class<*>> {
        return arrayOf(Person::class.java, PhoneNumber::class.java)
    }

    override fun configure(configuration: Configuration) {
        super.configure(configuration)
        configuration.properties = properties
    }

    @Test
    fun givenPersonWithFullData_whenSaved_thenFound() {
        doInHibernate(({ this.sessionFactory() }), { session ->
            val personToSave = Person(0, "John", "jhon@test.com", Arrays.asList(PhoneNumber(0, "202-555-0171"), PhoneNumber(0, "202-555-0102")))
            session.persist(personToSave)
            val personFound = session.find(Person::class.java, personToSave.id)
            session.refresh(personFound)

            assertTrue(personToSave == personFound)
        })
    }

    @Test
    fun givenPerson_whenSaved_thenFound() {
        doInHibernate(({ this.sessionFactory() }), { session ->
            val personToSave = Person(0, "John")
            session.persist(personToSave)
            val personFound = session.find(Person::class.java, personToSave.id)
            session.refresh(personFound)

            assertTrue(personToSave == personFound)
        })
    }

    @Test
    fun givenPersonWithNullFields_whenSaved_thenFound() {
        doInHibernate(({ this.sessionFactory() }), { session ->
            val personToSave = Person(0, "John", null, null)
            session.persist(personToSave)
            val personFound = session.find(Person::class.java, personToSave.id)
            session.refresh(personFound)

            assertTrue(personToSave == personFound)
        })
    }
}