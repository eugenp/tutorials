package ws.cloudcache.memcache.marshallers;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Uses JAXB.
 * <p/>
 * User: treeder
 * Date: Sep 22, 2008
 * Time: 6:33:31 PM
 */
public class JAXBMarshaller implements Marshaller {

    private Map<Class, JAXBContext> contexts = new ConcurrentHashMap<Class, JAXBContext>();

    public JAXBMarshaller() {
    }

    public byte[] marshal(Serializable object) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JAXBContext context = getContext(object.getClass());
        javax.xml.bind.Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(new JAXBElement(new QName(object.getClass().getSimpleName()), object.getClass(), object), out);
        return out.toByteArray();
    }

    public String marshalToString(Serializable object) throws Exception {
        JAXBContext context = getContext(object.getClass());
        javax.xml.bind.Marshaller marshaller = context.createMarshaller();
        StringWriter out = new StringWriter();
        marshaller.marshal(new JAXBElement(new QName(object.getClass().getSimpleName()), object.getClass(), object), out);
        return out.toString();
    }

    private JAXBContext getContext(Class c) throws JAXBException {
        JAXBContext context = contexts.get(c);
        if (context == null) {
            context = JAXBContext.newInstance(c);
            contexts.put(c, context);
        }
        return context;
    }

    public Object unmarshal(InputStream inputStream, String className) throws Exception {
        Class expectedType = Class.forName(className);
        JAXBContext context = getContext(expectedType);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        JAXBElement o = unmarshaller.unmarshal(new StreamSource(inputStream), expectedType);
        return o.getValue();
    }

    public Object unmarshal(String className, CharSequence buffer) throws Exception {
        Class expectedType = Class.forName(className);
        JAXBContext context = getContext(expectedType);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object o = unmarshaller.unmarshal(new StreamSource(new StringReader(buffer.toString())), expectedType);
        return o;
    }
}
