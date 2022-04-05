package com.baeldung.hibernate.types;

import java.io.Serializable;

public class CoverArt implements Serializable {

    private String frontCoverArtUrl;
    private String backCoverArtUrl;
    private String upcCode;

    public String getFrontCoverArtUrl() {
        return frontCoverArtUrl;
    }

    public void setFrontCoverArtUrl(String frontCoverArtUrl) {
        this.frontCoverArtUrl = frontCoverArtUrl;
    }

    public String getBackCoverArtUrl() {
        return backCoverArtUrl;
    }

    public void setBackCoverArtUrl(String backCoverArtUrl) {
        this.backCoverArtUrl = backCoverArtUrl;
    }

    public String getUpcCode() {
        return upcCode;
    }

    public void setUpcCode(String upcCode) {
        this.upcCode = upcCode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((backCoverArtUrl == null) ? 0 : backCoverArtUrl.hashCode());
        result = prime * result + ((frontCoverArtUrl == null) ? 0 : frontCoverArtUrl.hashCode());
        result = prime * result + ((upcCode == null) ? 0 : upcCode.hashCode());
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
        CoverArt other = (CoverArt) obj;
        if (backCoverArtUrl == null) {
            if (other.backCoverArtUrl != null)
                return false;
        } else if (!backCoverArtUrl.equals(other.backCoverArtUrl))
            return false;
        if (frontCoverArtUrl == null) {
            if (other.frontCoverArtUrl != null)
                return false;
        } else if (!frontCoverArtUrl.equals(other.frontCoverArtUrl))
            return false;
        if (upcCode == null) {
            if (other.upcCode != null)
                return false;
        } else if (!upcCode.equals(other.upcCode))
            return false;
        return true;
    }
}
