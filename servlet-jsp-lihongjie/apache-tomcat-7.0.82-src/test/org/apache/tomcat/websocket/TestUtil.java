/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tomcat.websocket;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import javax.websocket.Extension;
import javax.websocket.Extension.Parameter;
import javax.websocket.MessageHandler;

import org.junit.Assert;
import org.junit.Test;

public class TestUtil {

    // Used to init SecureRandom prior to running tests
    public static void generateMask() {
        Util.generateMask();
    }
    
    @Test
    public void testGetMessageTypeSimple() {
        Assert.assertEquals(
                String.class, Util.getMessageType(new SimpleMessageHandler()));
    }


    @Test
    public void testGetMessageTypeSubclass() {
        Assert.assertEquals(String.class,
                Util.getMessageType(new SubSimpleMessageHandler()));
    }


    @Test
    public void testGetMessageTypeGenericSubclass() {
        Assert.assertEquals(String.class,
                Util.getMessageType(new GenericSubMessageHandler()));
    }


    @Test
    public void testGetMessageTypeGenericMultipleSubclass() {
        Assert.assertEquals(String.class,
                Util.getMessageType(new GenericMultipleSubSubMessageHandler()));
    }


    @Test
    public void testGetMessageTypeGenericMultipleSubclassSwap() {
        Assert.assertEquals(String.class,
                Util.getMessageType(new GenericMultipleSubSubSwapMessageHandler()));
    }


    @Test
    public void testGetEncoderTypeSimple() {
        Assert.assertEquals(
                String.class, Util.getEncoderType(SimpleEncoder.class));
    }


    @Test
    public void testGetEncoderTypeSubclass() {
        Assert.assertEquals(String.class,
                Util.getEncoderType(SubSimpleEncoder.class));
    }


    @Test
    public void testGetEncoderTypeGenericSubclass() {
        Assert.assertEquals(String.class,
                Util.getEncoderType(GenericSubEncoder.class));
    }


    @Test
    public void testGetEncoderTypeGenericMultipleSubclass() {
        Assert.assertEquals(String.class,
                Util.getEncoderType(GenericMultipleSubSubEncoder.class));
    }


    @Test
    public void testGetEncoderTypeGenericMultipleSubclassSwap() {
        Assert.assertEquals(String.class,
                Util.getEncoderType(GenericMultipleSubSubSwapEncoder.class));
    }


    @Test
    public void testGetEncoderTypeSimpleWithGenericType() {
        Assert.assertEquals(List.class,
                Util.getEncoderType(SimpleEncoderWithGenericType.class));
    }


    @Test
    public void testGenericArrayEncoderString() {
        Assert.assertEquals(String[].class,
                Util.getEncoderType(GenericArrayEncoderString.class));
    }


    @Test
    public void testGenericArraySubEncoderString() {
        Assert.assertEquals(String[][].class,
                Util.getEncoderType(GenericArraySubEncoderString.class));
    }


    private static class SimpleMessageHandler
            implements MessageHandler.Whole<String> {
        @Override
        public void onMessage(String message) {
            // NO-OP
        }
    }


    private static class SubSimpleMessageHandler extends SimpleMessageHandler {
    }


    private abstract static class GenericMessageHandler<T> implements MessageHandler.Whole<T> {
    }


    private static class GenericSubMessageHandler extends GenericMessageHandler<String> {
        @Override
        public void onMessage(String message) {
            // NO-OP
        }
    }


    private static interface Foo<T> {
        void doSomething(T thing);
    }


    private abstract static class GenericMultipleMessageHandler<A,B>
            implements MessageHandler.Whole<A>, Foo<B> {
    }


    private abstract static class GenericMultipleSubMessageHandler<X,Y>
            extends GenericMultipleMessageHandler<X,Y> {
    }


    private static class GenericMultipleSubSubMessageHandler
            extends GenericMultipleSubMessageHandler<String,Boolean> {

        @Override
        public void onMessage(String message) {
            // NO-OP
        }

        @Override
        public void doSomething(Boolean thing) {
            // NO-OP
        }
    }


    private abstract static class GenericMultipleSubSwapMessageHandler<Y,X>
            extends GenericMultipleMessageHandler<X,Y> {
    }


    private static class GenericMultipleSubSubSwapMessageHandler
            extends GenericMultipleSubSwapMessageHandler<Boolean,String> {

        @Override
        public void onMessage(String message) {
            // NO-OP
        }

        @Override
        public void doSomething(Boolean thing) {
            // NO-OP
        }
    }


    private static class SimpleEncoder implements Encoder.Text<String> {

        @Override
        public void init(EndpointConfig endpointConfig) {
            // NO-OP
        }

        @Override
        public void destroy() {
            // NO-OP
        }

        @Override
        public String encode(String object) throws EncodeException {
            return null;
        }
    }


    private static class SubSimpleEncoder extends SimpleEncoder {
    }


    private abstract static class GenericEncoder<T> implements Encoder.Text<T> {

        @Override
        public void init(EndpointConfig endpointConfig) {
            // NO-OP
        }

        @Override
        public void destroy() {
            // NO-OP
        }
    }


    private static class GenericSubEncoder extends GenericEncoder<String> {

        @Override
        public String encode(String object) throws EncodeException {
            return null;
        }

    }


    private abstract static class GenericMultipleEncoder<A,B>
            implements Encoder.Text<A>, Foo<B> {

        @Override
        public void init(EndpointConfig endpointConfig) {
            // NO-OP
        }

        @Override
        public void destroy() {
            // NO-OP
        }
    }


