package org.baeldung

class User {
    String userName
    String password
    String email
    Integer age
    static constraints = {
        userName blank: false, unique: true
        password size: 5..10, blank: false
        email email: true, blank: true
        age min: 10
    }
    static mapping = { sort "userName" }
}
