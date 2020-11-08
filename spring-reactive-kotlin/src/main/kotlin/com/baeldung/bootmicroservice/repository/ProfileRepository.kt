package com.baeldung.bootmicroservice.repository

import com.baeldung.bootmicroservice.model.Profile
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfileRepository: ReactiveCrudRepository<Profile, Long>