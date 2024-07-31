/*
 * Copyright 2021 the original author or authors.
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

import com.github.gavlyukovskiy.boot.jdbc.decorator.DecoratedDataSource;
import com.github.gavlyukovskiy.boot.jdbc.decorator.p6spy.P6SpyDataSourceDecorator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SampleP6SpyApplicationIntegrationTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void whenP6SpyEnabled_datasourceIsDecorated() {
        assertThat(dataSource).isInstanceOf(DecoratedDataSource.class);
    }

    @Test
    void whenP6SpyEnabled_decoratingChainContainsP6Spy() {
        DecoratedDataSource decoratedDataSource = (DecoratedDataSource) dataSource;
        assertThat(decoratedDataSource.getDecoratingChain().get(0).getDataSourceDecorator()).isInstanceOf(P6SpyDataSourceDecorator.class);
    }

}