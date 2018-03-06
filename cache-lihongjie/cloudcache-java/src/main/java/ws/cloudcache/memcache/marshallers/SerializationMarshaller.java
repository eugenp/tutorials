package ws.cloudcache.memcache.marshallers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Uses standard java serialization.
 *
 * User: treeder
 * Date: Sep 22, 2008
 * Time: 6:29:10 PM
 */
public class SerializationMarshaller implements Marshaller {
    public byte[] marshal(Serializable object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(object);
        out.close();
        byte[] byteArray = bos.toByteArray();
        return byteArray;
    }

    public Object unmarshal(InputStream inputStream, String className) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(inputStream);
        Object o = in.readObject();
        in.close();
        return o;
    }

    public Object unmarshal(String className, CharSequence buffer) throws Exception {
        throw new java.lang.UnsupportedOperationException();
    }

    public String marshalToString(Serializable object) {
        throw new UnsupportedOperationException();
    }
}
