package com.baeldung.scope

data class Student(var studentId: String = "", var name: String = "", var surname: String = "") {
}

data class Teacher(var teacherId: Int = 0, var name: String = "", var surname: String = "") {
    fun setId(anId: Int): Teacher = apply { teacherId = anId }
    fun setName(aName: String): Teacher = apply { name = aName }
    fun setSurname(aSurname: String): Teacher = apply { surname = aSurname }
}

data class Headers(val headerInfo: String)

data class Response(val headers: Headers)

data class RestClient(val url: String) {
    fun getResponse() = Response(Headers("some header info"))
}

data class BankAccount(val id: Int) {
    fun checkAuthorization(username: String) = Unit
    fun addPayee(payee: String) = Unit
    fun makePayment(paymentDetails: String) = Unit

}