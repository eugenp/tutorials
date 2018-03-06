import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.disco.racer.domain.Race

scenario "Spring shards wired", {
    given "a spring context", {
       context = new ClassPathXmlApplicationContext("shardedspring-config.xml");
    }
    when "a sharded session factory is requested", {
        factory = context.getBean("mySessionFactory")
    }
    then "it should be wired correctly", {
        factory.shouldNotBe null
    }
}

scenario "daos should work too", {
    given "a dao", {
        rce = Race.findById(100)
    }

    then "it should work", {
        rce.name.shouldBe "Paris 1/2 Marathon"
        rce.distance.shouldEqual 13.1
    }
}

scenario "daos should work on another shard", {
    given "a dao", {
        rce = Race.findById(1)
    }

    then "it should work", {
        rce.name.shouldBe "Mclean 1/2 Marathon"
        rce.distance.shouldEqual 13.1
    }
}