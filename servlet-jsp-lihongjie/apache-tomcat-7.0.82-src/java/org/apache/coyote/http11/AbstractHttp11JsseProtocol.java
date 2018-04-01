/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.coyote.http11;

import org.apache.tomcat.util.net.SSLImplementation;

public abstract class AbstractHttp11JsseProtocol<S>
        extends AbstractHttp11Protocol<S> {

    protected SSLImplementation sslImplementation = null;

    public String getAlgorithm() { return endpoint.getAlgorithm();}
    public void setAlgorithm(String s ) { endpoint.setAlgorithm(s);}
    
    public String getClientAuth() { return endpoint.getClientAuth();}
    public void setClientAuth(String s ) { endpoint.setClientAuth(s);}

    public String getKeystoreFile() { return endpoint.getKeystoreFile();}
    public void setKeystoreFile(String s ) { endpoint.setKeystoreFile(s);}

    public String getKeystorePass() { return endpoint.getKeystorePass();}
    public void setKeystorePass(String s ) { endpoint.setKeystorePass(s);}
    
    public String getKeystoreType() { return endpoint.getKeystoreType();}
    public void setKeystoreType(String s ) { endpoint.setKeystoreType(s);}

    public String getKeystoreProvider() {
        return endpoint.getKeystoreProvider();
    }
    public void setKeystoreProvider(String s ) {
        endpoint.setKeystoreProvider(s);
    }

    public String getSslProtocol() { return endpoint.getSslProtocol();}
    public void setSslProtocol(String s) { endpoint.setSslProtocol(s);}
    
    public String getCiphers() { return endpoint.getCiphers();}
    public void setCiphers(String s) { endpoint.setCiphers(s);}

    public String getKeyAlias() { return endpoint.getKeyAlias();}
    public void setKeyAlias(String s ) { endpoint.setKeyAlias(s);}

    public String getKeyPass() { return endpoint.getKeyPass();}
    public void setKeyPass(String s ) { endpoint.setKeyPass(s);}
    
    public void setTruststoreFile(String f){ endpoint.setTruststoreFile(f);}
    public String getTruststoreFile(){ return endpoint.getTruststoreFile();}

    public void setTruststorePass(String p){ endpoint.setTruststorePass(p);}
    public String getTruststorePass(){return endpoint.getTruststorePass();}

    public void setTruststoreType(String t){ endpoint.setTruststoreType(t);}
    public String getTruststoreType(){ return endpoint.getTruststoreType();}

    public void setTruststoreProvider(String t){
        endpoint.setTruststoreProvider(t);
    }
    public String getTruststoreProvider(){
        return endpoint.getTruststoreProvider();
    }

    public void setTruststoreAlgorithm(String a){
        endpoint.setTruststoreAlgorithm(a);
    }
    public String getTruststoreAlgorithm(){
        return endpoint.getTruststoreAlgorithm();
    }
    
    public void setTrustMaxCertLength(String s){
        endpoint.setTrustMaxCertLength(s);
    }
    public String getTrustMaxCertLength(){
        return endpoint.getTrustMaxCertLength();
    }
    
    public void setCrlFile(String s){endpoint.setCrlFile(s);}
    public String getCrlFile(){ return endpoint.getCrlFile();}
    
    public void setSessionCacheSize(String s){endpoint.setSessionCacheSize(s);}
    public String getSessionCacheSize(){ return endpoint.getSessionCacheSize();}

    public void setSessionTimeout(String s){endpoint.setSessionTimeout(s);}
    public String getSessionTimeout(){ return endpoint.getSessionTimeout();}
    
    public void setAllowUnsafeLegacyRenegotiation(String s) {
        endpoint.setAllowUnsafeLegacyRenegotiation(s);
    }
    public String getAllowUnsafeLegacyRenegotiation() {
        return endpoint.getAllowUnsafeLegacyRenegotiation();
    }

    private String sslImplementationName = null;
    public String getSslImplementationName() { return sslImplementationName; }
    public void setSslImplementationName(String s) {
        this.sslImplementationName = s;
    }

    // ------------------------------------------------------- Lifecycle methods

    @Override
    public void init() throws Exception {
        // SSL implementation needs to be in place before end point is
        // initialized
        sslImplementation = SSLImplementation.getInstance(sslImplementationName);
        super.init();
    }
}
