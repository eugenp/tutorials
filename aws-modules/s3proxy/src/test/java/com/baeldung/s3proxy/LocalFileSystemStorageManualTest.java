package com.baeldung.s3proxy;

import org.springframework.test.context.ActiveProfiles;
/**
 * Required defined environment variables AWS_ACCESS_KEY_ID & AWS_ACCESS_KEY to access S3.
 */
@ActiveProfiles({ "local", "test" })
class LocalFileSystemStorageManualTest extends BaseStorageTest {
}