/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.baeldung.p6spy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class JDBCControllerIntegrationTest {

    private final RestTemplate restTemplate = new RestTemplate();
    @Value("http://localhost:${local.server.port}/jdbc")
    private String localhost;

    @Test
    void testJdbcCommitRESTMethod_isSuccessful() {
        Assertions.assertTrue(restTemplate.getForEntity(localhost + "/commit", String.class)
                .getStatusCode().is2xxSuccessful());
    }

    @Test
    void jdbcRollbackRESTMethod_isSuccessful() {
        Assertions.assertTrue(restTemplate.getForEntity(localhost + "/rollback", String.class)
                .getStatusCode().is2xxSuccessful());
    }

    @Test
    void jdbcQueryErrorRESTMethod_isSuccessful() {
        Assertions.assertTrue(restTemplate.getForEntity(localhost + "/query-error", String.class)
                .getStatusCode().is2xxSuccessful());
    }
}