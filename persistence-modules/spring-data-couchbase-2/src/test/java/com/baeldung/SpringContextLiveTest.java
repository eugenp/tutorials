package com.baeldung;

import com.baeldung.spring.data.couchbase2b.MultiBucketCouchbaseConfig;
import com.baeldung.spring.data.couchbase2b.MultiBucketIntegrationTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * This LiveTest requires:
 * 
 * 1- Couchbase 5 instance Running and Configured.
 * It's enough to execute the "dockerbuild.sh" script in the test/docker folder.
 *
 * 2.1- Spacial View: Add new spacial view (in Index tab) in document 'campus_spatial', view 'byLocation' with the following function:
 * {@code
 * function (doc) {
 *     if (doc.location &&
 *       doc._class == "com.baeldung.spring.data.couchbase.model.Campus") {
 *         emit([doc.location.x, doc.location.y], null);
 *   }
 * }}
 * 
 * 2.2- MapReduce Views: Add new views in document 'campus':
 *
 * 2.2.1- view 'all' with function:
 * {@code
 * function (doc, meta) {
 *     if(doc._class == "com.baeldung.spring.data.couchbase.model.Campus") {
 *         emit(meta.id, null);
 *     }
 * }}
 *
 * 2.2.2- view 'byName' with function:
 * {@code
 * function (doc, meta) {
 *     if(doc._class == "com.baeldung.spring.data.couchbase.model.Campus" &&
 *       doc.name) {    
 *         emit(doc.name, null);
 *     }
 * }}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MultiBucketCouchbaseConfig.class, MultiBucketIntegrationTestConfig.class })
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class })
public class SpringContextLiveTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
