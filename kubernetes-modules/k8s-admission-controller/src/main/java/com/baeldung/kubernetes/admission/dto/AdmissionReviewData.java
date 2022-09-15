/**
 * 
 */
package com.baeldung.kubernetes.admission.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

/**
 * Result sent to the API server after reviewing and, possibly
 * modifying the incoming request
 */
@Builder
@Data
public class AdmissionReviewData {

    final String uid;
    final boolean allowed;

    @JsonInclude(Include.NON_NULL)
    final String patchType;

    @JsonInclude(Include.NON_NULL)
    final String patch;

    @JsonInclude(Include.NON_NULL)
    final AdmissionStatus status;
}
