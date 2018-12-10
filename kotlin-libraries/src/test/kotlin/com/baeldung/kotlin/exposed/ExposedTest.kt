package com.baeldung.kotlin.exposed

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Test
import java.sql.DriverManager
import kotlin.test.*

class ExposedTest {

    @Test
    fun whenH2Database_thenConnectionSuccessful() {
        val database = Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        transaction {
            assertEquals(1.4.toBigDecimal(), database.version)
            assertEquals("h2", database.vendor)
        }
    }

    @Test
    fun whenH2DatabaseWithCredentials_thenConnectionSuccessful() {
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "myself", password = "secret")
    }

    @Test
    fun whenH2DatabaseWithManualConnection_thenConnectionSuccessful() {
        var connected = false
        Database.connect({ connected = true; DriverManager.getConnection("jdbc:h2:mem:test;MODE=MySQL") })
        assertEquals(false, connected)
        transaction {
            addLogger(StdOutSqlLogger)
            assertEquals(false, connected)
            SchemaUtils.create(Cities)
            assertEquals(true, connected)
        }
    }

    @Test
    fun whenManualCommit_thenOk() {
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        transaction {
            assertTrue(this is Transaction)
            commit()
            commit()
            commit()
        }
    }

    @Test
    fun whenInsert_thenGeneratedKeys() {
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        transaction {
            SchemaUtils.create(StarWarsFilms)
            val id = StarWarsFilms.insertAndGetId {
                it[name] = "The Last Jedi"
                it[sequelId] = 8
                it[director] = "Rian Johnson"
            }
            assertEquals(1, id.value)
            val insert = StarWarsFilms.insert {
                it[name] = "The Force Awakens"
                it[sequelId] = 7
                it[director] = "J.J. Abrams"
            }
            assertEquals(2, insert[StarWarsFilms.id]?.value)
            val selectAll = StarWarsFilms.selectAll()
            selectAll.forEach {
                assertTrue { it[StarWarsFilms.sequelId] >= 7 }
            }
            StarWarsFilms.slice(StarWarsFilms.name, StarWarsFilms.director).selectAll()
                    .forEach {
                        assertTrue { it[StarWarsFilms.name].startsWith("The") }
                    }
            val select = StarWarsFilms.select { (StarWarsFilms.director like "J.J.%") and (StarWarsFilms.sequelId eq 7) }
            assertEquals(1, select.count())
            StarWarsFilms.update ({ StarWarsFilms.sequelId eq 8 }) {
                it[name] = "Episode VIII – The Last Jedi"
                with(SqlExpressionBuilder) {
                    it.update(StarWarsFilms.sequelId, StarWarsFilms.sequelId + 1)
                }
            }
        }
    }

    @Test
    fun whenForeignKey_thenAutoJoin() {
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(StarWarsFilms, Players)
            StarWarsFilms.insert {
                it[name] = "The Last Jedi"
                it[sequelId] = 8
                it[director] = "Rian Johnson"
            }
            StarWarsFilms.insert {
                it[name] = "The Force Awakens"
                it[sequelId] = 7
                it[director] = "J.J. Abrams"
            }
            Players.insert {
                it[name] = "Mark Hamill"
                it[sequelId] = 7
            }
            Players.insert {
                it[name] = "Mark Hamill"
                it[sequelId] = 8
            }
            val simpleInnerJoin = (StarWarsFilms innerJoin Players).selectAll()
            assertEquals(2, simpleInnerJoin.count())
            simpleInnerJoin.forEach {
                assertNotNull(it[StarWarsFilms.name])
                assertEquals(it[StarWarsFilms.sequelId], it[Players.sequelId])
                assertEquals("Mark Hamill", it[Players.name])
            }
            val innerJoinWithCondition = (StarWarsFilms innerJoin Players)
                    .select { StarWarsFilms.sequelId eq Players.sequelId }
            assertEquals(2, innerJoinWithCondition.count())
            innerJoinWithCondition.forEach {
                assertNotNull(it[StarWarsFilms.name])
                assertEquals(it[StarWarsFilms.sequelId], it[Players.sequelId])
                assertEquals("Mark Hamill", it[Players.name])
            }
            val complexInnerJoin = Join(StarWarsFilms, Players, joinType = JoinType.INNER, onColumn = StarWarsFilms.sequelId, otherColumn = Players.sequelId, additionalConstraint = {
                StarWarsFilms.sequelId eq 8
            }).selectAll()
            assertEquals(1, complexInnerJoin.count())
            complexInnerJoin.forEach {
                assertNotNull(it[StarWarsFilms.name])
                assertEquals(it[StarWarsFilms.sequelId], it[Players.sequelId])
                assertEquals("Mark Hamill", it[Players.name])
            }

        }
    }

    @Test
    fun whenJoinWithAlias_thenFun() {
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(StarWarsFilms, Players)
            StarWarsFilms.insert {
                it[name] = "The Last Jedi"
                it[sequelId] = 8
                it[director] = "Rian Johnson"
            }
            StarWarsFilms.insert {
                it[name] = "The Force Awakens"
                it[sequelId] = 7
                it[director] = "J.J. Abrams"
            }
            val sequel = StarWarsFilms.alias("sequel")
            Join(StarWarsFilms, sequel,
                    additionalConstraint = { sequel[StarWarsFilms.sequelId] eq StarWarsFilms.sequelId + 1 })
                    .selectAll().forEach {
                assertEquals(it[sequel[StarWarsFilms.sequelId]], it[StarWarsFilms.sequelId] + 1)
            }
        }
    }

    @Test
    fun whenEntity_thenDAO() {
        val database = Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        val connection = database.connector.invoke() //Keep a connection open so the DB is not destroyed after the first transaction
        val inserted = transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(StarWarsFilms, Players)
            val theLastJedi = StarWarsFilm.new {
                name = "The Last Jedi"
                sequelId = 8
                director = "Rian Johnson"
            }
            assertFalse(TransactionManager.current().entityCache.inserts.isEmpty())
            assertEquals(1, theLastJedi.id.value) //Reading this causes a flush
            assertTrue(TransactionManager.current().entityCache.inserts.isEmpty())
            theLastJedi
        }
        transaction {
            val theLastJedi = StarWarsFilm.findById(1)
            assertNotNull(theLastJedi)
            assertEquals(inserted.id, theLastJedi?.id)
        }
        connection.close()
    }

    @Test
    fun whenManyToOne_thenNavigation() {
        val database = Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        val connection = database.connector.invoke()
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(StarWarsFilms, Players, Users, UserRatings)
            val theLastJedi = StarWarsFilm.new {
                name = "The Last Jedi"
                sequelId = 8
                director = "Rian Johnson"
            }
            val someUser = User.new {
                name = "Some User"
            }
            val rating = UserRating.new {
                value = 9
                user = someUser
                film = theLastJedi
            }
            assertEquals(theLastJedi, rating.film)
            assertEquals(someUser, rating.user)
            assertEquals(rating, theLastJedi.ratings.first())
        }
        transaction {
            val theLastJedi = StarWarsFilm.find { StarWarsFilms.sequelId eq 8 }.first()
            val ratings = UserRating.find { UserRatings.film eq theLastJedi.id }
            assertEquals(1, ratings.count())
            val rating = ratings.first()
            assertEquals("Some User", rating.user.name)
            assertEquals(rating, theLastJedi.ratings.first())
            UserRating.new {
                value = 8
                user = rating.user
                film = theLastJedi
            }
            assertEquals(2, theLastJedi.ratings.count())
        }
        connection.close()
    }

    @Test
    fun whenManyToMany_thenAssociation() {
        val database = Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        val connection = database.connector.invoke()
        val film = transaction {
            SchemaUtils.create(StarWarsFilms)
            StarWarsFilm.new {
                name = "The Last Jedi"
                sequelId = 8
                director = "Rian Johnson"
            }
        }

        val actor = transaction {
            SchemaUtils.create(Actors)
            Actor.new {
                firstname = "Daisy"
                lastname = "Ridley"
            }
        }

        transaction {
            SchemaUtils.create(StarWarsFilmActors)
            film.actors = SizedCollection(listOf(actor))
        }
        connection.close()
    }

}

