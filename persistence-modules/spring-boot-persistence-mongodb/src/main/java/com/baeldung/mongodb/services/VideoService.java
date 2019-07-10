package com.baeldung.mongodb.services;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baeldung.mongodb.models.Video;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;

@Service
public class VideoService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations operations;

    public Video getVideo(String id) {
        Video video = null;
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        if (file != null) {
            video = new Video();
            video.setTitle(file.getMetadata().get("title").toString());
            try {
                video.setStream(operations.getResource(file).getInputStream());
            } catch (IOException e) {
                return null;
            }
        }
        return video;
    }

    public String addVideo(String title, MultipartFile file) {
        DBObject metaData = new BasicDBObject();
        metaData.put("type", "video");
        metaData.put("title", title);
        ObjectId id;
        try {
            id = gridFsTemplate.store(file.getInputStream(), file.getName(), file.getContentType(), metaData);
        } catch (IOException e) {
            return null;
        }
        return id.toString();
    }

}
