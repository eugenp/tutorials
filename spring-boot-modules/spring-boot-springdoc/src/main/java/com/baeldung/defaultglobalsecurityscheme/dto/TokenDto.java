package com.baeldung.defaultglobalsecurityscheme.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * LoginDto
 */

@JsonTypeName("Token")
public class TokenDto {

    @JsonProperty("raw")
    private String raw;

    @Schema(name = "raw", example = "app token")
    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    @Override
    public String toString() {
        return "TokenDto [raw=" + raw + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((raw == null) ? 0 : raw.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TokenDto other = (TokenDto) obj;
        if (raw == null) {
            if (other.raw != null)
                return false;
        } else if (!raw.equals(other.raw))
            return false;
        return true;
    }

}
