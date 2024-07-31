/*
 *   Copyright (C) 2016 to the original authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.baeldung.spring.cloud.kubernetes.client;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class RibbonConfiguration {

    @Autowired
    IClientConfig ribbonClientConfig;

    /**
     *  PingUrl will ping a URL to check the status of each server.
     *  Say Hello has, as you’ll recall, a method mapped to the /path; that means that Ribbon will get an HTTP 200 response when it pings a running Backend Server
     *
     * @param  config Client configuration
     * @return The URL to be used for the Ping
     */
    @Bean
    public IPing ribbonPing(IClientConfig config) {
        return new PingUrl();
    }

    /**
     * AvailabilityFilteringRule will use Ribbon’s built-in circuit breaker functionality to filter out any servers in an “open-circuit” state:
     * if a ping fails to connect to a given server, or if it gets a read failure for the server, Ribbon will consider that server “dead” until it begins to respond normally.
     *
     * @param  config Client configuration
     * @return The Load Balancer rule
     */
    @Bean
    public IRule ribbonRule(IClientConfig config) {
        return new AvailabilityFilteringRule();
    }
}
