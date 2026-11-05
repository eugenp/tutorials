package com.baeldung.causeway.assets;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.baeldung.causeway.assets.domain.Asset;
import com.baeldung.causeway.assets.domain.AssetRepository;
import com.baeldung.causeway.assets.domain.Assets;

@Configuration
@ComponentScan(basePackageClasses = Assets.class)
@EnableJpaRepositories(basePackageClasses = AssetRepository.class)
@EntityScan(basePackageClasses = Asset.class)
public class AssetManagementModule {
}
