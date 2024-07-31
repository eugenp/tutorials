package com.baeldung.oauth2extractors.extractor.github;

import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GithubAuthoritiesExtractor implements AuthoritiesExtractor {
    private List<GrantedAuthority> GITHUB_FREE_AUTHORITIES = AuthorityUtils
            .commaSeparatedStringToAuthorityList("GITHUB_USER,GITHUB_USER_FREE");
    private List<GrantedAuthority> GITHUB_SUBSCRIBED_AUTHORITIES = AuthorityUtils
            .commaSeparatedStringToAuthorityList("GITHUB_USER,GITHUB_USER_SUBSCRIBED");

    @Override
    public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
        if (Objects.nonNull(map.get("plan"))) {
            if (!((LinkedHashMap) map.get("plan"))
                    .get("name")
                    .equals("free")) {
                return GITHUB_SUBSCRIBED_AUTHORITIES;
            }
        }
        return GITHUB_FREE_AUTHORITIES;
    }
}
