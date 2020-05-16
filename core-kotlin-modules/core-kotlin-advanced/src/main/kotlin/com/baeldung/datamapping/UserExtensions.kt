package com.baeldung.datamapping

import kotlin.reflect.full.memberProperties

fun User.toUserView() = UserView(
        name = "$firstName $lastName",
        address = "$street $houseNumber",
        telephone = phone,
        age = age
)

fun User.toUserViewReflection() = with(::UserView) {
    val propertiesByName = User::class.memberProperties.associateBy { it.name }
    callBy(parameters.associate { parameter ->
        parameter to when (parameter.name) {
            UserView::name.name -> "$firstName $lastName"
            UserView::address.name -> "$street $houseNumber"
            UserView::telephone.name -> phone
            else -> propertiesByName[parameter.name]?.get(this@toUserViewReflection)
        }
    })
}