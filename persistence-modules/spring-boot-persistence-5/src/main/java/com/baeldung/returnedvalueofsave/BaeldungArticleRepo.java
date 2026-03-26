package com.baeldung.returnedvalueofsave;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.returnedvalueofsave.entity.BaeldungArticle;

@Repository
interface BaeldungArticleRepo extends JpaRepository<BaeldungArticle, Long> {

}