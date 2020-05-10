package com.baeldung.datamapping

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals

class UserTest {

    @Test
    fun `maps User to UserResponse using extension function`() {
        val p = buildUser()
        val view = p.toUserView()
        assertUserView(view)
    }

    @Test
    fun `maps User to UserResponse using reflection`() {
        val p = buildUser()
        val view = p.toUserViewReflection()
        assertUserView(view)
    }

    private fun buildUser(): User {
        return User(
          "Java",
          "Duke",
          "Javastreet",
          "42",
          "1234567",
          30,
          "s3cr37"
        )
    }

    private fun assertUserView(pr: UserView) {
        assertAll(
          { assertEquals("Java Duke", pr.name) },
          { assertEquals("Javastreet 42", pr.address) },
          { assertEquals("1234567", pr.telephone) },
          { assertEquals(30, pr.age) }
        )
    }
}