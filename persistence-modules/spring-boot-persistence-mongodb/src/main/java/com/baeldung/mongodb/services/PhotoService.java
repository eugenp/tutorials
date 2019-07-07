package com.baeldung.mongodb.services;

import java.io.IOException;
import java.util.Optional;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baeldung.mongodb.daos.PhotoRepository;
import com.baeldung.mongodb.models.Photo;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepo;

    public Photo getPhoto(String id) {
        Optional<Photo> result = photoRepo.findById(id);
        return result.isPresent() ? result.get() : null;
    }

    public String addPhoto(String title, MultipartFile file) {
        String id = null;
        try {
            Photo photo = new Photo(title);
            photo.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
            photo = photoRepo.insert(photo);
            id = photo.getId();
        } catch (IOException e) {
            return null;
        }
        return id;
    }
}
