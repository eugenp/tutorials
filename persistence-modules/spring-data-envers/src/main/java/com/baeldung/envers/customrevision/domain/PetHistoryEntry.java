package com.baeldung.envers.customrevision.domain;

import org.springframework.data.history.RevisionMetadata;

import java.time.Instant;
import java.util.UUID;

public record PetHistoryEntry(
  Instant eventDate,
  RevisionMetadata.RevisionType revisionType,
  UUID petUuid,
  String speciesName,
  String petName,
  String ownerName,
  String remoteHost,
  String remoteUser
) {
}
