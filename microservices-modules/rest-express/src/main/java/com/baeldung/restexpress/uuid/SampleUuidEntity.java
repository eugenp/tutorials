package com.baeldung.restexpress.uuid;

import com.baeldung.restexpress.Constants;
import com.baeldung.restexpress.serialization.UuidFormatter;
import com.strategicgains.hyperexpress.annotation.BindToken;
import com.strategicgains.hyperexpress.annotation.TokenBindings;
import com.strategicgains.repoexpress.mongodb.AbstractUuidMongodbEntity;
import org.restexpress.plugin.hyperexpress.Linkable;

/**
 * This is a sample entity identified by a UUID (instead of a MongoDB ObjectID).
 * It also contains createdAt and updatedAt properties that are automatically maintained
 * by the persistence layer (SampleUuidEntityRepository).
 */
@TokenBindings({
        @BindToken(value = Constants.Url.SAMPLE_ID, field = "id", formatter = UuidFormatter.class)
})
public class SampleUuidEntity
        extends AbstractUuidMongodbEntity
        implements Linkable {
    public SampleUuidEntity() {
    }
}
