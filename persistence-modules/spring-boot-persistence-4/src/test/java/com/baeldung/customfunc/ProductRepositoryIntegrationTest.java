package com.baeldung.customfunc;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = CustomFunctionApplication.class, properties = {
        "spring.jpa.show-sql=false",
        "spring.jpa.properties.hibernate.format_sql=false",
        "spring.jpa.generate-ddl=true",
        "spring.jpa.defer-datasource-initialization=true",
        "spring.sql.init.data-locations=classpath:product-data.sql"
})
@Transactional
public class ProductRepositoryIntegrationTest {

    private static final String TEXT = "Hand Grip Strengthener";
    private static final String EXPECTED_HASH_HEX = getSha256Hex(TEXT);

    @Autowired
    private ProductRepository productRepository;

    private static String getSha256Hex(String value) {
        return Hex.encodeHexString(DigestUtils.sha256(value.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void whenGetSha256HexByNamedMapping_thenReturnCorrectHash() {
        var hash = productRepository.getSha256HexByNamedMapping(TEXT);
        assertThat(hash).isEqualTo(EXPECTED_HASH_HEX);
    }

    @Test
    void whenGetSha256HexByNativeCall_thenReturnCorrectHash() {
        var hash = productRepository.getSha256HexByNativeCall(TEXT);
        assertThat(hash).isEqualTo(EXPECTED_HASH_HEX);
    }

    @Test
    void whenCallGetSha256HexNative_thenReturnCorrectHash() {
        var hashList = productRepository.getProductNameListInSha256HexByNativeSelect();
        assertThat(hashList.get(0)).isEqualTo(EXPECTED_HASH_HEX);
    }

    @Test
    void whenCallGetSha256Hex_thenReturnCorrectHash() {
        var hashList = productRepository.getProductNameListInSha256Hex();
        assertThat(hashList.get(0)).isEqualTo(EXPECTED_HASH_HEX);
    }
    
    @AfterEach
    public void afterEach() {
        productRepository.deleteAll();
    }

}