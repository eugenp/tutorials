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


package org.apache.catalina.ha;


import org.apache.tomcat.util.digester.Digester;
import org.apache.tomcat.util.digester.RuleSetBase;


/**
 * <p><strong>RuleSet</strong> for processing the contents of a
 * Cluster definition element.  </p>
 *
 * @author Filip Hanik
 * @author Peter Rossbach
 */
public class ClusterRuleSet extends RuleSetBase {


    // ----------------------------------------------------- Instance Variables


    /**
     * The matching pattern prefix to use for recognizing our elements.
     */
    protected String prefix = null;


    // ------------------------------------------------------------ Constructor


    /**
     * Construct an instance of this <code>RuleSet</code> with the default
     * matching pattern prefix.
     */
    public ClusterRuleSet() {

        this("");

    }


    /**
     * Construct an instance of this <code>RuleSet</code> with the specified
     * matching pattern prefix.
     *
     * @param prefix Prefix for matching pattern rules (including the
     *  trailing slash character)
     */
    public ClusterRuleSet(String prefix) {
        super();
        this.namespaceURI = null;
        this.prefix = prefix;
    }


    // --------------------------------------------------------- Public Methods


    /**
     * <p>Add the set of Rule instances defined in this RuleSet to the
     * specified <code>Digester</code> instance, associating them with
     * our namespace URI (if any).  This method should only be called
     * by a Digester instance.</p>
     *
     * @param digester Digester instance to which the new Rule instances
     *  should be added.
     */
    @Override
    public void addRuleInstances(Digester digester) {
        //Cluster configuration start
        digester.addObjectCreate(prefix + "Manager",
                                 null, // MUST be specified in the element
                                 "className");
        digester.addSetProperties(prefix + "Manager");
        digester.addSetNext(prefix + "Manager",
                            "setManagerTemplate",
                            "org.apache.catalina.ha.ClusterManager");
        digester.addObjectCreate(prefix + "Manager/SessionIdGenerator",
                "org.apache.catalina.util.StandardSessionIdGenerator",
                "className");
        digester.addSetProperties(prefix + "Manager/SessionIdGenerator");
        digester.addSetNext(prefix + "Manager/SessionIdGenerator",
               "setSessionIdGenerator",
               "org.apache.catalina.SessionIdGenerator");

        digester.addObjectCreate(prefix + "Channel",
                                 null, // MUST be specified in the element
                                 "className");
        digester.addSetProperties(prefix + "Channel");
        digester.addSetNext(prefix + "Channel",
                            "setChannel",
                            "org.apache.catalina.tribes.Channel");


        String channelPrefix = prefix + "Channel/";
        { //channel properties
            digester.addObjectCreate(channelPrefix + "Membership",
                                     null, // MUST be specified in the element
                                     "className");
            digester.addSetProperties(channelPrefix + "Membership");
            digester.addSetNext(channelPrefix + "Membership",
                                "setMembershipService",
                                "org.apache.catalina.tribes.MembershipService");

            digester.addObjectCreate(channelPrefix + "MembershipListener",
                                     null, // MUST be specified in the element
                                     "className");
            digester.addSetProperties(channelPrefix + "MembershipListener");
            digester.addSetNext(channelPrefix + "MembershipListener",
                                "addMembershipListener",
                                "org.apache.catalina.tribes.MembershipListener");

            digester.addObjectCreate(channelPrefix + "Sender",
                                     null, // MUST be specified in the element
                                     "className");
            digester.addSetProperties(channelPrefix + "Sender");
            digester.addSetNext(channelPrefix + "Sender",
                                "setChannelSender",
                                "org.apache.catalina.tribes.ChannelSender");

            digester.addObjectCreate(channelPrefix + "Sender/Transport",
                                     null, // MUST be specified in the element
                                     "className");
            digester.addSetProperties(channelPrefix + "Sender/Transport");
            digester.addSetNext(channelPrefix + "Sender/Transport",
                                "setTransport",
                                "org.apache.catalina.tribes.transport.MultiPointSender");


            digester.addObjectCreate(channelPrefix + "Receiver",
                                     null, // MUST be specified in the element
                                     "className");
            digester.addSetProperties(channelPrefix + "Receiver");
            digester.addSetNext(channelPrefix + "Receiver",
                                "setChannelReceiver",
                                "org.apache.catalina.tribes.ChannelReceiver");

            digester.addObjectCreate(channelPrefix + "Interceptor",
                                     null, // MUST be specified in the element
                                     "className");
            digester.addSetProperties(channelPrefix + "Interceptor");
            digester.addSetNext(channelPrefix + "Interceptor",
                                "addInterceptor",
                                "org.apache.catalina.tribes.ChannelInterceptor");

            digester.addObjectCreate(channelPrefix + "Interceptor/LocalMember",
                                     null, // MUST be specified in the element
                                     "className");
            digester.addSetProperties(channelPrefix + "Interceptor/LocalMember");
            digester.addSetNext(channelPrefix + "Interceptor/LocalMember",
                                "setLocalMember",
                                "org.apache.catalina.tribes.Member");

            digester.addObjectCreate(channelPrefix + "Interceptor/Member",
                                     null, // MUST be specified in the element
                                     "className");
            digester.addSetProperties(channelPrefix + "Interceptor/Member");
            digester.addSetNext(channelPrefix + "Interceptor/Member",
                                "addStaticMember",
                                "org.apache.catalina.tribes.Member");

            digester.addObjectCreate(channelPrefix + "ChannelListener",
                                     null, // MUST be specified in the element
                                     "className");
            digester.addSetProperties(channelPrefix + "ChannelListener");
            digester.addSetNext(channelPrefix + "ChannelListener",
                                "addChannelListener",
                                "org.apache.catalina.tribes.ChannelListener");
        }

        digester.addObjectCreate(prefix + "Valve",
                                 null, // MUST be specified in the element
                                 "className");
        digester.addSetProperties(prefix + "Valve");
        digester.addSetNext(prefix + "Valve",
                            "addValve",
                            "org.apache.catalina.Valve");
        
        digester.addObjectCreate(prefix + "Deployer",
                                 null, // MUST be specified in the element
                                 "className");
        digester.addSetProperties(prefix + "Deployer");
        digester.addSetNext(prefix + "Deployer",
                            "setClusterDeployer",
                            "org.apache.catalina.ha.ClusterDeployer");
        
        digester.addObjectCreate(prefix + "Listener",
                null, // MUST be specified in the element
                "className");
        digester.addSetProperties(prefix + "Listener");
        digester.addSetNext(prefix + "Listener",
                            "addLifecycleListener",
                            "org.apache.catalina.LifecycleListener");
        
        digester.addObjectCreate(prefix + "ClusterListener",
                null, // MUST be specified in the element
                "className");
        digester.addSetProperties(prefix + "ClusterListener");
        digester.addSetNext(prefix + "ClusterListener",
                            "addClusterListener",
                            "org.apache.catalina.ha.ClusterListener");
        //Cluster configuration end
    }

}
