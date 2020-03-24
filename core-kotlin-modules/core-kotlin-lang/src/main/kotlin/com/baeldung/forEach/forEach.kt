package com.baeldung.forEach


class Country(val name : String, val cities : List<City>)

class City(val name : String, val streets : List<String>)

fun City.getStreetsWithCityName() : List<String> {
    return streets.map { "$name, $it" }.toList()
}

fun Country.getCitiesWithCountryName() : List<String> {
    return cities.flatMap { it.getStreetsWithCityName() }
            .map { "$name, $it" }
}

class World {

    private val streetsOfAmsterdam = listOf("Herengracht", "Prinsengracht")
    private val streetsOfBerlin = listOf("Unter den Linden","Tiergarten")
    private val streetsOfMaastricht = listOf("Grote Gracht", "Vrijthof")
    private val countries = listOf(
            Country("Netherlands", listOf(City("Maastricht", streetsOfMaastricht),
                    City("Amsterdam", streetsOfAmsterdam))),
            Country("Germany", listOf(City("Berlin", streetsOfBerlin))))

    fun allCountriesIt() {
        countries.forEach { println(it.name) }
    }

    fun allCountriesItExplicit() {
        countries.forEach { it -> println(it.name) }
    }

    //here we cannot refer to 'it' anymore inside the forEach
    fun allCountriesExplicit() {
        countries.forEach { c -> println(c.name) }
    }

    fun allNested() {
        countries.forEach {
            println(it.name)
            it.cities.forEach {
                println(" ${it.name}")
                it.streets.forEach { println("  $it") }
            }
        }
    }

    fun allTable() {
        countries.forEach { c ->
            c.cities.forEach { p ->
                p.streets.forEach { println("${c.name} ${p.name} $it") }
            }
        }
    }

    fun allStreetsFlatMap() {

        countries.flatMap { it.cities}
                .flatMap { it.streets}
                .forEach { println(it) }
    }

    fun allFlatMapTable() {

        countries.flatMap { it.getCitiesWithCountryName() }
                .forEach { println(it) }
    }
}

fun main(args : Array<String>) {

    val world = World()

    world.allCountriesExplicit()

    world.allNested()

    world.allTable()

    world.allStreetsFlatMap()

    world.allFlatMapTable()
}


