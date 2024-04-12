/**
 * 
 */
package com.baeldung.kubernetes.admission.service;

import java.util.Base64;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.baeldung.kubernetes.admission.config.AdmissionControllerProperties;
import com.baeldung.kubernetes.admission.dto.AdmissionReviewData;
import com.baeldung.kubernetes.admission.dto.AdmissionReviewException;
import com.baeldung.kubernetes.admission.dto.AdmissionReviewResponse;
import com.baeldung.kubernetes.admission.dto.AdmissionStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Process an incoming admission request and add the "wait-for-it" init container
 * if there's an appropriate annotation
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AdmissionService {

    private final AdmissionControllerProperties admissionControllerProperties;
    private final ObjectMapper om;

    public AdmissionReviewResponse processAdmission(ObjectNode body) {

        String uid = body.path("request")
          .required("uid")
          .asText();

        log.info("[I42] processAdmission: uid={}",uid);
        if ( log.isDebugEnabled()) {
            log.debug("processAdmission: body={}", body.toPrettyString());
        }

        // Get request annotations
        JsonNode annotations = body.path("request")
          .path("object")
          .path("metadata")
          .path("annotations");
        log.info("processAdmision: annotations={}", annotations.toString());

        AdmissionReviewData data;
        try {
            if (admissionControllerProperties.isDisabled()) {
                log.info("[I58] 'disabled' option in effect. No changes to current request will be made");
                data = createSimpleAllowedReview(body);
            } else if (annotations.isMissingNode()) {
                log.info("[I68] No annotations found in request. No changes will be made");
                data = createSimpleAllowedReview(body);
            } else {
                data = processAnnotations(body, annotations);
            }

            log.info("[I65] Review result: isAllowed=" + data.isAllowed());
            log.info("[I64] AdmissionReviewData= {}", data);

            return AdmissionReviewResponse.builder()
              .apiVersion(body.required("apiVersion").asText())
              .kind(body.required("kind").asText())
              .response(data)
              .build();
        } catch (AdmissionReviewException ex) {
            log.error("[E72] Error processing AdmissionRequest: code={}, message={}", ex.getCode(), ex.getMessage());
            data = createRejectedAdmissionReview(body, ex.getCode(), ex.getMessage());

            return AdmissionReviewResponse.builder()
              .apiVersion(body.required("apiVersion").asText())
              .kind(body.required("kind").asText())
              .response(data)
              .build();
        } catch (Exception ex) {
            log.error("[E72] Unable to process AdmissionRequest: " + ex.getMessage(), ex);
            data = createRejectedAdmissionReview(body, 500, ex.getMessage());
            return AdmissionReviewResponse.builder()
              .apiVersion(body.required("apiVersion").asText())
              .kind(body.required("kind").asText())
              .response(data)
              .build();
        }
    }

    /**
     * @param body
     * @return
     */
    protected AdmissionReviewData createSimpleAllowedReview(ObjectNode body) {
        AdmissionReviewData data;
        String requestId = body.path("request")
          .required("uid")
          .asText();

        data = AdmissionReviewData.builder()
          .allowed(true)
          .uid(requestId)
          .build();

        return data;

    }

    /**
     * @param body
     * @return
     */
    protected AdmissionReviewData createRejectedAdmissionReview(ObjectNode body, int code, String message) {
        AdmissionReviewData data;
        String requestId = body.path("request")
          .required("uid")
          .asText();

        AdmissionStatus status = AdmissionStatus.builder()
          .code(code)
          .message(message)
          .build();

        data = AdmissionReviewData.builder()
          .allowed(false)
          .uid(requestId)
          .status(status)
          .build();

        return data;

    }

    /**
     * Processa anotações incluídas no deployment
     * @param annotations
     * @return
     */
    protected AdmissionReviewData processAnnotations(ObjectNode body, JsonNode annotations) {

        if (annotations.path(admissionControllerProperties.getAnnotation())
            .isMissingNode()) {
            log.info("[I78] processAnnotations: Annotation {} not found in deployment deployment.", admissionControllerProperties.getAnnotation());
            return createSimpleAllowedReview(body);
        }
        else {
            log.info("[I163] annotation found: {}", annotations.path(admissionControllerProperties.getAnnotation()));
        }

        // Get wait-for-it arguments from the annotation
        String waitForArgs = annotations.path(admissionControllerProperties.getAnnotation())
          .asText();

        log.info("[I169] waitForArgs={}", waitForArgs);
        // Create a PATCH object
        String patch = injectInitContainer(body, waitForArgs);

        return AdmissionReviewData.builder()
          .allowed(true)
          .uid(body.path("request")
            .required("uid")
            .asText())
          .patch(Base64.getEncoder()
            .encodeToString(patch.getBytes()))
          .patchType("JSONPatch")
          .build();

    }
    
    /**
     * Creates the JSONPatch to be included in the admission response 
     * @param body
     * @param waitForArgs 
     * @return JSONPatch string
     */
    protected String injectInitContainer(ObjectNode body, String waitForArgs) {

        // Recover original init containers from the request
        JsonNode originalSpec = body.path("request")
          .path("object")
          .path("spec")
          .path("template")
          .path("spec")
          .require();

        JsonNode maybeInitContainers = originalSpec.path("initContainers");
        ArrayNode initContainers = 
        maybeInitContainers.isMissingNode()?
          om.createArrayNode():(ArrayNode) maybeInitContainers;

        // Create the patch array
        ArrayNode patchArray = om.createArrayNode();
        ObjectNode addNode = patchArray.addObject();

        addNode.put("op", "add");
        addNode.put("path", "/spec/template/spec/initContainers");
        ArrayNode values = addNode.putArray("value");

        // Preserve original init containers
        values.addAll(initContainers);

        // append the "wait-for-it" container
        ObjectNode wfi = values.addObject();
        wfi.put("name", "wait-for-it-" + UUID.randomUUID()); // Create an unique name, JIC
        wfi.put("image", admissionControllerProperties.getWaitForItImage());

        ArrayNode args = wfi.putArray("args");
        for (String s : waitForArgs.split("\\s")) {
            args.add(s);
        }

        return patchArray.toString();
    }
}
