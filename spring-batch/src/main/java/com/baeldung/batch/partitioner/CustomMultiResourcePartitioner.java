/*
 * Copyright 2006-2013 the original author or authors.
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

package com.baeldung.batch.partitioner;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

public class CustomMultiResourcePartitioner implements Partitioner {

    private static final String DEFAULT_KEY_NAME = "fileName";

    private static final String PARTITION_KEY = "partition";

    private Resource[] resources = new Resource[0];

    private String keyName = DEFAULT_KEY_NAME;

    /**
     * The resources to assign to each partition. In Spring configuration you
     * can use a pattern to select multiple resources.
     * @param resources the resources to use
     */
    public void setResources(Resource[] resources) {
        this.resources = resources;
    }

    /**
     * The name of the key for the file name in each {@link ExecutionContext}.
     * Defaults to "fileName".
     * @param keyName the value of the key
     */
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    /**
     * Assign the filename of each of the injected resources to an
     * {@link ExecutionContext}.
     *
     * @see Partitioner#partition(int)
     */
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> map = new HashMap<>(gridSize);
        int i = 0, k = 1;
        for (Resource resource : resources) {
            ExecutionContext context = new ExecutionContext();
            Assert.state(resource.exists(), "Resource does not exist: " + resource);
            context.putString(keyName, resource.getFilename());
            context.putString("opFileName", "output" + k++ + ".xml");

            map.put(PARTITION_KEY + i, context);
            i++;
        }
        return map;
    }

}
