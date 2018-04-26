package com.baeldung

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface EventRepository : ReactiveMongoRepository<Event, String>
