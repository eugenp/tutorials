package com.baeldung.architecture.hexagonal.persistence.postgres.adapter.parent;

import com.baeldung.architecture.hexagonal.persistence.postgres.adapter.parent.mapper.IGenericMapper;

@SuppressWarnings("rawtypes")
abstract class AbstractJpaAdapter<REPOSITORY> {

    protected abstract <T extends REPOSITORY> T getRepository();

    protected abstract <T extends IGenericMapper> T getMapper();
}
