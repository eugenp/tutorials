package com.baeldung.mongodb.services;

import java.io.IOException;

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
        return photoRepo.findById(id).get();
    }

    public String addPhoto(String title, MultipartFile file) throws IOException {
        Photo photo = new Photo(title);
        photo.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        photo = photoRepo.insert(photo);
        return photo.getId();
    }
}
