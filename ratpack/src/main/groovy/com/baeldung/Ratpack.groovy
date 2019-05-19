package com.baeldung;

@Grab('io.ratpack:ratpack-groovy:1.6.1')
import static ratpack.groovy.Groovy.ratpack

import com.baeldung.model.User
import com.google.common.reflect.TypeToken
import ratpack.exec.Promise
import ratpack.handling.Context
import ratpack.jackson.Jackson
import groovy.sql.Sql
import ratpack.hikari.HikariModule

ratpack {
    bindings {
        module(HikariModule) { config ->
            config.dataSourceClassName = 'org.h2.jdbcx.JdbcDataSource'
            config.addDataSourceProperty('URL', "jdbc:h2:mem:devDB;INIT=RUNSCRIPT FROM 'classpath:/User.sql'")
        }
    }


    handlers {
        
        get {
            render 'Hello World from Ratpck with Groovy!!'
        }
        
        get("greet/:name"){Context ctx ->
            render "Hello " + ctx.getPathTokens().get("name") + " !!!"
        }
        
        get("data"){
            render Jackson.json([title:"Mr",name:"Norman",country:"USA"])
        }

        post("user") {
            Promise<User> user = parse(Jackson.fromJson(User))
            user.then { u -> render u.name }
        }
        
        get('fetchUsers') {
            def db = [url:'jdbc:h2:mem:devDB']
            def sql = Sql.newInstance(db.url, db.user, db.password)
            def users = sql.rows("select * from user");
            render(Jackson.json(users))
        }
        
        post('addUser') {
            Promise<User> user = parse(Jackson.fromJson(User))
            user.then { u -> 
                def db = [url:'jdbc:h2:mem:devDB']
                Sql sql = Sql.newInstance(db.url, db.user, db.password)
                sql.executeInsert("insert into user values (?,?,?,?)", [u.id, u.title, u.name, u.country])
                render "User $u.name inserted"
            }
        }
        
    }
}

