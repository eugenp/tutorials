package com.baeldung.accesing_session_attributes.web.beans;

import java.util.Date;

import com.baeldung.accesing_session_attributes.business.beans.NameRequest;

public class SessionNameRequest {
    private Date requestDate;
    private NameRequest nameRequest;

    public SessionNameRequest() {
        super();
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public NameRequest getNameRequest() {
        return nameRequest;
    }

    public void setNameRequest(NameRequest nameRequest) {
        this.nameRequest = nameRequest;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((requestDate == null) ? 0 : requestDate.hashCode());
        result = prime * result + ((nameRequest == null) ? 0 : nameRequest.hashCode());
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
        SessionNameRequest other = (SessionNameRequest) obj;
        if (requestDate == null) {
            if (other.requestDate != null)
                return false;
        } else if (!requestDate.equals(other.requestDate))
            return false;
        if (nameRequest == null) {
            if (other.nameRequest != null)
                return false;
        } else if (!nameRequest.equals(other.nameRequest))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SessionNameRequest [requestDate=" + requestDate + ", nameRequest=" + nameRequest + "]";
    }

}
