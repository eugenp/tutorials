package com.baeldung.jhipster8.domain;

import static com.baeldung.jhipster8.domain.AuthorityTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.baeldung.jhipster8.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuthorityUnitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Authority.class);
        Authority authority1 = getAuthoritySample1();
        Authority authority2 = new Authority();
        assertThat(authority1).isNotEqualTo(authority2);

        authority2.setName(authority1.getName());
        assertThat(authority1).isEqualTo(authority2);

        authority2 = getAuthoritySample2();
        assertThat(authority1).isNotEqualTo(authority2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Authority authority = new Authority();
        assertThat(authority.hashCode()).isZero();

        Authority authority1 = getAuthoritySample1();
        authority.setName(authority1.getName());
        assertThat(authority).hasSameHashCodeAs(authority1);
    }
}
