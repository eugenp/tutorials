import groovy.sql.Sql
import org.disco.racer.domain.Race
import org.disco.racer.domain.Runner

db01url = "jdbc:hsqldb:file:/Users/aglover/Development/demos/talks/hibernate-sharding/plainead-shard/db01/db01"
db02url = "jdbc:hsqldb:file:/Users/aglover/Development/demos/talks/hibernate-sharding/plainead-shard/db02/db02"
driver = "org.hsqldb.jdbcDriver"
name = "sa"
psswrd = ""

before_each "Seed the 2 databases", {
  given "DB01 is seeded", {
    database_model(driver, db01url, name, psswrd) {
      return new File("./test/conf/race-db01.xml").text
    }
  }
  and "DB02 is seeded", {
    database_model(driver, db02url, name, psswrd) {
      return new File("./test/conf/race-db02.xml").text
    }
  }
}



scenario "races greater than 10.0K should be in shard 1 or db02", {
  given "a newly created race that is over 10.0K", {
    new Race("Belgium Marathon", new Date(), 26.2,
            "Race the beautiful country of Belgium!").create()
  }
  then "everything should work fine w/respect to Hibernate", {
    rce = Race.findByName("Belgium Marathon")
    rce.distance.shouldBe 26.2
  }
  and "the race should be stored in shard 1 or db02", {
    sql = Sql.newInstance(db02url, name, psswrd, driver)
    sql.eachRow("select race_id, distance, name from race where name=?", ["Belgium Marathon"]) {row ->
      row.distance.shouldBe 26.2
    }
    sql.close()
  }

  and "the race should NOT be stored in shard 0 or db01", {
    sql = Sql.newInstance(db01url, name, psswrd, driver)
    sql.eachRow("select race_id, distance, name from race where name=?", ["Belgium Marathon"]) {row ->
      fail "shard 0 contains a marathon!"
    }
    sql.close()
  }
}

scenario "races less than 10.0K should be in shard 0 or db01", {
  given "a newly created race that is over 10.0K", {
    new Race("Belgium 5K", new Date(), 3.1,
            "Fly fast on this fast course").create()
  }
  then "everything should work fine w/respect to Hibernate", {
    rce = Race.findByName("Belgium 5K")
    rce.distance.shouldBe 3.1
  }
  and "the race should be stored in shard 1 or db02", {
    sql = Sql.newInstance(db01url, name, psswrd, driver)
    sql.eachRow("select race_id, distance, name from race") {row ->
      if (row.name.equals("Belgium 5K")) {
        row.distance.shouldBe 3.1
      }
    }
    sql.close()
  }
}


scenario "Runners should be saved in the shard in which race they are singed up for", {
  given "the race participant table has a known size", {
    sql = Sql.newInstance(db01url, name, psswrd, driver)
    initialcount = 0
    sql.eachRow("select * from RACE_PARTICIPANTS") {row ->
      if (row.race_id == 15) {
        initialcount++
      }
    }
    sql.close()
  }
  and "a newly created runner object added to a shard 0 race", {
    race = Race.findById(15)
    size = race.participants.size()
    irun = new Runner("Michaels", "Lebbys", 84, race)
    race.addParticipant irun
    race.update()
  }
  when "it that runner is requested via a finder", {
    runner = Runner.findByFirstAndLastName("Michaels", "Lebbys")[0]
  }
  then "it should be returned and it should be assoicated with a race", {
    runner.age.shouldBe 84
    irace = Race.findById(15)
    irace.participants.size().shouldBeGreaterThan size
  }
  and "the race participant table should have a runner with it", {
    sql = Sql.newInstance(db01url, name, psswrd, driver)
    finalcount = 0
    sql.eachRow("select * from RACE_PARTICIPANTS") {row ->
      if (row.race_id == 15) {
        finalcount++
      }
    }
    sql.close()
    finalcount.shouldBeGreaterThan initialcount
  }
  and "the race should be stored in shard 1 or db02", {
    sql = Sql.newInstance(db01url, name, psswrd, driver)
    sql.eachRow("select runner_id, first_name, last_name, age from runner") {row ->
      if (row.last_name.equals("Ibby")) {
        row.age.shouldBe 84
      }
    }
    sql.close()
  }
}



scenario "races greater than 10.0K and assoicated runners should be in shard 1 or db02", {
  given "a newly created race that is over 10.0K", {
    new Race("Patriot Marathon", new Date(), 26.2,
            "Race beautiful Williamsburg").create()
  }

  and "a newly created runner object added to a shard 0 race", {
    rce = Race.findByName("Patriot Marathon")
    isize = rce.participants.size()
    irun = new Runner("Jason", "Randu", 14, rce)
    rce.addParticipant irun
    rce.update()
  }

  then "everything should work fine w/respect to Hibernate", {
    rce = Race.findByName("Patriot Marathon")
    rce.distance.shouldBe 26.2
  }

  and "the race should be stored in shard 1 or db02", {
    sql = Sql.newInstance(db02url, name, psswrd, driver)
    sql.eachRow("select race_id, distance, name from race") {row ->
      if (row.name.equals("Patriot Marathon")) {
        row.distance.shouldBe 26.2
      }
    }
    sql.close()
  }

  and "the race should have another person in it", {
    rceagain = Race.findByName("Patriot Marathon")
    newsize = rceagain.participants.size()
    newsize.shouldBeGreaterThan isize
  }

  and "the race runner assoicatation should be stored in shard 1 or db02", {
    patriotrace = Race.findByName("Patriot Marathon")
    patriotrunner = Runner.findByFirstAndLastName("Jason", "Randu")[0]
    sql = Sql.newInstance(db02url, name, psswrd, driver)
    racerscount = 0
    sql.eachRow("select runner_id from RACE_PARTICIPANTS where race_id = ${patriotrace.id}") {row ->
      row.runner_id.shouldEqual patriotrunner.id
    }
    sql.close()
  }
}

after "put the database back into a known state", {
  given "DB02 is reseeded", {
    database_model(driver, db02url, name, psswrd) {
      return new File("./test/conf/race-db02-1.xml").text
    }
  }
}
