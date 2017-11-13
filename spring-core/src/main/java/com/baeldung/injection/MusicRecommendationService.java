package com.baeldung.injection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicRecommendationService {

    private MusicFinder finder;

    @Autowired
    public MusicRecommendationService(MusicFinder finder) {
        this.finder = finder;
    }

    public List<Music> recommend(Long userId) {
        return finder.find(userId);
    }

}
