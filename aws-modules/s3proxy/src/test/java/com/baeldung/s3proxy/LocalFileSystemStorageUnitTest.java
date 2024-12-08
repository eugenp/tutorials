package com.baeldung.s3proxy;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({ "local", "test" })
class LocalFileSystemStorageUnitTest extends BaseStorageTest {
}