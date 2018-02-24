package com.baeldung.kotlin.kodein

import com.github.salomonbrys.kodein.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KodeinUnitTest {

    class InMemoryDao : Dao

    @Test
    fun whenSingletonBinding_thenSingleInstanceIsCreated() {
        var created = false
        val kodein = Kodein {
            bind<Dao>() with singleton {
                created = true
                MongoDao()
            }
        }

        assertThat(created).isFalse()

        val dao1: Dao = kodein.instance()

        assertThat(created).isTrue()

        val dao2: Dao = kodein.instance()

        assertThat(dao1).isSameAs(dao2)
    }

    @Test
    fun whenFactoryBinding_thenNewInstanceIsCreated() {
        val kodein = Kodein {
            bind<Dao>() with singleton { MongoDao() }
            bind<Service>() with factory { tag: String -> Service(instance(), tag) }
        }
        val service1: Service = kodein.with("myTag").instance()
        val service2: Service = kodein.with("myTag").instance()

        assertThat(service1).isNotSameAs(service2)
    }

    @Test
    fun whenProviderBinding_thenNewInstanceIsCreated() {
        val kodein = Kodein {
            bind<Dao>() with provider { MongoDao() }
        }
        val dao1: Dao = kodein.instance()
        val dao2: Dao = kodein.instance()

        assertThat(dao1).isNotSameAs(dao2)
    }

    @Test
    fun whenTaggedBinding_thenMultipleInstancesOfSameTypeCanBeRegistered() {
        val kodein = Kodein {
            bind<Dao>("dao1") with singleton { MongoDao() }
            bind<Dao>("dao2") with singleton { MongoDao() }
        }
        val dao1: Dao = kodein.instance("dao1")
        val dao2: Dao = kodein.instance("dao2")

        assertThat(dao1).isNotSameAs(dao2)
    }

    @Test
    fun whenEagerSingletonBinding_thenCreationIsEager() {
        var created = false
        val kodein = Kodein {
            bind<Dao>() with eagerSingleton {
                created = true
                MongoDao()
            }
        }

        assertThat(created).isTrue()
        val dao1: Dao = kodein.instance()
        val dao2: Dao = kodein.instance()

        assertThat(dao1).isSameAs(dao2)
    }

    @Test
    fun whenMultitonBinding_thenInstancesAreReused() {
        val kodein = Kodein {
            bind<Dao>() with singleton { MongoDao() }
            bind<Service>() with multiton { tag: String -> Service(instance(), tag) }
        }
        val service1: Service = kodein.with("myTag").instance()
        val service2: Service = kodein.with("myTag").instance()

        assertThat(service1).isSameAs(service2)
    }

    @Test
    fun whenInstanceBinding_thenItIsReused() {
        val dao = MongoDao()
        val kodein = Kodein {
            bind<Dao>() with instance(dao)
        }
        val fromContainer: Dao = kodein.instance()

        assertThat(dao).isSameAs(fromContainer)
    }

    @Test
    fun whenConstantBinding_thenItIsAvailable() {
        val kodein = Kodein {
            constant("magic") with 42
        }
        val fromContainer: Int = kodein.instance("magic")

        assertThat(fromContainer).isEqualTo(42)
    }

    @Test
    fun whenUsingModules_thenTransitiveDependenciesAreSuccessfullyResolved() {
        val jdbcModule = Kodein.Module {
            bind<Dao>() with singleton { JdbcDao() }
        }
        val kodein = Kodein {
            import(jdbcModule)
            bind<Controller>() with singleton { Controller(instance()) }
            bind<Service>() with singleton { Service(instance(), "myService") }
        }

        val dao: Dao = kodein.instance()
        assertThat(dao).isInstanceOf(JdbcDao::class.java)
    }

    @Test
    fun whenComposition_thenBeansAreReUsed() {
        val persistenceContainer = Kodein {
            bind<Dao>() with singleton { MongoDao() }
        }
        val serviceContainer = Kodein {
            extend(persistenceContainer)
            bind<Service>() with singleton { Service(instance(), "myService") }
        }
        val fromPersistence: Dao = persistenceContainer.instance()
        val fromService: Dao = serviceContainer.instance()

        assertThat(fromPersistence).isSameAs(fromService)
    }

    @Test
    fun whenOverriding_thenRightBeanIsUsed() {
        val commonModule = Kodein.Module {
            bind<Dao>() with singleton { MongoDao() }
            bind<Service>() with singleton { Service(instance(), "myService") }
        }
        val testContainer = Kodein {
            import(commonModule)
            bind<Dao>(overrides = true) with singleton { InMemoryDao() }
        }
        val dao: Dao = testContainer.instance()

        assertThat(dao).isInstanceOf(InMemoryDao::class.java)
    }

    @Test
    fun whenMultiBinding_thenWorks() {
        val kodein = Kodein {
            bind() from setBinding<Dao>()
            bind<Dao>().inSet() with singleton { MongoDao() }
            bind<Dao>().inSet() with singleton { JdbcDao() }
        }
        val daos: Set<Dao> = kodein.instance()

        assertThat(daos.map { it.javaClass as Class<*> }).containsOnly(MongoDao::class.java, JdbcDao::class.java)
    }

    @Test
    fun whenInjector_thenWorks() {
        class Controller2 {
            private val injector = KodeinInjector()
            val service: Service by injector.instance()
            fun injectDependencies(kodein: Kodein) = injector.inject(kodein)
        }

        val kodein = Kodein {
            bind<Dao>() with singleton { MongoDao() }
            bind<Service>() with singleton { Service(instance(), "myService") }
        }
        val controller = Controller2()
        controller.injectDependencies(kodein)

        assertThat(controller.service).isNotNull
    }
}