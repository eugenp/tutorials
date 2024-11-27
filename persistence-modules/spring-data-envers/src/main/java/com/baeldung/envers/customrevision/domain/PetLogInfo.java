package com.baeldung.envers.customrevision.domain;

import org.springframework.data.history.RevisionMetadata;

import java.time.Instant;
import java.util.UUID;

public record PetLogInfo(
  Instant eventDate,
  RevisionMetadata.RevisionType revisionType,
  UUID petUuid,
  String speciesName,
  String petName,
  String ownerName
) {
}
