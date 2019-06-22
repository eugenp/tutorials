package com.baeldung.hexagonal.core.domain.bo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

@Data
@Slf4j
public class PostBo {

    Long id;
    String title;
    String content;
    PostState state;

    public long computeWordCount() {
        String line;
        long count = 0L;
        try (BufferedReader br = new BufferedReader(new StringReader(content))) {
            while ((line = br.readLine()) != null) {
                count += line.split("\\s+").length;
            }
        } catch (IOException e) {
            log.error("IO error while getting the word count", e);
        }
        return count;
    }

    public boolean isReady() {
        return state == PostState.READY;
    }

    public boolean isWorkInProgress() {
        return state == PostState.DRAFT || state == PostState.REVIEW;
    }

    public boolean isDraft() {
        return state == PostState.DRAFT;
    }

    public boolean isReadyForReview() {
        return state != PostState.REVIEW;
    }

    public enum PostState {
        DRAFT, REVIEW, READY, PUBLISHED
    }
}
