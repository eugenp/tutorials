package com.baeldung.tutorials.passkey.repository;

import com.baeldung.tutorials.passkey.domain.PasskeyUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.webauthn.api.Bytes;
import org.springframework.security.web.webauthn.api.ImmutablePublicKeyCredentialUserEntity;
import org.springframework.security.web.webauthn.api.PublicKeyCredentialUserEntity;
import org.springframework.security.web.webauthn.management.PublicKeyCredentialUserEntityRepository;

@Slf4j
@RequiredArgsConstructor
public class DbPublicKeyCredentialUserEntityRepository implements PublicKeyCredentialUserEntityRepository {

    private final PasskeyUserRepository userRepository;

    @Override
    public PublicKeyCredentialUserEntity findById(Bytes id) {
        log.info("findById: id={}", id.toBase64UrlString());
        var externalId = id.toBase64UrlString();
        return userRepository.findByExternalId(externalId)
          .map(DbPublicKeyCredentialUserEntityRepository::mapToUserEntity)
          .orElse(null);
    }

    @Override
    public PublicKeyCredentialUserEntity findByUsername(String username) {
        log.info("findByUsername: username={}", username);
        return userRepository.findByName(username)
          .map(DbPublicKeyCredentialUserEntityRepository::mapToUserEntity)
          .orElse(null);
    }

    @Override
    public void save(PublicKeyCredentialUserEntity userEntity) {
        log.info("save: username={}, externalId={}", userEntity.getName(),userEntity.getId().toBase64UrlString());
        var entity = userRepository.findByExternalId(userEntity.getId().toBase64UrlString())
          .orElse(new PasskeyUser());

        entity.setExternalId(userEntity.getId().toBase64UrlString());
        entity.setName(userEntity.getName());
        entity.setDisplayName(userEntity.getDisplayName());

        userRepository.save(entity);
    }

    @Override
    public void delete(Bytes id) {
        log.info("delete: id={}", id.toBase64UrlString());
        userRepository.findByExternalId(id.toBase64UrlString())
          .ifPresent(userRepository::delete);
    }

    private static PublicKeyCredentialUserEntity mapToUserEntity(PasskeyUser user) {
        return ImmutablePublicKeyCredentialUserEntity.builder()
          .id(Bytes.fromBase64(user.getExternalId()))
          .name(user.getName())
          .displayName(user.getDisplayName())
          .build();
    }
}
