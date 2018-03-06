package org.disco.racer.story

import org.disco.racer.domain.Race
import org.disco.racer.domain.Runner

//ignore all

scenario "an existing race can have new runners added to it", {

    given "the database is seeded", {
        database_model("org.hsqldb.jdbcDriver",
                "jdbc:hsqldb:file:/Users/aglover/Development/demos/talks/hibernate-sharding/plainead-shard/db01/db01", "sa", "") {
            return new File("./test/conf/race-db.xml").text
        }
    }

    and "an instance of a race", {
        race = Race.findById(2)
        origsize = race.participants.size() //should be 2
    }

    when "a runner is added to it", {
        runner = new Runner("John", "Smith", 55, race)
        //TODO why is a create needed? Why can't I just have a runner created on
        //update?
        //runner.create()
        //runner.addRace race
        race.addParticipant runner

        race.update()
    }

    then "the race should have +1 runners assoicated with it", {
        nrace = Race.findById(2)
        nrace.participants.size().shouldBe(origsize + 1)
    }
}