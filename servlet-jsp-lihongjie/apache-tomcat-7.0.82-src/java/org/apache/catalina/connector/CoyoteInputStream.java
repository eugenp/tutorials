/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.catalina.connector;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import javax.servlet.ServletInputStream;

import org.apache.catalina.security.SecurityUtil;

/**
 * This class handles reading bytes.
 *
 * @author Remy Maucherat
 * @author Jean-Francois Arcand
 */
public class CoyoteInputStream
    extends ServletInputStream {


    // ----------------------------------------------------- Instance Variables


    protected InputBuffer ib;


    // ----------------------------------------------------------- Constructors


    protected CoyoteInputStream(InputBuffer ib) {
        this.ib = ib;
    }


    // -------------------------------------------------------- Package Methods


    /**
     * Clear facade.
     */
    void clear() {
        ib = null;
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Prevent cloning the facade.
     */
    @Override
    protected Object clone()
        throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }


    // --------------------------------------------- ServletInputStream Methods


    @Override
    public int read()
        throws IOException {
        if (SecurityUtil.isPackageProtectionEnabled()){

            try{
                Integer result =
                    AccessController.doPrivileged(
                        new PrivilegedExceptionAction<Integer>(){

                            @Override
                            public Integer run() throws IOException{
                                Integer integer = Integer.valueOf(ib.readByte());
                                return integer;
                            }

                });
                return result.intValue();
            } catch(PrivilegedActionException pae){
                Exception e = pae.getException();
                if (e instanceof IOException){
                    throw (IOException)e;
                } else {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        } else {
            return ib.readByte();
        }
    }

    @Override
    public int available() throws IOException {

        if (SecurityUtil.isPackageProtectionEnabled()){
            try{
                Integer result =
                    AccessController.doPrivileged(
                        new PrivilegedExceptionAction<Integer>(){

                            @Override
                            public Integer run() throws IOException{
                                Integer integer = Integer.valueOf(ib.available());
                                return integer;
                            }

                });
                return result.intValue();
            } catch(PrivilegedActionException pae){
                Exception e = pae.getException();
                if (e instanceof IOException){
                    throw (IOException)e;
                } else {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        } else {
           return ib.available();
        }
    }

    @Override
    public int read(final byte[] b) throws IOException {

        if (SecurityUtil.isPackageProtectionEnabled()){
            try{
                Integer result =
                    AccessController.doPrivileged(
                        new PrivilegedExceptionAction<Integer>(){

                            @Override
                            public Integer run() throws IOException{
                                Integer integer =
                                    Integer.valueOf(ib.read(b, 0, b.length));
                                return integer;
                            }

                });
                return result.intValue();
            } catch(PrivilegedActionException pae){
                Exception e = pae.getException();
                if (e instanceof IOException){
                    throw (IOException)e;
                } else {
                    throw new RuntimeException(e.getMessage() ,e);
                }
            }
        } else {
            return ib.read(b, 0, b.length);
         }
    }


    @Override
    public int read(final byte[] b, final int off, final int len)
        throws IOException {

        if (SecurityUtil.isPackageProtectionEnabled()){
            try{
                Integer result =
                    AccessController.doPrivileged(
                        new PrivilegedExceptionAction<Integer>(){

                            @Override
                            public Integer run() throws IOException{
                                Integer integer =
                                    Integer.valueOf(ib.read(b, off, len));
                                return integer;
                            }

                });
                return result.intValue();
            } catch(PrivilegedActionException pae){
                Exception e = pae.getException();
                if (e instanceof IOException){
                    throw (IOException)e;
                } else {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        } else {
            return ib.read(b, off, len);
        }
    }


    @Override
    public int readLine(byte[] b, int off, int len) throws IOException {
        return super.readLine(b, off, len);
    }


    /**
     * Close the stream
     * Since we re-cycle, we can't allow the call to super.close()
     * which would permanently disable us.
     */
    @Override
    public void close() throws IOException {

        if (SecurityUtil.isPackageProtectionEnabled()){
            try{
                AccessController.doPrivileged(
                    new PrivilegedExceptionAction<Void>(){

                        @Override
                        public Void run() throws IOException{
                            ib.close();
                            return null;
                        }

                });
            } catch(PrivilegedActionException pae){
                Exception e = pae.getException();
                if (e instanceof IOException){
                    throw (IOException)e;
                } else {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        } else {
             ib.close();
        }
    }

}
