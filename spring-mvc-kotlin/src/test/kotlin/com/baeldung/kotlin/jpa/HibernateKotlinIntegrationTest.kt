package com.baeldung.kotlin.jpa

import com.baeldung.jpa.Person
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
        return arrayOf(Person::class.java)
    }

    override fun configure(configuration: Configuration) {
        super.configure(configuration)
        configuration.properties = properties
    }

    @Test
    fun givenPerson_whenSaved_thenFound() {
        val personToSave = Person(0, "John")
        doInHibernate(({ this.sessionFactory() }), { session ->
            session.persist(personToSave)
            assertTrue(session.contains(personToSave))
        })
        doInHibernate(({ this.sessionFactory() }), { session ->
            val personFound = session.find(Person::class.java, personToSave.id)
            assertTrue(personToSave == personFound)
        })
    }
}