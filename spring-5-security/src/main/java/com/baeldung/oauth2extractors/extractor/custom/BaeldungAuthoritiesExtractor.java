package com.baeldung.oauth2extractors.extractor.custom;

import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BaeldungAuthoritiesExtractor implements AuthoritiesExtractor {

    @Override
    public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
        return AuthorityUtils
                .commaSeparatedStringToAuthorityList(asAuthorities(map));
    }

    private String asAuthorities(Map<String, Object> map) {
        List<String> authorities = new ArrayList<>();
        authorities.add("BAELDUNG_USER");
        List<LinkedHashMap<String, String>> authz = (List<LinkedHashMap<String, String>>) map.get("authorities");
        for (LinkedHashMap<String, String> entry : authz) {
            authorities.add(entry.get("authority"));
        }
        return String.join(",", authorities);
    }
}
