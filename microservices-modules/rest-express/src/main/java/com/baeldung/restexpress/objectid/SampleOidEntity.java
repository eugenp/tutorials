package com.baeldung.restexpress.objectid;

import com.baeldung.restexpress.Constants;
import com.strategicgains.hyperexpress.annotation.BindToken;
import com.strategicgains.hyperexpress.annotation.TokenBindings;
import com.strategicgains.repoexpress.mongodb.AbstractMongodbEntity;
import org.restexpress.plugin.hyperexpress.Linkable;

/**
 * This is a sample entity identified by a MongoDB ObjectID (instead of a UUID).
 * It also contains createdAt and updatedAt properties that are automatically maintained
 * by the persistence layer (SampleOidEntityRepository).
 */
@TokenBindings({
        @BindToken(value = Constants.Url.SAMPLE_ID, field = "id")
})
public class SampleOidEntity
        extends AbstractMongodbEntity
        implements Linkable {
    private String name;

    public SampleOidEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
