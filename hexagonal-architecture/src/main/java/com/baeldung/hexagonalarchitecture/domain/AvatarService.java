package com.baeldung.hexagonalarchitecture.domain;

import org.springframework.stereotype.Service;

@Service
public class AvatarService {
    private final CodeRepository codeRepository;

    public AvatarService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public Avatar getAvatarLink(String userName) {
        return codeRepository.getAvatarLink(userName);
    }
}
