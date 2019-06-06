package com.baeldung;

@Grab('io.ratpack:ratpack-groovy:1.6.1')
import static ratpack.groovy.Groovy.ratpack

import com.baeldung.model.User
import com.google.common.reflect.TypeToken
import ratpack.exec.Promise
import ratpack.handling.Context
import ratpack.jackson.Jackson
import groovy.sql.Sql
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import ratpack.hikari.HikariModule
import javax.sql.DataSource;

ratpack {
    serverConfig { port(5050) }
    bindings {
        module(HikariModule) { config ->
            config.dataSourceClassName = 'org.h2.jdbcx.JdbcDataSource'
            config.addDataSourceProperty('URL', "jdbc:h2:mem:devDB;INIT=RUNSCRIPT FROM 'classpath:/User.sql'")
        }
    }

    handlers {

        get { render 'Hello World from Ratpack with Groovy!!' }

        get("greet/:name") { Context ctx ->
            render "Hello " + ctx.getPathTokens().get("name") + "!!!"
        }

        get("data") {
            render Jackson.json([title: "Mr", name: "Norman", country: "USA"])
        }

        post("user") {
            Promise<User> user = parse(Jackson.fromJson(User))
            user.then { u -> render u.name }
        }

        get('fetchUserName/:id') { Context ctx ->
            Connection connection = ctx.get(DataSource.class).getConnection()
            PreparedStatement queryStatement = connection.prepareStatement("SELECT NAME FROM USER WHERE ID=?")
            queryStatement.setInt(1, Integer.parseInt(ctx.getPathTokens().get("id")))
            ResultSet resultSet = queryStatement.executeQuery()
            resultSet.next()
            render resultSet.getString(1)
        }

        get('fetchUsers') {
            def db = [url:'jdbc:h2:mem:devDB']
            def sql = Sql.newInstance(db.url, db.user, db.password)
            def users = sql.rows("SELECT * FROM USER");
            render(Jackson.json(users))
        }

        post('addUser') {
            parse(Jackson.fromJson(User))
                .then { u ->
                    def db = [url:'jdbc:h2:mem:devDB']
                    Sql sql = Sql.newInstance(db.url, db.user, db.password)
                    sql.executeInsert("INSERT INTO USER VALUES (?,?,?,?)", [
                        u.id,
                        u.title,
                        u.name,
                        u.country
                    ])
                    render "User $u.name inserted"
                }
        }
    }
}

