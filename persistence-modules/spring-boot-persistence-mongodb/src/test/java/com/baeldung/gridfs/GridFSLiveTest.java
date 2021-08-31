package com.baeldung.gridfs;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;

/**
 * 
 * This test requires:
 * * mongodb instance running on the environment
 *
 */
@ContextConfiguration("file:src/main/resources/mongoConfig.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class GridFSLiveTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @After
    public void tearDown() {
        List<GridFSFile> fileList = new ArrayList<GridFSFile>();
        gridFsTemplate.find(new Query()).into(fileList);
        for (GridFSFile file : fileList) {
            gridFsTemplate.delete(new Query(Criteria.where("filename").is(file.getFilename())));
        }
    }

    @Test
    public void whenStoringFileWithMetadata_thenFileAndMetadataAreStored() {
        DBObject metaData = new BasicDBObject();
        metaData.put("user", "alex");
        InputStream inputStream = null;
        String id = "";
        try {
            inputStream = new FileInputStream("src/main/resources/test.png");
            id = gridFsTemplate.store(inputStream, "test.png", "image/png", metaData).toString();
        } catch (FileNotFoundException ex) {
            logger.error("File not found", ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    logger.error("Failed to close", ex);
                }
            }
        }

        assertNotNull(id);
    }

    @Test
    public void givenFileWithMetadataExist_whenFindingFileById_thenFileWithMetadataIsFound() {
        DBObject metaData = new BasicDBObject();
        metaData.put("user", "alex");
        InputStream inputStream = null;
        ObjectId id = null;
        try {
            inputStream = new FileInputStream("src/main/resources/test.png");
            id = gridFsTemplate.store(inputStream, "test.png", "image/png", metaData);
        } catch (FileNotFoundException ex) {
            logger.error("File not found", ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    logger.error("Failed to close", ex);
                }
            }
        }

        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));

        assertNotNull(gridFSFile);
//        assertNotNull(gridFSFile.getInputStream());
//        assertThat(gridFSFile.numChunks(), is(1));
//        assertThat(gridFSFile.containsField("filename"), is(true));
        assertThat(gridFSFile.getFilename(), is("test.png"));
        assertThat(gridFSFile.getObjectId(), is(id));
//        assertThat(gridFSFile.keySet().size(), is(9));
//        assertNotNull(gridFSFile.getMD5());
        assertNotNull(gridFSFile.getUploadDate());
//        assertNull(gridFSFile.getAliases());
        assertNotNull(gridFSFile.getChunkSize());
        //assertThat(gridFSFile.getMetadata().get("_contentType"), is("image/png"));
        assertThat(gridFSFile.getFilename(), is("test.png"));
        assertThat(gridFSFile.getMetadata().get("user"), is("alex"));
    }

    @Test
    public void givenMetadataAndFilesExist_whenFindingAllFiles_thenFilesWithMetadataAreFound() {
        DBObject metaDataUser1 = new BasicDBObject();
        metaDataUser1.put("user", "alex");
        DBObject metaDataUser2 = new BasicDBObject();
        metaDataUser2.put("user", "david");
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream("src/main/resources/test.png");
            gridFsTemplate.store(inputStream, "test.png", "image/png", metaDataUser1);
            gridFsTemplate.store(inputStream, "test.png", "image/png", metaDataUser2);
        } catch (FileNotFoundException ex) {
            logger.error("File not found", ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    logger.error("Failed to close", ex);
                }
            }
        }

        List<GridFSFile> gridFSFiles = new ArrayList<GridFSFile>();
        gridFsTemplate.find(new Query()).into(gridFSFiles);

        assertNotNull(gridFSFiles);
        assertThat(gridFSFiles.size(), is(2));
    }

    @Test
    public void givenMetadataAndFilesExist_whenFindingAllFilesOnQuery_thenFilesWithMetadataAreFoundOnQuery() {
        DBObject metaDataUser1 = new BasicDBObject();
        metaDataUser1.put("user", "alex");
        DBObject metaDataUser2 = new BasicDBObject();
        metaDataUser2.put("user", "david");
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream("src/main/resources/test.png");
            gridFsTemplate.store(inputStream, "test.png", "image/png", metaDataUser1);
            gridFsTemplate.store(inputStream, "test.png", "image/png", metaDataUser2);
        } catch (FileNotFoundException ex) {
            logger.error("File not found", ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    logger.error("Failed to close", ex);
                }
            }
        }

        List<GridFSFile> gridFSFiles = new ArrayList<GridFSFile>();
        gridFsTemplate.find(new Query(Criteria.where("metadata.user").is("alex"))).into(gridFSFiles);

        assertNotNull(gridFSFiles);
        assertThat(gridFSFiles.size(), is(1));
    }

    @Test
    public void givenFileWithMetadataExist_whenDeletingFileById_thenFileWithMetadataIsDeleted() {
        DBObject metaData = new BasicDBObject();
        metaData.put("user", "alex");
        InputStream inputStream = null;
        String id = "";
        try {
            inputStream = new FileInputStream("src/main/resources/test.png");
            id = gridFsTemplate.store(inputStream, "test.png", "image/png", metaData).toString();
        } catch (FileNotFoundException ex) {
            logger.error("File not found", ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    logger.error("Failed to close", ex);
                }
            }
        }

        gridFsTemplate.delete(new Query(Criteria.where("_id").is(id)));

        assertThat(gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id))), is(nullValue()));
    }

    @Test
    public void givenFileWithMetadataExist_whenGettingFileByResource_thenFileWithMetadataIsGotten() {
        DBObject metaData = new BasicDBObject();
        metaData.put("user", "alex");
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("src/main/resources/test.png");
            gridFsTemplate.store(inputStream, "test.png", "image/png", metaData).toString();
        } catch (FileNotFoundException ex) {
            logger.error("File not found", ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    logger.error("Failed to close", ex);
                }
            }
        }

        GridFsResource[] gridFsResource = gridFsTemplate.getResources("test*");

        assertNotNull(gridFsResource);
        assertEquals(gridFsResource.length, 1);
        assertThat(gridFsResource[0].getFilename(), is("test.png"));
    }
}
