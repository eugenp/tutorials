package org.baeldung.gridfs;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@ContextConfiguration("file:src/main/resources/mongoConfig.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class GridFSLiveTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @After
    public void tearDown() {
        List<GridFSDBFile> fileList = gridFsTemplate.find(null);
        for (GridFSDBFile file : fileList) {
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
            id = gridFsTemplate.store(inputStream, "test.png", "image/png", metaData).getId().toString();
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
        String id = "";
        try {
            inputStream = new FileInputStream("src/main/resources/test.png");
            id = gridFsTemplate.store(inputStream, "test.png", "image/png", metaData).getId().toString();
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

        GridFSDBFile gridFSDBFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));

        assertNotNull(gridFSDBFile);
        assertNotNull(gridFSDBFile.getInputStream());
        assertThat(gridFSDBFile.numChunks(), is(1));
        assertThat(gridFSDBFile.containsField("filename"), is(true));
        assertThat(gridFSDBFile.get("filename"), is("test.png"));
        assertThat(gridFSDBFile.getId(), is(id));
        assertThat(gridFSDBFile.keySet().size(), is(9));
        assertNotNull(gridFSDBFile.getMD5());
        assertNotNull(gridFSDBFile.getUploadDate());
        assertNull(gridFSDBFile.getAliases());
        assertNotNull(gridFSDBFile.getChunkSize());
        assertThat(gridFSDBFile.getContentType(), is("image/png"));
        assertThat(gridFSDBFile.getFilename(), is("test.png"));
        assertThat(gridFSDBFile.getMetaData().get("user"), is("alex"));
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

        List<GridFSDBFile> gridFSDBFiles = gridFsTemplate.find(null);

        assertNotNull(gridFSDBFiles);
        assertThat(gridFSDBFiles.size(), is(2));
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

        List<GridFSDBFile> gridFSDBFiles = gridFsTemplate.find(new Query(Criteria.where("metadata.user").is("alex")));

        assertNotNull(gridFSDBFiles);
        assertThat(gridFSDBFiles.size(), is(1));
    }

    @Test
    public void givenFileWithMetadataExist_whenDeletingFileById_thenFileWithMetadataIsDeleted() {
        DBObject metaData = new BasicDBObject();
        metaData.put("user", "alex");
        InputStream inputStream = null;
        String id = "";
        try {
            inputStream = new FileInputStream("src/main/resources/test.png");
            id = gridFsTemplate.store(inputStream, "test.png", "image/png", metaData).getId().toString();
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
        String id = "";
        try {
            inputStream = new FileInputStream("src/main/resources/test.png");
            id = gridFsTemplate.store(inputStream, "test.png", "image/png", metaData).getId().toString();
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
