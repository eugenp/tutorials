/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package baeldung.test;

import baeldung.data.*;
import baeldung.model.Member;
import baeldung.service.MemberRegistration;
import baeldung.util.Resources;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.File;
import java.util.logging.Logger;

import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class MemberRegistrationLiveTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        File[] files = Maven
          .resolver()
          .loadPomFromFile("pom.xml")
          .importRuntimeDependencies()
          .resolve()
          .withTransitivity()
          .asFile();

        return ShrinkWrap
          .create(WebArchive.class, "test.war")
          .addClasses(EntityManagerProducer.class, Member.class, MemberRegistration.class, MemberRepository.class, Resources.class, QueryDslRepositoryExtension.class, QueryDslSupport.class, SecondaryPersistenceUnit.class, SecondaryEntityManagerProducer.class,
            SecondaryEntityManagerResolver.class)
          .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
          .addAsResource("META-INF/apache-deltaspike.properties")
          .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
          .addAsWebInfResource("test-ds.xml")
          .addAsWebInfResource("test-secondary-ds.xml")
          .addAsLibraries(files);
    }

    @Inject MemberRegistration memberRegistration;

    @Inject Logger log;

    @Test
    public void testRegister() throws Exception {
        Member newMember = new Member();
        newMember.setName("Jane Doe");
        newMember.setEmail("jane@mailinator.com");
        newMember.setPhoneNumber("2125551234");
        memberRegistration.register(newMember);
        assertNotNull(newMember.getId());
        log.info(newMember.getName() + " was persisted with id " + newMember.getId());
    }

}
