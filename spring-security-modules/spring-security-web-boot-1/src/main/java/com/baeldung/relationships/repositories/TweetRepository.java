package com.baeldung.relationships.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.baeldung.relationships.models.Tweet;

public interface TweetRepository extends PagingAndSortingRepository<Tweet, Long> {

    @Query("SELECT twt FROM Tweet twt JOIN twt.likes AS lk WHERE lk = ?#{ principal?.username } OR twt.owner = ?#{ principal?.username }")
    Page<Tweet> getMyTweetsAndTheOnesILiked(Pageable pageable);
}
