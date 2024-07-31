package com.baeldung.operators.deptrack.resources.deptrack;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.baeldung.operators.deptrack.resources.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.fabric8.kubernetes.api.model.ManagedFieldsEntry;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.client.CustomResource;

public final class BuilderHelper {
    private static ObjectMapper om;
    static {
        om = new ObjectMapper(new YAMLFactory());
    }

    private BuilderHelper(){}

    public static <T extends CustomResource<?,?>> ObjectMetaBuilder fromPrimary(T primary, String component) {
        return  new ObjectMetaBuilder()
          .withNamespace(primary.getMetadata().getNamespace())
          .withManagedFields((List<ManagedFieldsEntry>)null)
          .addToLabels("component", component)
          .addToLabels("name", primary.getMetadata().getName())
          .withName(primary.getMetadata().getName() + "-" + component)
          .addToLabels("ManagedBy", Constants.OPERATOR_NAME);
    }

    public static <T> T loadTemplate(Class<T> clazz, String resource) {

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if ( cl == null ) {
            cl =  BuilderHelper.class.getClassLoader();
        }

        try (InputStream is = cl.getResourceAsStream(resource)){
            return loadTemplate(clazz, is);
        }
        catch(IOException ioe) {
            throw new RuntimeException("Unable to load classpath resource '" + resource + "': " + ioe.getMessage());
        }


    }

    public  static <T> T loadTemplate(Class<T> clazz, InputStream is) throws IOException{
        return om.readValue(is, clazz);
    }
}
