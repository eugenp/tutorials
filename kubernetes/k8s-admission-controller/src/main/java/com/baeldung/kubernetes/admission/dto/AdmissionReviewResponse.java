/**
 * 
 */
package com.baeldung.kubernetes.admission.dto;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

/**
 * Response "envelope" sent back to the API Server
 */
@Builder
@Data
public class AdmissionReviewResponse {

    @Default
    final String apiVersion = "admission.k8s.io/v1";

    @Default
    final String kind = "AdmissionReview";

    final AdmissionReviewData response;

}
