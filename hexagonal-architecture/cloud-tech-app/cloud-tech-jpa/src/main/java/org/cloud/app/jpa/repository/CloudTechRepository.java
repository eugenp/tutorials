package org.cloud.app.jpa.repository;

import org.cloud.app.jpa.model.CloudTechEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CloudTechRepository extends JpaRepository<CloudTechEntity, Long> {

    void deleteAllByCloud(String name);

    CloudTechEntity findByCloud(String Name);

    CloudTechEntity findByCloudTech(String CloudTech);
}
