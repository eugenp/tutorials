package com.baeldung.hexagonal.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeedReaderImpl implements FeedReader {

    @Autowired
    private FeedRepository feedRepository;

    @Override
    public Optional<Feed> read(Long id) {
        return feedRepository.findById(id);
    }
}