object Cities: IntIdTable() {
    val name = varchar("name", 50)
}

object StarWarsFilms_Simple : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val sequelId = integer("sequel_id").uniqueIndex()
    val name = varchar("name", 50)
    val director = varchar("director", 50)
}

object StarWarsFilms : IntIdTable() {
    val sequelId = integer("sequel_id").uniqueIndex()
    val name = varchar("name", 50)
    val director = varchar("director", 50)
}

object Players : Table() {
    //val sequelId = integer("sequel_id").uniqueIndex().references(StarWarsFilms.sequelId)
    val sequelId = reference("sequel_id", StarWarsFilms.sequelId).uniqueIndex()
    //val filmId = reference("film_id", StarWarsFilms).nullable()
    val name = varchar("name", 50)
}

class StarWarsFilm(id: EntityID<Int>) : Entity<Int>(id) {
    companion object : EntityClass<Int, StarWarsFilm>(StarWarsFilms)

    var sequelId by StarWarsFilms.sequelId
    var name     by StarWarsFilms.name
    var director by StarWarsFilms.director
    var actors   by Actor via StarWarsFilmActors
    val ratings  by UserRating referrersOn UserRatings.film
}

object Users: IntIdTable() {
    val name = varchar("name", 50)
}

object UserRatings: IntIdTable() {
    val value = long("value")
    val film  = reference("film", StarWarsFilms)
    val user  = reference("user", Users)
}

class User(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var name by Users.name
}

class UserRating(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<UserRating>(UserRatings)

    var value by UserRatings.value
    var film  by StarWarsFilm referencedOn UserRatings.film
    var user  by User         referencedOn UserRatings.user
}

object Actors: IntIdTable() {
    val firstname = varchar("firstname", 50)
    val lastname = varchar("lastname", 50)
}

class Actor(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Actor>(Actors)

    var firstname by Actors.firstname
    var lastname by Actors.lastname
}

object StarWarsFilmActors : Table() {
    val starWarsFilm = reference("starWarsFilm", StarWarsFilms).primaryKey(0)
    val actor = reference("actor", Actors).primaryKey(1)
}