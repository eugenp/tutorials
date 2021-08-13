package com.baeldung.kubernetes.admission.dto;

public class AdmissionReviewException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final int code;

    public AdmissionReviewException(int code, String message) {
        super(message);
        this.code = code;
    }

    public AdmissionReviewException(String message) {
        super(message);
        this.code = 400;

    }

    public int getCode() {
        return code;
    }

}