    private abstract static class GenericMultipleSubEncoder<X,Y>
            extends GenericMultipleEncoder<X,Y> {
    }


    private static class GenericMultipleSubSubEncoder
            extends GenericMultipleSubEncoder<String,Boolean> {

        @Override
        public String encode(String object) throws EncodeException {
            return null;
        }

        @Override
        public void doSomething(Boolean thing) {
            // NO-OP
        }

    }


    private abstract static class GenericMultipleSubSwapEncoder<Y,X>
            extends GenericMultipleEncoder<X,Y> {
    }


    private static class GenericMultipleSubSubSwapEncoder
            extends GenericMultipleSubSwapEncoder<Boolean,String> {

        @Override
        public String encode(String object) throws EncodeException {
            return null;
        }

        @Override
        public void doSomething(Boolean thing) {
            // NO-OP
        }
    }


    private static class SimpleEncoderWithGenericType
            implements Encoder.Text<List<String>> {

        @Override
        public void init(EndpointConfig endpointConfig) {
            // NO-OP
        }

        @Override
        public void destroy() {
            // NO-OP
        }

        @Override
        public String encode(List<String> object) throws EncodeException {
            return null;
        }
    }


    private abstract static class GenericArrayEncoder<T> implements Encoder.Text<T[]> {
    }


    private static class GenericArrayEncoderString extends GenericArrayEncoder<String> {

        @Override
        public void init(EndpointConfig endpointConfig) {
            // NO-OP
        }

        @Override
        public void destroy() {
            // NO-OP
        }

        @Override
        public String encode(String[] object) throws EncodeException {
            return null;
        }
    }


    private abstract static class GenericArraySubEncoder<T> extends GenericArrayEncoder<T[]> {
    }


    private static class GenericArraySubEncoderString extends GenericArraySubEncoder<String> {

        @Override
        public void init(EndpointConfig endpointConfig) {
            // NO-OP
        }

        @Override
        public void destroy() {
            // NO-OP
        }

        @Override
        public String encode(String[][] object) throws EncodeException {
            return null;
        }
    }


    @Test
    public void testParseExtensionHeaderSimple01() {
        doTestParseExtensionHeaderSimple("ext;a=1;b=2");
    }

    @Test
    public void testParseExtensionHeaderSimple02() {
        doTestParseExtensionHeaderSimple("ext;a=\"1\";b=2");
    }

    @Test
    public void testParseExtensionHeaderSimple03() {
        doTestParseExtensionHeaderSimple("ext;a=1;b=\"2\"");
    }

    @Test
    public void testParseExtensionHeaderSimple04() {
        doTestParseExtensionHeaderSimple(" ext ; a = 1 ; b = 2 ");
    }

    private void doTestParseExtensionHeaderSimple(String header) {
        // Simple test
        List<Extension> result = new ArrayList<Extension>();
        Util.parseExtensionHeader(result, header);

        Assert.assertEquals(1, result.size());

        Extension ext = result.get(0);
        Assert.assertEquals("ext", ext.getName());
        List<Parameter> params = ext.getParameters();
        Assert.assertEquals(2, params.size());
        Parameter paramA = params.get(0);
        Assert.assertEquals("a", paramA.getName());
        Assert.assertEquals("1", paramA.getValue());
        Parameter paramB = params.get(1);
        Assert.assertEquals("b", paramB.getName());
        Assert.assertEquals("2", paramB.getValue());
    }


    @Test
    public void testParseExtensionHeaderMultiple01() {
        doTestParseExtensionHeaderMultiple("ext;a=1;b=2,ext2;c;d=xyz,ext3");
    }

    @Test
    public void testParseExtensionHeaderMultiple02() {
        doTestParseExtensionHeaderMultiple(
                " ext ; a = 1 ; b = 2 , ext2 ; c ; d = xyz , ext3 ");
    }

    private void doTestParseExtensionHeaderMultiple(String header) {
        // Simple test
        List<Extension> result = new ArrayList<Extension>();
        Util.parseExtensionHeader(result, header);

        Assert.assertEquals(3, result.size());

        Extension ext = result.get(0);
        Assert.assertEquals("ext", ext.getName());
        List<Parameter> params = ext.getParameters();
        Assert.assertEquals(2, params.size());
        Parameter paramA = params.get(0);
        Assert.assertEquals("a", paramA.getName());
        Assert.assertEquals("1", paramA.getValue());
        Parameter paramB = params.get(1);
        Assert.assertEquals("b", paramB.getName());
        Assert.assertEquals("2", paramB.getValue());

        Extension ext2 = result.get(1);
        Assert.assertEquals("ext2", ext2.getName());
        List<Parameter> params2 = ext2.getParameters();
        Assert.assertEquals(2, params2.size());
        Parameter paramC = params2.get(0);
        Assert.assertEquals("c", paramC.getName());
        Assert.assertNull(paramC.getValue());
        Parameter paramD = params2.get(1);
        Assert.assertEquals("d", paramD.getName());
        Assert.assertEquals("xyz", paramD.getValue());

        Extension ext3 = result.get(2);
        Assert.assertEquals("ext3", ext3.getName());
        List<Parameter> params3 = ext3.getParameters();
        Assert.assertEquals(0, params3.size());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testParseExtensionHeaderInvalid01() {
        Util.parseExtensionHeader(new ArrayList<Extension>(), "ext;a=\"1;b=2");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testParseExtensionHeaderInvalid02() {
        Util.parseExtensionHeader(new ArrayList<Extension>(), "ext;a=1\";b=2");
    }
}
