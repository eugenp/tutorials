package com.spaceprogram.bigcache;

import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * User: treeder
 * Date: Sep 22, 2008
 * Time: 1:39:36 AM
 */
public class SerializationPerformanceTests {

    /**
     * This test compares a bunch of serializers.
     * @throws Exception
     */
    @Test
    public void testSerializationVsJaxb() throws Exception {


        List<Times> times = new ArrayList<Times>();
        Times jaxb = new Times("jaxb");
        times.add(jaxb);

        Times serial = new Times("serialization");
        times.add(serial);

        Times xstreamt = new Times("xstreamXml");
        times.add(xstreamt);

        Times xstreamjson = new Times("xstreamJson");
        times.add(xstreamjson);
        long totalFlexOut = 0;
        long totalFlexIn = 0;

        Times jabsorbT = new Times("jabsorb");
        times.add(jabsorbT);

        int flexjsonsize = 0;
        Times jboss = new Times("jboss");
        times.add(jboss);
        Times hessian = new Times("hessian");
        times.add(hessian);
        {
            JAXBContext context = JAXBContext.newInstance(RootObject.class);
            Marshaller marshaller = context.createMarshaller();
            Unmarshaller unmarshaller = context.createUnmarshaller();

            for (int i = 0; i < 100; i++) {
                RootObject testOb = new RootObject();
                testOb.setId("" + i);
                testOb.setCreated(new Date());
                testOb.addOther(new SomeObject2("so-" + i));
                testOb.addOther(new SomeObject2("so2-" + i));

                StringWriter writer = new StringWriter(5000);
                long start = System.currentTimeMillis();
                marshaller.marshal(new JAXBElement(new QName(testOb.getClass().getSimpleName()), testOb.getClass(), testOb), writer);
                long duration = System.currentTimeMillis() - start;
                jaxb.addOut(duration);
                byte[] ba = writer.toString().getBytes();
                jaxb.setSize(ba.length);
                if (i == 0) System.out.println("jaxb=" + writer.toString());

                StringReader reader = new StringReader(writer.toString());
//                XMLStreamReader r2 = new (new ByteArrayInputStream(ba));
//                System.out.println("writer.tostring=" + writer.toString());
                start = System.currentTimeMillis();
                unmarshaller.unmarshal(new StreamSource(reader), testOb.getClass());
                duration = System.currentTimeMillis() - start;
                jaxb.addIn(duration);


                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutput out = new ObjectOutputStream(bos);
                start = System.currentTimeMillis();
                out.writeObject(testOb);
                out.close();
                duration = System.currentTimeMillis() - start;
                byte[] byteArray = bos.toByteArray();
                serial.addOut(duration);
                serial.setSize(byteArray.length);

                ByteArrayInputStream bin = new ByteArrayInputStream(byteArray);
                ObjectInputStream in = new ObjectInputStream(bin);
                start = System.currentTimeMillis();
                Object o = in.readObject();
                in.close();
                duration = System.currentTimeMillis() - start;
                serial.addIn(duration);

                String s;

                /*XStream xstream = new XStream();
                xstream.alias("wrapper", RootObject.class);
                start = System.currentTimeMillis();
                String s = xstream.toXML(testOb);
                duration = System.currentTimeMillis() - start;
                xstreamt.addOut(duration);
                xstreamt.setSize(s.getBytes().length);

                start = System.currentTimeMillis();
                xstream.fromXML(s);
                duration = System.currentTimeMillis() - start;
                xstreamt.addIn(duration);

                xstream = new XStream(new JettisonMappedXmlDriver());
                xstream.alias("wrapper", RootObject.class);
                start = System.currentTimeMillis();
                s = xstream.toXML(testOb);
                duration = System.currentTimeMillis() - start;
                xstreamjson.addOut(duration);
                xstreamjson.setSize(s.getBytes().length);
                System.out.println("xstream=" + s);

                start = System.currentTimeMillis();
                xstream.fromXML(s);
                duration = System.currentTimeMillis() - start;
                xstreamjson.addIn(duration);

                JSONSerializer serializer = new JSONSerializer();
                start = System.currentTimeMillis();
                s = serializer.serialize(testOb);
                duration = System.currentTimeMillis() - start;
                totalFlexOut += duration;
                flexjsonsize = s.getBytes().length;
                System.out.println("flex=" + s);*/

                /*start = System.currentTimeMillis();
                serializer.
                duration = System.currentTimeMillis() - start;
                totalFlexIn += duration;*/


                /*org.jabsorb.JSONSerializer jsonSerializer = new org.jabsorb.JSONSerializer();
                jsonSerializer.registerDefaultSerializers();
                start = System.currentTimeMillis();
                s = jsonSerializer.toJSON(testOb);
                duration = System.currentTimeMillis() - start;
                jabsorbT.addOut(duration);
                jabsorbT.setSize(s.getBytes().length);
                if (i == 0)
                    System.out.println("jabsorb=" + s);

                start = System.currentTimeMillis();
                testOb = (RootObject) jsonSerializer.fromJSON(s);
                duration = System.currentTimeMillis() - start;
//                System.out.println("test=" + testOb);
                jabsorbT.addIn(duration);
*/
                /* bos = new ByteArrayOutputStream();
                JBossObjectOutputStream outJboss = new JBossObjectOutputStream(bos);
                start = System.currentTimeMillis();
                outJboss.writeObject(testOb);
                outJboss.close();
                duration = System.currentTimeMillis() - start;
                jboss.addOut(duration);
                byteArray = bos.toByteArray();
                jboss.setSize(byteArray.length);

                bin = new ByteArrayInputStream(byteArray);
                JBossObjectInputStream inJboss = new JBossObjectInputStream(bin);
                start = System.currentTimeMillis();
                o = inJboss.readObject();
                inJboss.close();
                duration = System.currentTimeMillis() - start;
                jboss.addIn(duration);*/

               /* bos = new ByteArrayOutputStream();
                Hessian2Output outh = new Hessian2Output(bos);
                start = System.currentTimeMillis();
                outh.writeObject(testOb);
                outh.close();
                duration = System.currentTimeMillis() - start;
                byteArray = bos.toByteArray();
                hessian.addOut(duration);
                hessian.setSize(byteArray.length);

                bin = new ByteArrayInputStream(byteArray);
                Hessian2Input inh = new Hessian2Input(bin);
                start = System.currentTimeMillis();
                o = inh.readObject();
                inh.close();
                duration = System.currentTimeMillis() - start;
                hessian.addIn(duration);*/

            }

            for (Times time : times) {
                System.out.println(time);
            }
        }

    }
}
