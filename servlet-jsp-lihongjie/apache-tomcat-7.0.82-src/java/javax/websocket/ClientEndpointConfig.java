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
package javax.websocket;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface ClientEndpointConfig extends EndpointConfig {

    List<String> getPreferredSubprotocols();

    List<Extension> getExtensions();

    public Configurator getConfigurator();

    public final class Builder {

        private static final Configurator DEFAULT_CONFIGURATOR =
                new Configurator() {};


        public static Builder create() {
            return new Builder();
        }


        private Builder() {
            // Hide default constructor
        }

        private Configurator configurator = DEFAULT_CONFIGURATOR;
        private List<String> preferredSubprotocols = Collections.emptyList();
        private List<Extension> extensions = Collections.emptyList();
        private List<Class<? extends Encoder>> encoders =
                Collections.emptyList();
        private List<Class<? extends Decoder>> decoders =
                Collections.emptyList();


        public ClientEndpointConfig build() {
            return new DefaultClientEndpointConfig(preferredSubprotocols,
                    extensions, encoders, decoders, configurator);
        }


        public Builder configurator(Configurator configurator) {
            if (configurator == null) {
                this.configurator = DEFAULT_CONFIGURATOR;
            } else {
                this.configurator = configurator;
            }
            return this;
        }


        public Builder preferredSubprotocols(
                List<String> preferredSubprotocols) {
            if (preferredSubprotocols == null ||
                    preferredSubprotocols.size() == 0) {
                this.preferredSubprotocols = Collections.emptyList();
            } else {
                this.preferredSubprotocols =
                        Collections.unmodifiableList(preferredSubprotocols);
            }
            return this;
        }


        public Builder extensions(
                List<Extension> extensions) {
            if (extensions == null || extensions.size() == 0) {
                this.extensions = Collections.emptyList();
            } else {
                this.extensions = Collections.unmodifiableList(extensions);
            }
            return this;
        }


        public Builder encoders(List<Class<? extends Encoder>> encoders) {
            if (encoders == null || encoders.size() == 0) {
                this.encoders = Collections.emptyList();
            } else {
                this.encoders = Collections.unmodifiableList(encoders);
            }
            return this;
        }


        public Builder decoders(List<Class<? extends Decoder>> decoders) {
            if (decoders == null || decoders.size() == 0) {
                this.decoders = Collections.emptyList();
            } else {
                this.decoders = Collections.unmodifiableList(decoders);
            }
            return this;
        }
    }


    public class Configurator {

        /**
         * Provides the client with a mechanism to inspect and/or modify the headers
         * that are sent to the server to start the WebSocket handshake.
         *
         * @param headers   The HTTP headers
         */
        public void beforeRequest(Map<String, List<String>> headers) {
            // NO-OP
        }

        /**
         * Provides the client with a mechanism to inspect the handshake response
         * that is returned from the server.
         *
         * @param handshakeResponse The response
         */
        public void afterResponse(HandshakeResponse handshakeResponse) {
            // NO-OP
        }
    }
}
