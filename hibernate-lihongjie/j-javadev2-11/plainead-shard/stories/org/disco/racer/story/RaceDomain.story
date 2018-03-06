package org.disco.racer.story

import org.disco.racer.domain.Race

//ignore all


before_each "obtain the DAO from Spring", {
    given "the database is seeded", {
        database_model("org.hsqldb.jdbcDriver",
                "jdbc:hsqldb:file:/Users/aglover/Development/demos/talks/hibernate-sharding/plainead-shard/db01/db01", "sa", "") {
            return new File("./test/conf/race-db.xml").text
        }
    }
}

scenario "domain object should start up fine with seeded database", {

    when "the find method is called", {
        race = Race.findById(1)
    }

    then "the race should be Mclean 1/2 Marathon", {
        race.name.shouldBe "Mclean 1/2 Marathon"
    }

}

scenario "domain object should return a race with a runner", {

    when "the find by id method is called", {
        race = Race.findById(1)
    }

    then "the race should only have one person in it", {
        race.participants.size().shouldBe 1
    }
}

scenario "domain object should return a race with a runner when asked for by name", {

    when "the find by name method is called", {
        race = Race.findByName("Mclean 1/2 Marathon")
    }

    then "the race should have only 1 runner", {
        race.participants.size().shouldBe 1
    }
}

scenario "domain object should return a race with a runner when asked for by name and that runner should be populated", {

    when "the find by name method is called", {
        race = Race.findByName("Mclean 1/2 Marathon")
    }

    then "the race should have only 1 runner", {
        race.participants.size().shouldBe 1
    }

    and "the runner should be Andrew with age 32", {
        race.participants.each {runnr ->
            runnr.firstName.shouldBe "Andrew"
            runnr.age.shouldBe 32
        }

    }
}

scenario "an instance of a race should have results", {

    when "the find by name method is called", {
        race = Race.findByName("Mclean 1/2 Marathon")
    }

    then "the race should have only 1 result", {
        race.results.size().shouldBe 1
    }

}

scenario "an instance of a race should have results with data", {

    when "the find by name method is called", {
        race = Race.findByName("Mclean 1/2 Marathon")
    }

    then "the race should have only 1 result", {
        race.results.size().shouldBe 1
    }

    and "the one result should be interesting", {
        race.results.each {
            it.ranking.shouldBe 45
        }
    }

}

scenario "an instance of a race should have results and an easy way to find out the runner", {

    when "the find by name method is called", {
        race = Race.findByName("Mclean 1/2 Marathon")
    }

    then "the race should have only 1 result", {
        race.results.size().shouldBe 1
    }

    and "the one result should be interesting", {
        race.results.each {
            it.resultId.runnerId.shouldBe 1
        }
    }

}

scenario "an instance of a race should have results and link to runner", {

    when "the find by name method is called", {
        race = Race.findByName("Mclean 1/2 Marathon")
    }

    then "the race should have only 1 result", {
        race.results.size().shouldBe 1
    }

    and "the one result should be interesting", {
        race.results.each {
            it.runner.firstName.shouldBe "Andrew"
            it.race.name.shouldBe "Mclean 1/2 Marathon"
        }
    }

}

scenario "the domain object should faciliate creating new races", {
    given "a new race is created", {
        new Race("SML 10K", new Date(), 6.2,
                "race the hills of Smith Mountain lake").create()
    }
    then "the domain class should actually find it by name", {
        race = Race.findByName("SML 10K")
        race.description.shouldBe "race the hills of Smith Mountain lake"
    }

}

scenario "the domain object should support updating a race", {
    given "a valid race instance", {
        race = Race.findById(1)
    }
    when "that race is updated with new information", {
        race.description = "run 13 miles through lovely Mclean, VA!"
        race.update()
    }
    when "another transaction asks for the race", {
        nrace = Race.findById(1)
    }
    then "that other race instance should have a description", {
        nrace.description.shouldBe "run 13 miles through lovely Mclean, VA!"
    }
}

scenario "the domain object should support removing races", {
    given "and instance of a race", {
        race = Race.findById(1)
    }
    when "remove (or delete) is called", {
        race.remove()
    }
    when "this race is requsted", {
        nrace = Race.findById(1)
    }
    then "it should be purged from the system", {
        nrace.shouldBe null
    }
}

scenario "the domain object should return all races", {
    given "findall is called", {
        allraces = Race.findAll()
        //allraces.each{
        //    println it.name
        //}

    }
    then "a collection of 6 races should be returned", {
        allraces.size().shouldBe 9
    }
}