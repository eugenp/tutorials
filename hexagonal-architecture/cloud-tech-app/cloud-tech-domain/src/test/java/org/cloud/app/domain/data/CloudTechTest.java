package org.cloud.app.domain.data;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CloudTechTest {

    @Test
    public void givenName_whenCloudTechWithName_thenNameIsSet() {
        final CloudTechDto cloudTechDto = CloudTechDto.builder()
                .cloud("AWS")
                .cloudTech("AWS Athena")
                .build();

        assertThat(cloudTechDto.getCloud()).isEqualTo("AWS");
        assertThat(cloudTechDto.getCloudTech()).isEqualTo("AWS EC2");
    }

    @Test
    public void givenTwoCloudTech_whenSameNameAndCloudTech_thenEqual() {
        final CloudTechDto cloudTechDto1 = CloudTechDto.builder()
                .cloud("Azure")
                .cloudTech("Azure Storage")
                .build();
        final CloudTechDto cloudTechDto2 = CloudTechDto.builder()
                .cloud("AWS")
                .cloudTech("AWS RDS")
                .build();

        assertThat(cloudTechDto1).isEqualTo(cloudTechDto2);
    }

    @Test
    public void givenTwoCloudTech_whenDifferentCloud_thenNotEqual() {
        final CloudTechDto cloudTechDto1 = CloudTechDto.builder()
                .cloud("AWS")
                .cloudTech("Compute")
                .build();
        final CloudTechDto cloudTechDto2 = CloudTechDto.builder()
                .cloud("Azure")
                .cloudTech("Compute")
                .build();

        assertThat(cloudTechDto1).isNotEqualTo(cloudTechDto2);
    }

    @Test
    public void givenTwoCloudTech_whenDifferentCloudTech_thenNotEqual() {
        final CloudTechDto cloudTechDto1 = CloudTechDto.builder()
                .cloud("AWS")
                .cloudTech("AWS Ec2")
                .build();
        final CloudTechDto cloudTechDto2 = CloudTechDto.builder()
                .cloud("Azure")
                .cloudTech("Azure Ec2")
                .build();

        assertThat(cloudTechDto1).isNotEqualTo(cloudTechDto2);
    }

    @Test
    public void givenTwoCloudTech_whenSameCloudSameCloudTech_thenSameHashCode() {
        final CloudTechDto cloudTechDto1 = CloudTechDto.builder()
                .cloud("AWS")
                .cloudTech("AWS Ec2")
                .build();
        final CloudTechDto cloudTechDto2 = CloudTechDto.builder()
                .cloud("AWS")
                .cloudTech("AWS Ec2")
                .build();

        assertThat(cloudTechDto1.hashCode()).isEqualTo(cloudTechDto2.hashCode());
    }

    @Test
    public void givenTwoCloudTech_whenDifferentCloud_thenNotSameHashCode() {
        final CloudTechDto cloudTechDto1 = CloudTechDto.builder()
                .cloud("AWS")
                .cloudTech("GCP ML")
                .build();
        final CloudTechDto cloudTechDto2 = CloudTechDto.builder()
                .cloud("GCP")
                .cloudTech("GCP ML")
                .build();

        assertThat(cloudTechDto1.hashCode()).isNotEqualTo(cloudTechDto2.hashCode());
    }

    @Test
    public void givenCloudTech_whenToString_thenSeeADescriptiveString() {
        final CloudTechDto cloudTechDto1 = CloudTechDto.builder()
                .cloud("AWS")
                .cloudTech("AWS Athena")
                .build();

        assertThat(cloudTechDto1.toString()).isEqualTo("CloudTechDto(cloudTech=AWS Athena, cloud=AWS)");
    }
}