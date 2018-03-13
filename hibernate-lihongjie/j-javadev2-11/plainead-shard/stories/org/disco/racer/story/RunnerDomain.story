package org.disco.racer.story

import org.disco.racer.domain.*

//ignore all


before_each "obtain the dao from spring", {

    given "the database is seeded", {
        database_model("org.hsqldb.jdbcDriver",
                "jdbc:hsqldb:file:/Users/aglover/Development/demos/talks/hibernate-sharding/plainead-shard/db01/db01", "sa", "") {
            return new File("./test/conf/race-db.xml").text
        }
    }
}

scenario "runner object should return a runner by id", {

    when "find by id is invoked", {
        runner = Runner.findById(1)
    }
    then "a valid runner instance should be returned", {
        runner.shouldNotBe null
        runner.firstName.shouldBe "Andrew"
        runner.age.shouldBe 32
    }
}

scenario "runner object should return a runner by first and last name", {

    when "find by first and last name is invoked", {
        runners = Runner.findByFirstAndLastName("Andrew", "Glover")
    }
    then "a valid runner instance should be returned", {
        runners.size().shouldBe 1
        runners.each {
            it.age.shouldBe 32
        }
    }
}

scenario "runner object should have collection of races he/she is in", {

    when "find by id is invoked", {
        runner = Runner.findById(1)
    }
    then "a valid runner instance should be returned with races attached", {
        runner.races.size().shouldBe 1
        runner.races.each {
            it.name.shouldBe "Mclean 1/2 Marathon"
        }
    }
}

scenario "runner object should have collection of results he/she is in", {

    when "find by id is invoked", {
        runner = Runner.findById(1)
    }
    then "a valid runner instance should be returned with results attached", {
        runner.raceResults.size().shouldBe 1
        runner.raceResults.each {
            it.time.shouldBe 100.04
            it.ranking.shouldEqual 45
            it.race.id.shouldBe 1
        }
    }
}

scenario "new runners should be created", {
    given "a newly created runner object", {

        race = Race.findById(1)
        size = race.participants.size()
        irun = new Runner("Michael", "Lebby", 34, race)
        race.addParticipant irun
        race.update()
    }
    when "it is requested via a finder", {
        runner = Runner.findByFirstAndLastName("Michael", "Lebby")[0]
    }
    then "it should be returned", {
        runner.age.shouldBe 34

        irace = Race.findById(1)
        irace.participants.size().shouldBeGreaterThan size
    }
}

scenario "runners should be able to be updated", {
    given "a valid runner", {
        runner = Runner.findById(1)
    }
    when "its values are updated", {
        runner.lastName = "Law"
        runner.update()
    }
    then "they should be reflected in that runner in a new transaction", {
        nrunner = Runner.findById(1)
        nrunner.lastName.shouldBe "Law"
    }
}

scenario "runners should be able to be deleted", {
    given "a valid runner", {
        runner = Runner.findById(1)
    }
    when "it is deleted", {
        runner.delete()
    }
    then "it should no longer be in the database", {
        nrunner = Runner.findById(1)
        nrunner.shouldBe null
    }
}