package com.spring.security.kotlin.dsl

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.beans
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router

@EnableWebSecurity
@SpringBootApplication
class SpringSecurityKotlinApplication

@Order(1)
@Configuration
class AdminSecurityConfiguration : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http {
            authorizeRequests {
                authorize("/greetings/**", hasAuthority("ROLE_ADMIN"))
            }
            httpBasic {}
        }
    }
}

@Configuration
class BasicSecurityConfiguration : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http {
            authorizeRequests {
                authorize("/**", permitAll)
            }
            httpBasic {}
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SpringSecurityKotlinApplication>(*args) {
        addInitializers( beans {
            bean {
                fun user(user: String, password: String, vararg  roles: String) =
                        User
                        .withDefaultPasswordEncoder()
                        .username(user)
                        .password(password)
                        .roles(*roles)
                        .build()

                InMemoryUserDetailsManager(user("user", "password", "USER")
                         , user("admin", "password", "USER", "ADMIN"))
            }

            bean {
                router {
                    GET("/greetings") {
                        request -> request.principal().map { it.name }.map { ServerResponse.ok().body(mapOf("greeting" to "Hello $it")) }.orElseGet { ServerResponse.badRequest().build() }
                    }
                }
            }
        })
    }
}
